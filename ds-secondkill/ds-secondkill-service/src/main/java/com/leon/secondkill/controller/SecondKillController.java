package com.leon.secondkill.controller;


import com.leon.authentication.entity.UserInfo;
import com.leon.item.pojo.SecondKillGoods;
import com.leon.secondkill.access.AccessLimit;
import com.leon.secondkill.client.GoodsClient;
import com.leon.secondkill.interceptor.LoginInterceptor;
import com.leon.secondkill.service.SecondKillService;
import com.leon.secondkill.vo.SecondKillMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping
public class SecondKillController implements InitializingBean {

    @Autowired
    private SecondKillService secondKillService;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String KEY_PREFIX = "leon:secondKill:stock";

    private Map<Long,Boolean> localOverMap = new HashMap<>();

    /**
     * 系统初始化，初始化秒杀商品数量
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //1.查询可以秒杀的商品
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX);
        if (hashOperations.hasKey(KEY_PREFIX)){
            hashOperations.entries().forEach((m,n) -> localOverMap.put(Long.parseLong(m.toString()),false));
        }
    }


    /**
     * 秒杀
     * @param path
     * @param secondKillGoods
     * @return
     */
    @PostMapping("/{path}/seck")
    public ResponseEntity<String> seckillOrder(@PathVariable("path") String path, @RequestBody SecondKillGoods secondKillGoods){

        String result = "排队中";

        UserInfo userInfo = LoginInterceptor.getLoginUser();

        //1.验证路径
        boolean check = this.secondKillService.checkSecondKillPath(secondKillGoods.getId(),userInfo.getId(),path);
        if (!check){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //2.内存标记，减少redis访问
        boolean over = localOverMap.get(secondKillGoods.getSkuId());
        if (over){
            return ResponseEntity.ok(result);
        }

        //3.读取库存，减一后更新缓存
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX);
        Long stock = hashOperations.increment(secondKillGoods.getSkuId().toString(), -1);

        //4.库存不足直接返回
        if (stock < 0){
            localOverMap.put(secondKillGoods.getSkuId(),true);
            return ResponseEntity.ok(result);
        }

        //5.库存充足，请求入队
        //5.1 获取用户信息
        SecondKillMessage secondKillMessage = new SecondKillMessage(userInfo,secondKillGoods);
        //5.2 发送消息
        this.secondKillService.sendMessage(secondKillMessage);

        return ResponseEntity.ok(result);
    }

    /**
     * 根据userId查询订单号
     * @param userId
     * @return
     */
    @GetMapping("orderId")
    public ResponseEntity<Long> checkSeckillOrder(Long userId){
        Long result = this.secondKillService.checkSecondKillOrder(userId);
        if (result == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);

    }

    /**
     * 创建秒杀路径
     * @param goodsId
     * @return
     */
    @AccessLimit(seconds = 20,maxCount = 5,needLogin = true)
    @GetMapping("get_path/{goodsId}")
    public ResponseEntity<String> getSecondKillPath(@PathVariable("goodsId") Long goodsId){
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        if (userInfo == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String str = this.secondKillService.createPath(goodsId,userInfo.getId());
        if (StringUtils.isEmpty(str)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(str);
    }

}
