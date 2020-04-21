package com.leon.item.service;

import com.leon.common.pojo.PageResult;
import com.leon.common.pojo.SpuQueryByPageParameter;
import com.leon.item.bo.*;
import com.leon.item.pojo.SecondKillGoods;
import com.leon.item.pojo.Sku;
import com.leon.item.pojo.SpuDetail;

import java.text.ParseException;
import java.util.List;

/**
 * @author LeonMac
 * @description
 */

public interface GoodsService {

    /**
    * @description: 分页查询
    * @param spuQueryByPageParameter
    * @return com.leon.order.pojo.PageResult<com.leon.item.bo.SpuBo>
    */
    PageResult<SpuBo> querySpuByPageAndSort(SpuQueryByPageParameter spuQueryByPageParameter);

    /**
    * @description: 保存商品
    * @param spu
    * @return void
    */
    void saveGoods(SpuBo spu);

    /**
    * @description: 根据Id查询商品
    * @param id
    * @return com.leon.item.bo.SpuBo
    */
    SpuBo queryGoodsById(Long id);

    /**
    * @description: 更新商品信息
    * @param spuBo
    * @return void
    */
    void updateGoods(SpuBo spuBo);

    /**
    * @description: 商品下架
    * @param id
    * @return void
    */
    void goodsSoldOut(Long id);

    /**
    * @description: 商品删除，单个多个二合一
    * @param id
    * @return void
    */
    void deleteGoods(long id);

    /**
    * @description: 根据spu商品Id查询详细信息
    * @param id
    * @return com.leon.order.pojo.SpuDetail
    */
    SpuDetail querySpuDetailBySpuId(long id);

    /**
    * @description: 根据spu的Id查询其下所有sku
    * @param id
    * @return java.util.List<com.leon.order.pojo.Sku>
    */
    List<Sku> querySkuBySpuId(Long id);

    /**
    * @description: 发送消息到mq
    * @param id
	* @param type
    * @return void
    */
    void sendMessage(Long id,String type);

    /**
    * @description: 根据id查询sku
    * @param id
    * @return com.leon.order.pojo.Sku
    */
    Sku querySkuById(Long id);

    /**
    * @description: 查询秒杀商品
    * @param
    * @return java.util.List<com.leon.order.pojo.SecondKillGoods>
    */
    List<SecondKillGoods> querySecondKillGoods();

    /**
     * @param secondKillParameter
     * @return void
     * @description: 添加秒杀商品
     */
    void addSecondKillGoods(SecondKillParameter secondKillParameter) throws ParseException;

}
