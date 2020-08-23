package com.xiaojiaqi.learn.order;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @Author: liangjiaqi
 * @Date: 2020/8/23 9:47 AM
 */
public class Producer {
    public static void main(String[] args) throws MQClientException {
        //1.实例化生产者
        DefaultMQProducer producer = new DefaultMQProducer("learn_producer");
        //2.配置nameServer
        producer.setNamesrvAddr("192.168.123.98:9876");
        producer.setVipChannelEnabled(false);
        //3.启动Producer
        producer.start();

        //4.发送消息

        List<OrderStep> orderStepList = OrderStep.buildOrders();

        for(OrderStep order : orderStepList){
            producer.send(msg, new MessageQueueSelector() {
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {


                    return null;
                }
            }, orderId);

        };

        producer.shutdown();
    }
}
