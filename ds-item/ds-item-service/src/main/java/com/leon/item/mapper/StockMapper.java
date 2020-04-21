package com.leon.item.mapper;

import com.leon.item.pojo.Stock;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author LeonMac
 * @description
 */
public interface StockMapper extends Mapper<Stock>, SelectByIdListMapper<Stock, Long> {
}
