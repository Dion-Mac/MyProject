package com.leon.web.service;

import java.util.concurrent.ExecutionException;

/**
 * @author LeonMac
 * @description 页面详情静态化接口
 */

public interface GoodsHtmlService {
    /**
    * @description: 创建html界面
    * @param spuId
    * @return void
    */
    void createHtml(Long spuId) throws InterruptedException, ExecutionException;

    /**
    * @description: 新建线程处理页面静态化，Controller调用
    * @param spuId
    * @return void
    */
    void asyncExecute(Long spuId);

    /**
    * @description: 删除html页面
    * @param id
    * @return void
    */
    void deleteHtml(Long id);
}
