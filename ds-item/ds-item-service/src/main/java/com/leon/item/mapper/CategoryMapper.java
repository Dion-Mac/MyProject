package com.leon.item.mapper;

import com.leon.item.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @author LeonMac
 * @description
 * 分类接口类
 */
public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category, Long> {
    /*
    * @description:
    * 根据品牌Id查询商品分类
    * @param bid
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid})")
    List<Category> selectCategoryByBid(Long bid);

    /*
     * @description:
     * 根据分类Id删除中间表相关数据
     * @param bid
     * @return java.lang.Integer
     */
    @Delete("DELETE FROM tb_category_brand WHERE category_id = #{cid}")
    void deleteByCategoryIdInCategoryBrand(@Param("cid") Long cid);

    /*
     * @description:
     * 根据分类Id删除分类名
     * @param bid
     * @return java.lang.Integer
     */
    @Delete("DELETE FROM tb_category WHERE id = #{cid}")
    Integer deleteByBrandId(Long bid);

    /**
    * @description: 根据Id查询分类名字
    * @param id
    * @return java.lang.String
    */
    @Select("SELECT name FROM tb_category WHERE id = #{id}")
    String queryNameById(Long id);

    /**
    * @description: 查询最后一条数据
    * @param
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    @Select("SELECT * FROM `tb_category` WHERE id = (SELECT MAX(id) FROM tb_category)")
    List<Category> selectLast();

}
