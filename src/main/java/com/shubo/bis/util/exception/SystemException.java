package com.shubo.bis.util.exception;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/10,15:39
 * 系统错误类
 */
public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 8870734814939563760L;

    public SystemException(String msg) {
        super(msg);
    }

    public SystemException() {
        super();
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}