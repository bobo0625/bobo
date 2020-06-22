package com.shubo.bis.service;

import com.alibaba.fastjson.JSONObject;
import com.shubo.base.dao.UserInfo;
import com.shubo.base.mapper.UserManageMapper;
import com.shubo.bis.constants.KafKaTopics;
import com.shubo.bis.util.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/4,17:01
 */
@Service
public class UerManageService {

    @Autowired
    private UserManageMapper userManageMapper;

    public UserInfo findById(int id){
        UserInfo info=new UserInfo();
        try{
            info= userManageMapper.findById(id);
            //测试线程池
            ThreadPoolManager.newInstance().addTask(new Runnable() {
                @Override
                public void run() {
                    //TODO
                }
            });
            JSONObject jsonParam=new JSONObject();

        }catch (Exception e){
            //BaseLog.getDailyLog().info("");
        }
        return info;
    }
    /**
    * @param getData
    * @return void
    * @Description: 测试kafka工具类
    * @author ruanshubo
    * @date 2020/6/12 10:28
    */
    public void pushMessage(JSONObject getData){
        //发送消息主题
        try {
            new SendTask(KafKaTopics.TPOIC_TEST, getData.toJSONString()).taskProducer();
        } catch (Exception e) {
           // BaseLog.getErrorLog().info("testKafka消息发送失败,请求参数为：{}",getData);
        }
        //消费消息
        try {
            new KafkaHandler(KafKaTopics.GROUP_ID,3,KafKaTopics.TPOIC_TEST);
        } catch (Exception e) {
           // BaseLog.getErrorLog().info("消费消息主题为：testKafka失败");
        }
    }
}
