package com.xiaojiaqi.learn.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Gary Leung
 * @Date: 8/23/20 11:09 AM
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        //1.实例化生产者
        DefaultMQProducer producer = new DefaultMQProducer("learn_producer");
        //2.配置nameServer
        producer.setNamesrvAddr("192.168.123.98:9876");
        producer.setVipChannelEnabled(false);
        //3.启动Producer
        producer.start();
        //4.发送消息
        List<Message> msgs = new LinkedList<Message>();

        Message msg1 = new Message("BatchTopic","TagA",("Hello RocketMQ "+1).getBytes(RemotingHelper.DEFAULT_CHARSET));
        Message msg2 = new Message("BatchTopic","TagA",("Hello RocketMQ "+2).getBytes(RemotingHelper.DEFAULT_CHARSET));
        Message msg3 = new Message("BatchTopic","TagA",("Hello RocketMQ "+3).getBytes(RemotingHelper.DEFAULT_CHARSET));
        msgs.add(msg1);
        msgs.add(msg2);
        msgs.add(msg3);
        // 设定延时时间
        SendResult send = producer.send(msgs);
        System.out.println(":"+send);

        Thread.sleep(1000);

        producer.shutdown();
    }
}
