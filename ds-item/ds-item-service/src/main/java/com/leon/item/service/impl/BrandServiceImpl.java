package com.leon.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leon.item.mapper.BrandMapper;
import com.leon.item.pojo.Brand;
import com.leon.common.pojo.BrandQueryByPageParameter;
import com.leon.common.pojo.PageResult;
import com.leon.item.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author LeonMac
 * @description
 */

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /*
    * @description:
    * 根据品牌Id集合查询品牌信息
    * @param ids
    * @return java.util.List<com.leon.order.pojo.Brand>
    */
    @Override
    public List<Brand> queryBrandByBrandIds(List<Long> ids) {
        return this.brandMapper.selectByIdList(ids);
    }

    /*
    * @description:
    * 根据分类Id查询品牌
    * @param cid
    * @return java.util.List<com.leon.order.pojo.Brand>
    */
    @Override
    public List<Brand> queryBrandByCategoryId(Long cid) {
        return this.brandMapper.selectBrandByCid(cid);
    }

    /*
     * @description:
     * 新增品牌信息
     * @param brand
     * @param cids
     * @return void
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增品牌信息
        this.brandMapper.insertSelective(brand);
        //新增品牌和分类中间表
        for (Long cid : cids) {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        }
    }

    /*
    * @description:
    * 删除中间表数据
    * @param bid
    * @return void
    */
    @Override
    public void deleteByBrandIdInCategoryBrand(Long bid) {
        this.brandMapper.deleteByBrandIdInCategoryBrand(bid);
    }

    /*
    * @description:
    * 品牌删除
    * @param id
    * @return void
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBrand(Long id) {
        //删除品牌信息
        this.brandMapper.deleteByPrimaryKey(id);
        //维护中间表
        this.brandMapper.deleteByBrandIdInCategoryBrand(id);
    }

    /*
     * @description:
     * 品牌更新
     * @param brand
     * @param cids
     * @return void
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(Brand brand, List<Long> cids) {
        //删除原来的数据
        deleteByBrandIdInCategoryBrand(brand.getId());
        //修改品牌信息
        this.brandMapper.updateByPrimaryKeySelective(brand);
        //维护品牌和分类中间表
        for (Long cid : cids) {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        }
    }

    /*
    * @description:
    * 分页查询
    * @param brandQueryByPageParameter
    * @return com.leon.order.pojo.PageResult<com.leon.order.pojo.Brand>
    */
    @Override
    public PageResult<Brand> queryBrandByPage(BrandQueryByPageParameter brandQueryByPageParameter) {
        //1.分页
        PageHelper.startPage(brandQueryByPageParameter.getPage(), brandQueryByPageParameter.getRows());
        //2.排序
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(brandQueryByPageParameter.getSortBy())) {
            example.setOrderByClause(brandQueryByPageParameter.getSortBy() + (brandQueryByPageParameter.getDesc() ? "DESC" : "ASC"));
        }
        //3.查询
        if (StringUtils.isNotBlank(brandQueryByPageParameter.getKey())) {
            example.createCriteria().orLike("name", brandQueryByPageParameter.getKey() + "%").orEqualTo("letter", brandQueryByPageParameter.getKey().toUpperCase());
        }
        List<Brand> list = this.brandMapper.selectByExample(example);
        //4.创建PageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        //5.返回分页结果
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
