package com.leon.item.service;

import com.leon.common.exception.MyException;
import com.leon.item.pojo.Category;

import java.util.List;

/**
 * @author LeonMac
 * @description
 */

public interface CategoryService {

    /**
    * @description: 根据父Id查询分类
    * @param pid
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    List<Category> queryCategoryByPid(Long pid) throws MyException;

    /**
    * @description: 根据品牌Id查询分类信息
    * @param bid
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    List<Category> queryByBrandId(Long bid);

    /**
    * @description: 保存分类信息
    * @param category
    * @return void
    */
    void saveCategory(Category category);

    /**
    * @description: 更新分类信息
    * @param category
    * @return void
    */
    void updateCategory(Category category);

    /**
    * @description: 删除分类信息
    * @param id
    * @return void
    */
    void deleteCategory(Long id);

    /**
    * @description: 根据ids查询分类名称信息
    * @param asList
    * @return java.util.List<java.lang.String>
    */
    List<String> queryNameByIds(List<Long> asList);

    /**
    * @description: 查询当前数据库中最后一条数据
    * @param
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    List<Category> queryLast();

    /**
    * @description: 根据分类Id集合查询分类信息
    * @param ids
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    List<Category> queryCategoryByIds(List<Long> ids);

    /**
    * @description: 根据最小子分类Id查询所有层级分类
    * @param id
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    List<Category> queryAllCategoryLevelByMinCid(Long id);




}
