package com.leon.item.service.impl;

import com.leon.item.mapper.SpecificationMapper;
import com.leon.item.pojo.Specification;
import com.leon.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LeonMac
 * @description
 */

public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public Specification queryById(Long id) {
        return this.specificationMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveSpecification(Specification specification) {
        this.specificationMapper.insert(specification);
    }

    @Override
    public void updateSpecification(Specification specification) {
        this.specificationMapper.updateByPrimaryKeySelective(specification);
    }

    @Override
    public void deleteSpecification(Specification specification) {
        this.specificationMapper.deleteByPrimaryKey(specification);
    }
}
