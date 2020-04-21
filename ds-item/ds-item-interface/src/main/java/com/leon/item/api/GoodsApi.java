package com.leon.item.api;

import com.leon.common.pojo.PageResult;
import com.leon.item.bo.SpuBo;
import com.leon.item.pojo.SecondKillGoods;
import com.leon.item.pojo.Sku;
import com.leon.item.pojo.Spu;
import com.leon.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author LeonMac
 * @description
 */

@RequestMapping("/goods")
public interface GoodsApi {

    /**
    * @description:
    * 分页查询商品
    * @param page
	* @param rows
	* @param saleable
	* @param key
    * @return com.leon.order.pojo.PageResult<com.leon.item.bo.SpuBo>
    */
    @GetMapping("/spu/page")
    PageResult<SpuBo> querySpuBoByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", defaultValue = "true") Boolean saleable,
            @RequestParam(value = "key", required = false) String key);

    /**
    * @description:
    * 根据spuId查询spuDetail
    * @param id
    * @return com.leon.order.pojo.SpuDetail
    */
    @GetMapping("/spu/detail/{id}")
    SpuDetail querySpuDetailBySpuId(@PathVariable("id") Long id);

    /**
    * @description:
    * 根据spuId查询sku列表
    * @param id
    * @return java.util.List<com.leon.order.pojo.Sku>
    */
    @GetMapping("/sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long id);

    /**
    * @description:
    * 根据spuId查询spu
    * @param id
    * @return com.leon.order.pojo.Spu
    */
    @GetMapping("spu/{id}")
    SpuBo queryGoodsById(@PathVariable("id") Long id);

    /**
    * @description: 根据sku的Id查询sku
    * @param id
    * @return com.leon.order.pojo.Sku
    */
    @GetMapping("/sku/{id}")
    Sku querySkuById(@PathVariable("id") Long id);

    /**
    * @description: 查询秒杀商品
    * @param
    * @return java.util.List<com.leon.order.pojo.SecondKillGoods>
    */
    @GetMapping("/secondkill/list")
    List<SecondKillGoods> querySecondKillGoods();

}
