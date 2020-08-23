package com.xiaojiaqi.learn.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @Author: Gary Leung
 * @Date: 8/23/20 10:11 AM
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        // 1. 创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 2. 指定nameServer地址
        consumer.setNamesrvAddr("192.168.123.98:9876");
        consumer.setVipChannelEnabled(false);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //3. 订阅主题Topic
        consumer.subscribe("OrderTopic","*");

        // 设定消费模式：cluster | broadcast

//        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        //4. 设置回调函数，消费消息 (orderly)
        consumer.registerMessageListener(new MessageListenerOrderly() {
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt msg: list){
                    System.out.println("线程名称：["+Thread.currentThread().getName()+"]"+"消费消息："+ new String(msg.getBody()));
                }
                System.out.println("============================");
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        // 5.启动消费者consumer
        consumer.start();
    }
}
