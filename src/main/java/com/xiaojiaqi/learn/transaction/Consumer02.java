package com.xiaojiaqi.learn.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Author: Gary Leung
 * @Date: 8/23/20 11:09 AM
 */
public class Consumer02 {
    public static void main(String[] args) throws Exception {
        // 1. 创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1TXTopic");
        // 2. 指定nameServer地址
        consumer.setNamesrvAddr("192.168.123.98:9876");
        consumer.setVipChannelEnabled(false);
        consumer.setConsumeMessageBatchMaxSize(3);
        //3. 订阅主题Topic
        consumer.subscribe("TransactionTopic","*");

        // 设定消费模式：cluster | broadcast

//        consumer.setMessageModel(MessageModel.BROADCASTING);
        //4. 设置回调函数，消费消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            // 接收消息内容
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for(MessageExt msg: msgs){
                    System.out.println("消息ID：【"+msg.getMsgId()+"】"+
                            "延迟时间："+(System.currentTimeMillis()-msg.getStoreTimestamp())+"body:"+
                            new String(msg.getBody())+"TAG:"+msg.getTags());

                }
                System.out.println("===================================");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 5.启动消费者consumer
        consumer.start();
        System.out.println("consumer02启动");
    }
}
