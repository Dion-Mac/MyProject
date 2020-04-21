package com.leon.item.mapper;

import com.leon.item.pojo.Brand;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;

import java.util.List;

/**
 * @author LeonMac
 * @description 品牌接口类
 */

@Mapper
public interface BrandMapper extends tk.mybatis.mapper.common.Mapper<Brand>, SelectByIdListMapper<Brand, Long> {

    /*
     * @description:
     * 根据分类Id查询品牌，内连接
     * @param cid
     * @return java.util.List<com.leon.order.pojo.Brand>
     */
    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> selectBrandByCid(Long cid);

    /*
     * @description:
     * 新增分类和品牌中间表数据
     * @param cid
     * @param bid
     * @return int
     */
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES(#{cid}, #{bid})")
    int insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /*
     * @description:
     * 根据品牌Id删除中间表相关数据
     * @param bid
     * @return void
     */
    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
    void deleteByBrandIdInCategoryBrand(@Param("bid") Long bid);

    /*
     * @description:
     * 根据品牌Id删除品牌相关数据
     * @param bid
     * @return java.lang.Integer
     */
    @Delete("DELETE FROM tb_brand WHERE id = #{bid}")
    Integer deleteByBrandId1(Long bid);



}
