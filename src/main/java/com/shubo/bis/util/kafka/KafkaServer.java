package com.shubo.bis.util.kafka;

import com.alibaba.fastjson.JSONObject;
import com.shubo.bis.util.cache.BaseLog;
import com.shubo.bis.util.exception.SystemException;
import kafka.producer.KeyedMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/10,15:17
 * kafka服务端
 */
public abstract class KafkaServer<K, V> {
    public KafkaServer() {
    }

    private KafkaProducer<String, String> producer = null;

    private void init() {
        producer = new KafkaProducer<String, String>(this.getProducerConfig());
    }

    private Properties getProducerConfig() {
        Properties props = new Properties();
        String brokerList = this.getIdStr();
        if (StringUtils.isEmpty(brokerList)) {
            throw new SystemException("zk上没有找到kafka服务器...");
        } else {
            props.put("metadata.broker.list", this.getIdStr());
            props.put("request.required.acks", "1");
            props.put("request.timeout.ms", "500");
            props.put("message.send.max.retries", "1");
            props.put("topic.metadata.refresh.interval.ms", "-1");
            props.put("producer.type", "sync");
            return props;
        }
    }

    ;

    protected void sendMessage(KeyedMessage<K, V> message) {
        if (null == message) {
            return;
        }
        long start = System.currentTimeMillis();
        if (this.producer == null) {
            this.init();
        }
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(message.topic(), message.key().toString(), message.message().toString());
        try {
            this.producer.send(record);
        } catch (Exception e) {
            throw new SystemException("kafka发送消息topic[" + message.topic() + "]partKey[" + message.key() + "]message[" + message.message() + "]异常...", e);
        } finally {
            //统计发送消息执行时间
            BaseLog.getDailyLog().info("kafka发送消息topic[" + message.topic() + "]partKey[" + message.key() + "]message[" + message.message() + "]耗时[" + (System.currentTimeMillis() - start) + "]");
        }

    }


    public String getIdStr() {
        //TOdo  List<String> borkerIds = ZKClientManager.getChildren("/brokers/ids");
        List<String> borkerIds = new ArrayList<>();
        if (CollectionUtils.isEmpty(borkerIds)) {
            return null;
        } else {
            StringBuffer allIdStr = new StringBuffer();

            for (int i = 0; i < borkerIds.size(); ++i) {
                String id = (String) borkerIds.get(i);
                //TODO  byte[] nodeDataByte = ZKClientManager.readData("/brokers/ids/" + id);
                byte[] nodeDataByte = new byte[1024];
                if (!ArrayUtils.isEmpty(nodeDataByte)) {
                    try {
                        JSONObject jo = JSONObject.parseObject(new String(nodeDataByte, "UTF-8"));
                        String host_port = jo.getString("host") + ":" + jo.getIntValue("port");
                        allIdStr.append(host_port);
                    } catch (UnsupportedEncodingException var7) {
                        continue;
                    }
                    if (i < borkerIds.size() - 1) {
                        allIdStr.append(",");
                    }
                }
            }
            return allIdStr.toString();
        }
    }
}
