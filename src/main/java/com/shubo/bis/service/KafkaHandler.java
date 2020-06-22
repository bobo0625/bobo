package com.shubo.bis.service;

import com.alibaba.fastjson.JSONObject;
import com.shubo.bis.util.kafka.KafkaClient;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/12,10:21
 */
public class KafkaHandler extends KafkaClient<String, String> {

    /**
     * @param groupId, threadNum, topic
     * @return
     * @Description: kafka消息的实际使用方法
     * 通常topic内有几个partition就使用几个线程
     * @author ruanshubo
     * @date 2020/6/15 14:45
     */
    public KafkaHandler(String groupId, int threadNum, String topic) {
        super(groupId, threadNum, topic);
    }

    @Override
    public void callBack(Object var1, Object var2) {
        JSONObject getData = (JSONObject) var2;
        //todo
    }
}
