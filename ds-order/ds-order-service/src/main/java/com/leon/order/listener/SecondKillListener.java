package com.leon.order.listener;

import com.leon.authentication.entity.UserInfo;
import com.leon.item.pojo.SecondKillGoods;
import com.leon.item.pojo.Stock;
import com.leon.order.pojo.*;
import com.leon.order.mapper.*;
import com.leon.order.service.OrderService;
import com.leon.common.utils.IdWorker;
import com.leon.common.utils.JsonUtils;
import com.leon.secondkill.vo.SecondKillMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author LeonMac
 * @description
 */
@Component
public class SecondKillListener {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private SecondKillOrderMapper secondKillOrderMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private SecondKillMapper secondKillMapper;

    /**
    * @description: 接收秒杀信息
    * @param secondKill
    * @return void
    */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leon.order.secondkill.queue",durable = "true"), //队列持久化
            exchange = @Exchange(
                    value = "leon.order.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"order.secondkill"}
    ))
    @Transactional(rollbackFor = Exception.class)
    public void listenSecondKill(String secondKill){

        SecondKillMessage secondKillMessage = JsonUtils.parse(secondKill, SecondKillMessage.class);
        UserInfo userInfo = secondKillMessage.getUserInfo();
        SecondKillGoods secondKillGoods = secondKillMessage.getSecondKillGoods();


        //1.首先判断库存是否充足
        Stock stock = stockMapper.selectByPrimaryKey(secondKillGoods.getSkuId());
        if (stock.getSeckillStock() <= 0 || stock.getStock() <= 0){
            //如果库存不足的话修改秒杀商品的enable字段
            Example example = new Example(SecondKillGoods.class);
            example.createCriteria().andEqualTo("skuId", secondKillGoods.getSkuId());
            List<SecondKillGoods> list = this.secondKillMapper.selectByExample(example);
            for (SecondKillGoods temp : list){
                if (temp.getEnable()){
                    temp.setEnable(false);
                    this.secondKillMapper.updateByPrimaryKeySelective(temp);
                }
            }
            return;
        }
        //2.判断此用户是否已经秒杀到了
        Example example = new Example(SecondKillOrder.class);
        example.createCriteria().andEqualTo("userId",userInfo.getId()).andEqualTo("skuId",secondKillGoods.getSkuId());
        List<SecondKillOrder> list = this.secondKillOrderMapper.selectByExample(example);
        if (list.size() > 0){
            return;
        }
        //3.下订单
        //构造order对象
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

        //3.1 生成orderId
        long orderId = idWorker.nextId();
        //3.2 初始化数据
        order.setBuyerNick(userInfo.getUsername());
        order.setBuyerRate(false);
        order.setCreateTime(new Date());
        order.setOrderId(orderId);
        order.setUserId(userInfo.getId());
        //3.3 保存数据
        this.orderMapper.insertSelective(order);

        //3.4 保存订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCreateTime(order.getCreateTime());
        //初始状态未未付款：1
        orderStatus.setStatus(1);
        //3.5 保存数据
        this.orderStatusMapper.insertSelective(orderStatus);

        //3.6 在订单详情中添加orderId
        order.getOrderDetails().forEach(od -> {
            //添加订单
            od.setOrderId(orderId);
        });

        //3.7 保存订单详情，使用批量插入功能
        this.orderDetailMapper.insertList(order.getOrderDetails());

        //3.8 修改库存
        order.getOrderDetails().forEach(ord -> {
            Stock stock1 = this.stockMapper.selectByPrimaryKey(ord.getSkuId());
            stock1.setStock(stock1.getStock() - ord.getNum());
            stock1.setSeckillStock(stock1.getSeckillStock() - ord.getNum());
            this.stockMapper.updateByPrimaryKeySelective(stock1);

            //新建秒杀订单
            SecondKillOrder secondKillOrder = new SecondKillOrder();
            secondKillOrder.setOrderId(orderId);
            secondKillOrder.setSkuId(ord.getSkuId());
            secondKillOrder.setUserId(userInfo.getId());
            this.secondKillOrderMapper.insert(secondKillOrder);

        });


    }
}
