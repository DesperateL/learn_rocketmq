package com.xiaojiaqi.learn.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * @Author: liangjiaqi
 * @Date: 2020/8/23 9:47 AM
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //1.实例化生产者
        DefaultMQProducer producer = new DefaultMQProducer("Order");
        //2.配置nameServer
        producer.setNamesrvAddr("sr-test-rockermq-1.gz.cvte.cn:9876");
        producer.setVipChannelEnabled(false);
        //3.启动Producer
        producer.start();

        //4.发送消息

        List<OrderStep> orderStepList = OrderStep.buildOrders();

        for(final OrderStep order : orderStepList){
            Message msg = new Message("OrderTopicTest","Order",order.toString().getBytes());

            SendResult send = producer.send(msg, new MessageQueueSelector() {
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    long id = (Long) arg;
                    long index = id % mqs.size();

                    return mqs.get((int) index);
                }
            }, order.getOrderId());

            System.out.println("发送结果："+send);

        };

        producer.shutdown();
    }
}
