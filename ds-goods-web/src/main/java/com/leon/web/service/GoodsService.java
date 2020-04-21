package com.leon.web.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author LeonMac
 * @description 商品详情页后台
 */

public interface GoodsService {
    /**
    * @description: 商品详细信息
    * @param spuId
    * @return java.util.Map<java.lang.String,java.lang.Object>
    */
    Map<String,Object> loadModel(Long spuId) throws InterruptedException, ExecutionException;
}
