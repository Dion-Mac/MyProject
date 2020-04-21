package com.leon.item.api;

import com.leon.item.bo.SpuBo;
import com.leon.common.pojo.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author LeonMac
 * @description
 */

@RequestMapping("spu")
public interface SpuApi {

    @GetMapping("page")
    PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "saleable", defaultValue = "true") Boolean saleabl);

}
