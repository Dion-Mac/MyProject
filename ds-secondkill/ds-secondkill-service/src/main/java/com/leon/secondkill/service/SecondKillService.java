package com.leon.secondkill.service;


import com.leon.item.pojo.SecondKillGoods;
import com.leon.secondkill.vo.SecondKillMessage;


public interface SecondKillService {

    /**
     * 创建订单
     * @param secondKillGoods
     * @return
     */
    Long createOrder(SecondKillGoods secondKillGoods);


    /**
     * 检查库存
     * @param skuId
     * @return
     */
    boolean queryStock(Long skuId);

    /**
     * 发送秒杀信息到队列当中
     * @param secondKillMessage
     */
    void sendMessage(SecondKillMessage secondKillMessage);

    /**
     * 根据用户id查询秒杀订单
     * @param userId
     * @return
     */
    Long checkSecondKillOrder(Long userId);


    /**
     * 创建秒杀地址
     * @param goodsId
     * @param id
     * @return
     */
    String createPath(Long goodsId, Long id);

    /**
     * 验证秒杀地址
     * @param goodsId
     * @param id
     * @param path
     * @return
     */
    boolean checkSecondKillPath(Long goodsId, Long id, String path);

}
