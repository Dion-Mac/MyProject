package com.leon.item.service.impl;

import com.leon.common.exception.DsException;
import com.leon.common.exception.MyException;
import com.leon.item.mapper.CategoryMapper;
import com.leon.item.service.CategoryService;
import com.leon.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LeonMac
 * @description
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
    * @description: 根据父节点查询分类
    * @param pid
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    @Override
    public List<Category> queryCategoryByPid(Long pid) throws MyException {
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId", pid);
        List<Category> list = this.categoryMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new MyException(DsException.CATEGORY_NOT_FOUND);
        }
        return list;
    }

    /**
    * @description: 根据品牌id查询分类
    * @param bid
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    @Override
    public List<Category> queryByBrandId(Long bid) {
        return this.categoryMapper.selectCategoryByBid(bid);
    }

    /**
    * @description: 新增分类信息
     * 1.将本节点插入到数据库
     * 2.将此分类的父节点的isParent设为true
    * @param category
    * @return void
    */
    @Override
    public void saveCategory(Category category) {
        //1.首先置id为null
        category.setId(null);
        //2.保存
        this.categoryMapper.insert(category);
        //3.修改父节点
        Category parent = new Category();
        parent.setId(category.getParentId());
        parent.setIsParent(true);
        this.categoryMapper.updateByPrimaryKeySelective(parent);
    }

    /**
    * @description: 更新分类信息
    * @param category
    * @return void
    */
    @Override
    public void updateCategory(Category category) {
        this.categoryMapper.updateByPrimaryKeySelective(category);
    }

    /**
    * @description: 删除分类信息
     * 先根据id查询要删除的对象，然后进行判断
     * 如果是父节点，那么删除所有附带子节点,然后维护中间表
     * 如果是子节点，那么只删除自己,然后判断父节点孩子的个数，
     * 如果孩子不为0，则不做修改；如果孩子个数为0，则修改父节点isParent的值为false,
     * 最后维护中间表
    * @param id
    * @return void
    */
    @Override
    public void deleteCategory(Long id) {
        Category category=this.categoryMapper.selectByPrimaryKey(id);
        if(category.getIsParent()){
            //1.查找所有叶子节点
            List<Category> list = new ArrayList<>();
            queryAllLeafNode(category,list);

            //2.查找所有子节点
            List<Category> list2 = new ArrayList<>();
            queryAllNode(category,list2);

            //3.删除tb_category中的数据,使用list2
            for (Category c:list2){
                this.categoryMapper.delete(c);
            }

            //4.维护中间表
            for (Category c:list){
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(c.getId());
            }

        }else {
            //1.查询此节点的父亲节点的孩子个数 ===> 查询还有几个兄弟
            Example example = new Example(Category.class);
            example.createCriteria().andEqualTo("parentId",category.getParentId());
            List<Category> list=this.categoryMapper.selectByExample(example);
            if(list.size()!=1){
                //有兄弟,直接删除自己
                this.categoryMapper.deleteByPrimaryKey(category.getId());

                //维护中间表
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(category.getId());
            }
            else {
                //已经没有兄弟了
                this.categoryMapper.deleteByPrimaryKey(category.getId());

                Category parent = new Category();
                parent.setId(category.getParentId());
                parent.setIsParent(false);
                this.categoryMapper.updateByPrimaryKeySelective(parent);
                //维护中间表
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(category.getId());
            }
        }
    }

    /**
    * @description: 根据ids查询分类名字
    * @param asList
    * @return java.util.List<java.lang.String>
    */
    @Override
    public List<String> queryNameByIds(List<Long> asList) {
        ArrayList<String > names = new ArrayList<>();
        if (asList != null && asList.size() != 0) {
            for (Long id : asList) {
                names.add(this.categoryMapper.queryNameById(id));
            }
        }
        return names;

    }

    /**
    * @description: 查询数据库中最后一条数据
    * @param
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    @Override
    public List<Category> queryLast() {
        List<Category> last = this.categoryMapper.selectLast();
        return last;
        //使用通用mapper接口中的SelectByIdListMapper接口查询
        //return this.categoryMapper.selectByIdList(asList)
        // .stream().map(Category::getName).collect(Collectors.toList());
    }

    /**
    * @description: 根据分类Id结合查询分类集合
    * @param ids
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    @Override
    public List<Category> queryCategoryByIds(List<Long> ids) {
        return this.categoryMapper.selectByIdList(ids);
    }

    /**
    * @description: 根据最小分类Id查询其所有层级的分类
    * @param id
    * @return java.util.List<com.leon.order.pojo.Category>
    */
    @Override
    public List<Category> queryAllCategoryLevelByMinCid(Long id) {
        List<Category> categoryList = new ArrayList<>();
        Category category = this.categoryMapper.selectByPrimaryKey(id);
        while (category.getParentId() != 0){
            categoryList.add(category);
            category = this.categoryMapper.selectByPrimaryKey(category.getParentId());
        }
        categoryList.add(category);
        return categoryList;
    }

    /**
    * @description: 查询本节点下所包含的所有叶子节点，用于维护tb_category_brand中间表
    * @param category
	* @param leafNode
    * @return void
    */
    private void queryAllLeafNode(Category category,List<Category> leafNode){
        if(!category.getIsParent()){
            leafNode.add(category);
        }
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list=this.categoryMapper.selectByExample(example);

        for (Category category1:list){
            queryAllLeafNode(category1,leafNode);
        }
    }

    /**
    * @description: 查询本节点下所有子节点
    * @param category
	* @param node
    * @return void
    */
    private void queryAllNode(Category category,List<Category> node){

        node.add(category);
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list=this.categoryMapper.selectByExample(example);

        for (Category category1:list){
            queryAllNode(category1,node);
        }
    }
}
