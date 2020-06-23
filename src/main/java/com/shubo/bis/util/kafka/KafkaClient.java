package com.shubo.bis.util.kafka;

import com.shubo.bis.util.cache.BaseLog;
import com.shubo.bis.util.exception.SystemException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/12,9:01
 * kafka客户端
 */
public abstract class KafkaClient<K, V> {
    private String groupId;
    private int threadNum = 1;
    private String topic;
    private static KafkaConsumer<String, String> consumer = null;

    public KafkaClient(String groupId, int threadNum, String topic) {
        if (!StringUtils.isEmpty(groupId) && !StringUtils.isEmpty(topic)) {
            this.groupId = groupId;
            this.threadNum = threadNum <= 0 ? 1 : threadNum;
            this.topic = topic;
            this.init();
        } else {
            throw new SystemException("消息中间件:需要接受的topic或者groupId为空.请检查配置...");
        }
    }

    private void init() {
        Properties consumerConfig = this.createconfig();
        consumer = new KafkaConsumer(consumerConfig);
    }

    private Properties createconfig() {
        Properties props = new Properties();
//        props.put("zookeeper.connect", ParamCache.getLocalParam("zk.ip"));
        props.put("group.id", this.groupId);
//        props.put("client.id", this.groupId + "-" + OSUtil.getLocalIP());
        props.put("auto.commit.interval.ms", "1000");
        props.put("zookeeper.session.timeout.ms", "5000");
        props.put("zookeeper.connection.timeout.ms", "10000");
        props.put("rebalance.backoff.ms", "2000");
        props.put("rebalance.max.retries", "10");
        props.put("auto.offset.reset", "smallest");
        props.put("partition.assignment.strategy", "roundrobin");
        return props;
    }

    /**
     * 执行方法
     */
    public void run() {
        ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
        ExecutorService executor = Executors.newFixedThreadPool(this.threadNum);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Iterator<ConsumerRecord<String, String>> it = consumerRecords.iterator();
                ConsumerRecord record = null;
                while (it.hasNext()) {
                    record = it.next();
                    KafkaClient.this.readCallBack(record.key(), record.value());
                }
            }
        });
    }


    private void readCallBack(Object key, Object message) {
        BaseLog.getSystemLog().info("收到topic[" + this.topic + "]key[" + key + "]message[" + message + "]的消息。");
        this.callBack(key, message);
    }

    /**
     * 回调函数
     * @param var1
     * @param var2
     */
    public abstract void callBack(Object var1, Object var2);

}
