package com.leon.web.listener;

import com.leon.web.service.GoodsHtmlService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LeonMac
 * @description mq监听器，消费者
 */

@Component
public class GoodsListener {
    @Autowired
    private GoodsHtmlService goodsHtmlService;

    /**
    * @description: 处理insert和update的消息
    * @param id
    * @return void
    */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leon.create.web.queue",durable = "true"), //队列持久化
            exchange = @Exchange(
                    value = "leon.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"item.insert","item.update"}
    ))
    public void listenCreate(Long id) throws Exception{
        if (id == null){
            return;
        }
        //创建或更新索引
        this.goodsHtmlService.createHtml(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leon.delete.web.queue",durable = "true"), //队列持久化
            exchange = @Exchange(
                    value = "leon.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"item.delete"}
    ))
    public void listenDelete(Long id){
        if (id == null){
            return;
        }

        //删除索引
        this.goodsHtmlService.deleteHtml(id);
    }
}
