package com.leon.item.service;

import com.leon.item.pojo.Specification;

/**
 * @author LeonMac
 * @description
 */

public interface SpecificationService {
    /**
    * @description: 根据分类Id查询规格参数模板
    * @param id
    * @return com.leon.order.pojo.Specification
    */
    Specification queryById(Long id);

    /**
    * @description: 添加规格参数模板
    * @param specification
    * @return void
    */
    void saveSpecification(Specification specification);

    /**
    * @description: 修改规格参数模板
    * @param specification
    * @return void
    */
    void updateSpecification(Specification specification);

    /**
    * @description: 删除规格参数模板
    * @param specification
    * @return void
    */
    void deleteSpecification(Specification specification);
}
