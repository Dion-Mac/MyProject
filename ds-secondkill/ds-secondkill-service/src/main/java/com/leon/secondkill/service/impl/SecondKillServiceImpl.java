package com.leon.secondkill.service.impl;

import com.leon.common.utils.JsonUtils;
import com.leon.item.mapper.SecondKillMapper;
import com.leon.item.pojo.SecondKillGoods;
import com.leon.item.pojo.Stock;
import com.leon.order.pojo.Order;
import com.leon.order.pojo.OrderDetail;
import com.leon.order.pojo.SecondKillOrder;
import com.leon.secondkill.client.GoodsClient;
import com.leon.secondkill.client.OrderClient;
import com.leon.secondkill.mapper.SecondKillOrderMapper;
import com.leon.secondkill.mapper.SkuMapper;
import com.leon.secondkill.mapper.StockMapper;
import com.leon.secondkill.service.SecondKillService;
import com.leon.secondkill.vo.SecondKillMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class SecondKillServiceImpl implements SecondKillService {

    @Autowired
    private SecondKillMapper secondKillMapper;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private SecondKillOrderMapper seckillOrderMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    private static final Logger LOGGER = LoggerFactory.getLogger(SecondKillServiceImpl.class);

    private static final String KEY_PREFIX_PATH = "leon:seckill:path";


    /**
     * 创建订单
     * @param secondKillGoods
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(SecondKillGoods secondKillGoods) {

        Order order = new Order();
        order.setPaymentType(1);
        order.setTotalPay(secondKillGoods.getSecondKillPrice());
        order.setActualPay(secondKillGoods.getSecondKillPrice());
        order.setPostFee(0+"");
        order.setReceiver("李四");
        order.setReceiverMobile("15812312312");
        order.setReceiverCity("西安");
        order.setReceiverDistrict("碑林区");
        order.setReceiverState("陕西");
        order.setReceiverZip("000000000");
        order.setInvoiceType(0);
        order.setSourceType(2);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setSkuId(secondKillGoods.getSkuId());
        orderDetail.setNum(1);
        orderDetail.setTitle(secondKillGoods.getTitle());
        orderDetail.setImage(secondKillGoods.getImage());
        orderDetail.setPrice(secondKillGoods.getSecondKillPrice());
        orderDetail.setOwnSpec(this.skuMapper.selectByPrimaryKey(secondKillGoods.getSkuId()).getOwnSpec());

        order.setOrderDetails(Arrays.asList(orderDetail));


        String seck = "secondKill";
        ResponseEntity<List<Long>> responseEntity = (ResponseEntity<List<Long>>) this.orderClient.createOrder(seck,order);

        if (responseEntity.getStatusCode() == HttpStatus.OK){
            //库存不足，返回null
            return null;
        }
        return responseEntity.getBody().get(0);
    }

    /**
     * 检查秒杀库存
     * @param skuId
     * @return
     */
    @Override
    public boolean queryStock(Long skuId) {
        Stock stock = this.stockMapper.selectByPrimaryKey(skuId);
        if (stock.getSeckillStock() - 1 < 0){
            return false;
        }
        return true;
    }

    /**
     * 发送消息到秒杀队列当中
     * @param secondKillMessage
     */
    @Override
    public void sendMessage(SecondKillMessage secondKillMessage) {
        String json = JsonUtils.serialize(secondKillMessage);
        try {
            this.amqpTemplate.convertAndSend("order.seckill", json);
        }catch (Exception e){
            LOGGER.error("秒杀商品消息发送异常，商品id：{}",secondKillMessage.getSecondKillGoods().getSkuId(),e);
        }
    }

    /**
     * 根据用户id查询秒杀订单
     * @param userId
     * @return
     */
    @Override
    public Long checkSecondKillOrder(Long userId) {
        Example example = new Example(SecondKillOrder.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<SecondKillOrder> seckillOrders = this.seckillOrderMapper.selectByExample(example);
        if (seckillOrders == null || seckillOrders.size() == 0){
            return null;
        }
        return seckillOrders.get(0).getOrderId();
    }

    /**
     * 创建秒杀地址
     * @param goodsId
     * @param id
     * @return
     */
    @Override
    public String createPath(Long goodsId, Long id) {
        String str = new BCryptPasswordEncoder().encode(goodsId.toString()+id);
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX_PATH);
        String key = id.toString() + "_" + goodsId;
        hashOperations.put(key,str);
        hashOperations.expire(60, TimeUnit.SECONDS);
        return str;
    }

    /**
     * 验证秒杀地址
     * @param goodsId
     * @param id
     * @param path
     * @return
     */
    @Override
    public boolean checkSecondKillPath(Long goodsId, Long id, String path) {
        String key = id.toString() + "_" + goodsId;
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX_PATH);
        String encodePath = (String) hashOperations.get(key);
        return new BCryptPasswordEncoder().matches(path,encodePath);
    }


}
