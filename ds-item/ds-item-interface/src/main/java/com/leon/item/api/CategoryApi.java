package com.leon.item.api;

import com.leon.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author LeonMac
 * @description
 */

@RequestMapping("/category")
public interface CategoryApi {

    /**
    * @description: 根据Id集合查询分类名称
    * @param ids
    * @return java.util.List<java.lang.String>
    */
    @GetMapping("names")
    List<String> queryNamesByIds(@RequestParam("ids") List<Long> ids);

    /**
    * @description: 根据分类Id集合查询分类
    * @param ids
    * @return java.util.List<com.leon.item.pojo.Category>
    */
    @GetMapping("all")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
