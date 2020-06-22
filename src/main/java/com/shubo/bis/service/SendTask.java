package com.shubo.bis.service;

import com.shubo.bis.util.kafka.KafkaServer;
import kafka.producer.KeyedMessage;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/11,17:23
 */
public class SendTask extends KafkaServer<String,String> {

    private String topic;

    private String taskParams;

    public SendTask(String topic, String taskParams) {
        super();
        this.topic = topic;
        this.taskParams = taskParams;
    }

    public void taskProducer(){
        KeyedMessage<String, String> ks = new KeyedMessage<String, String>(topic, taskParams);
        this.sendMessage(ks);
    }
}
