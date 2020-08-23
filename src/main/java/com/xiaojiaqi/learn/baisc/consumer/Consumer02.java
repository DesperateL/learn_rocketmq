package com.xiaojiaqi.learn.baisc.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 消息的接收者
 * @Author: liangjiaqi
 * @Date: 2020/8/22 8:39 PM
 */
public class Consumer02 {
    public static void main(String[] args) throws Exception {
        // 1. 创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 2. 指定nameServer地址
        consumer.setNamesrvAddr("192.168.123.98:9876");
        consumer.setVipChannelEnabled(false);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //3. 订阅主题Topic
        consumer.subscribe("LearnTopic","*");

        // 设定消费模式：cluster | broadcast

//        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        //4. 设置回调函数，消费消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            // 接收消息内容
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 5.启动消费者consumer
        consumer.start();
    }
}
