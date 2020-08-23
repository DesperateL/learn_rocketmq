package com.xiaojiaqi.learn.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Author: Gary Leung
 * @Date: 8/23/20 11:09 AM
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        //1.实例化生产者
        TransactionMQProducer producer = new TransactionMQProducer("tx_producer");
        //2.配置nameServer
        producer.setNamesrvAddr("192.168.123.98:9876");

        producer.setTransactionListener(new TransactionListener() {
            // 该方法中执行本地事物
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                System.out.println("executeLocalTransaction:"+msg.getTags());
                if(StringUtils.equals("TAGA",msg.getTags())){
                    return LocalTransactionState.COMMIT_MESSAGE;
                }else if(StringUtils.equals("TAGB",msg.getTags())){
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }else {
                    return LocalTransactionState.UNKNOW;
                }
            }

            // 该方法中回查消息
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("checkLocalTransaction:"+msg.getTags());

                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        producer.setVipChannelEnabled(false);
        //3.启动Producer
        producer.start();

        String[] tags = {"TAGA","TAGB","TAGC"};
        //4.发送消息
        for(int i=0;i<3;i++){
            Message msg1 = new Message("TransactionTopic",tags[i],("Hello RocketMQ "+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 设定延时时间
            TransactionSendResult sendResult = producer.sendMessageInTransaction(msg1, null);
            System.out.println(":"+sendResult);

            Thread.sleep(100);
        }


        //producer.shutdown();
    }
}
