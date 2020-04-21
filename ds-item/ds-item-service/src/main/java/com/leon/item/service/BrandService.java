package com.leon.item.service;

import com.leon.item.pojo.Brand;
import com.leon.common.pojo.BrandQueryByPageParameter;
import com.leon.common.pojo.PageResult;

import java.util.List;

/**
 * @author LeonMac
 * @description
 */

public interface BrandService {
    /*
    * @description:
    * 根据品牌Id集合查询品牌信息
    * @param ids
    * @return java.util.List<com.leon.order.pojo.Brand>
    */
    List<Brand> queryBrandByBrandIds(List<Long> ids);

    /*
    * @description:
    * 根据分类Id查询brand
    * @param id
    * @return java.util.List<com.leon.order.pojo.Brand>
    */
    List<Brand> queryBrandByCategoryId(Long cid);

    /*
    * @description:
    * 新增brand，并维护中间表
    * @param brand
	* @param cids
    * @return void
    */
    void saveBrand(Brand brand, List<Long> cids);

    /*
    * @description:
    * 根据品牌Id删除中间表的数据
    * @param bid
    * @return void
    */
    void deleteByBrandIdInCategoryBrand(Long bid);

    /*
    * @description:
    * 删除brand,并维护中间表
    * @param id
    * @return void
    */
    void deleteBrand(Long id);

    /*
    * @description:
    * 修改brand,并维护中间表
    * @param brand
	* @param cids
    * @return void
    */
    void updateBrand(Brand brand, List<Long> cids);

    /*
    * @description:
    * 分页查询
    * @param brandQueryByPageParameter
    * @return com.leon.order.pojo.PageResult<com.leon.order.pojo.Brand>
    */
    PageResult<Brand> queryBrandByPage(BrandQueryByPageParameter brandQueryByPageParameter);





}
