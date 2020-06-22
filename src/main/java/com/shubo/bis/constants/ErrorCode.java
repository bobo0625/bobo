package com.shubo.bis.constants;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/12,13:59
 */
public enum ErrorCode {

    PRAAM_ERROR(01001, "请求参数错误"),
    THREAD_POOL_FULL_ERROR(01002,"线程池已满");

    private int code;
    private String desc;

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
