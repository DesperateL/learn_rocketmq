package com.xiaojiaqi.learn.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

/**
 * 发送单向消息
 * @Author: liangjiaqi
 * @Date: 2020/8/22 5:38 PM
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception{
        //1.实例化生产者
        DefaultMQProducer producer = new DefaultMQProducer("learn_producer");
        //2.配置nameServer
        producer.setNamesrvAddr("192.168.123.98:9876");
        producer.setVipChannelEnabled(false);
        //3.启动Producer
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        //4.发送消息
        for(int i=0;i<10;i++){

            Message msg = new Message("LearnTopic","Tag3",
                    "oneWay",("Hello RocketMQ "+i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            producer.sendOneway(msg);
            TimeUnit.MILLISECONDS.sleep(500);
        }

        producer.shutdown();
    }
}
