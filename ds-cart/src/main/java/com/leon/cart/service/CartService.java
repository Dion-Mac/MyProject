package com.leon.cart.service;

import com.leon.cart.pojo.Cart;

import java.util.List;

/**
 * @author LeonMac
 * @description
 */

public interface CartService {
    /**
    * @description: 添加入购物车
    * @param cart
    * @return void
    */
    void addCart(Cart cart);

    /**
    * @description: 查询购物车
    * @param
    * @return java.util.List<com.leon.cart.pojo.Cart>
    */
    List<Cart> queryCartList();

    /**
    * @description: 更新购物车中商品数量
    * @param skuId
	* @param num
    * @return void
    */
    void updateNum(Long skuId, Integer num);

    /**
    * @description: 删除购物车中的商品
    * @param skuId
    * @return void
    */
    void deleteCart(String skuId);
}
