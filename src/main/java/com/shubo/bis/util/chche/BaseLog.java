package com.shubo.bis.util.chche;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/19,11:05
 */
public class BaseLog {
    private static Logger dailyLog = LoggerFactory.getLogger("DailyLog");
    private static Logger errorLog = LoggerFactory.getLogger("ErrorLog");
    private static Logger systemLog = LoggerFactory.getLogger("SystemLog");

    public BaseLog() {
    }

    public static Logger getDailyLog() {
        return dailyLog;
    }

    public static Logger getErrorLog() {
        return errorLog;
    }

    public static Logger getSystemLog() {
        return systemLog;
    }
}
