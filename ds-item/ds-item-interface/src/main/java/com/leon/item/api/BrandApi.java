package com.leon.item.api;

import com.leon.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author LeonMac
 * @description 品牌服务接口
 */
@RequestMapping("brand")
public interface BrandApi {
    /**
    * @description: 根据品牌Id集合，查询品牌信息
    * @param ids
    * @return com.leon.item.pojo.Brand
    */
    @GetMapping("list")
    List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);
}
