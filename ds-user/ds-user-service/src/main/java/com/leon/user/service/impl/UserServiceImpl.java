package com.leon.user.service.impl;

import com.leon.user.mapper.UserMapper;
import com.leon.user.pojo.User;
import com.leon.user.service.UserService;
import com.leon.common.utils.CodeUtils;
import com.leon.common.utils.JsonUtils;
import com.leon.common.utils.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author LeonMac
 * @description
 */

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String KEY_PREFIX = "user:code:phone";

    private static final String KEY_PREFIX2 = "leon:user:info";

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
    * @description:
    * 检验用户名和手机号
    * @param data
	* @param type
    * @return java.lang.Boolean
    */
    @Override
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        switch (type) {
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                return null;
        }
        return this.userMapper.selectCount(user) == 0;
    }

    /**
    * @description:
    * 发送短信验证码
    * @param phone
    * @return java.lang.Boolean
    */
    @Override
    public Boolean sendVerifyCode(String phone) {
        //1.生成验证码
        String code = NumberUtils.generateCode(6);

        try {
            HashMap<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            //2.发送短信
            this.amqpTemplate.convertAndSend("ds.sms.exchange", "sms.verify.code", msg);
            //3.将code存入redis
            this.stringRedisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            logger.error("发送短信失败。phone：{}，code：{}",phone,code);
            return false;
        }
    }

    @Override
    public Boolean register(User user, String code) {
        String key = KEY_PREFIX + user.getPhone();
        //1.从redis中取出验证码
        String codeCache = this.stringRedisTemplate.opsForValue().get(key);
        //2.检查验证码是否正确
        if (!codeCache.equals(code)) {
            return false;
        }
        user.setId(null);
        user.setCreated(new Date());
        //3.密码加密
        String encodePassword = CodeUtils.passwordBcryptEncode(user.getUsername().trim(), user.getPassword().trim());
        user.setPassword(encodePassword);
        //4.写入数据库
        boolean result = this.userMapper.insertSelective(user) == 1;
        //5.如果注册成功，则删除redis中的code
        if (result) {
            try {
                this.stringRedisTemplate.delete(KEY_PREFIX + user.getPhone());
            } catch (Exception e) {
                logger.error("删除缓存验证码失败，code:{}", code, e);
            }
        }
        return result;
    }

    /**
    * @description:
    * 用户验证
    * @param username
	* @param password
    * @return com.leon.order.pojo.User
    */
    @Override
    public User queryUser(String username, String password) {
        //逻辑改变，先去缓存中查询用户数据，查到的话直接返回，
        // 查不到再去数据库中查询，然后放入到缓存当中
        //1.缓存中查询
        BoundHashOperations<String, Object, Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX2);
        String userStr = (String) hashOperations.get(username);
        User user;
        if (StringUtil.isEmpty(userStr)) {
            //在缓存中没有查到，去数据库查，查到放入缓存中
            User record = new User();
            record.setUsername(username);
            user = this.userMapper.selectOne(record);
            hashOperations.put(user.getUsername(), JsonUtils.serialize(user));
        } else {
            user = JsonUtils.parse(userStr, User.class);
        }
        //2.校验用户名
        if (user == null) {
            return null;
        }
        //3.校验密码
        Boolean result = CodeUtils.passwordConfirm(username + password, user.getPassword());
        if (!result) {
            return null;
        }
        //4.用户名密码都正确
        return user;
    }

    /**
    * @description: 根据用户名改密码
     * 这里涉及对缓存的操作
     * 先把数据存到数据库，，成功后，再让缓存失效
    * @param username
	* @param oldPassword
	* @param newPassword
    * @return java.lang.Boolean
    */
    @Override
    public Boolean updatePassword(String username, String oldPassword, String newPassword) {
        //1.读取用户信息
        User user = this.queryUser(username, oldPassword);
        if (user == null) {
            return false;
        }
        //2.更新数据库中的用户信息,密码加密
        User updateUser = new User();
        updateUser.setId(user.getId());
        String encodePassword = CodeUtils.passwordBcryptEncode(username.trim(), newPassword.trim());
        updateUser.setPassword(encodePassword);
        this.userMapper.updateByPrimaryKeySelective(updateUser);
        //3.处理缓存中的信息
        BoundHashOperations<String, Object, Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX + username);
        hashOperations.delete(username);
        return true;
    }
}
