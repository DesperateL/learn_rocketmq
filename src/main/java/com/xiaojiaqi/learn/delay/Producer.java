package com.xiaojiaqi.learn.delay;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

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
        for(int i=0;i<10;i++){

            Message msg = new Message("DelayTopic","TagA",("Hello RocketMQ "+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 设定延时时间
            msg.setDelayTimeLevel(2);
            SendResult send = producer.send(msg);
            System.out.println(i+":"+send);

            Thread.sleep(1000);
        }

        producer.shutdown();
    }
}
