package com.leon.item.mapper;


import com.leon.item.pojo.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author LeonMac
 * @description
 */
public interface SkuMapper extends Mapper<Sku> {
    /*
    * @description:
    * 根据spuId查询sku信息
    * @param id
    * @return java.util.List<com.leon.order.pojo.Sku>
    */
    @Select("SELECT a.*,b.stock FROM tb_sku a,tb_stock b WHERE a.id=b.sku_id AND a.spu_id=#{id}")
    List<Sku> queryById(@Param("id") Long id);
}
