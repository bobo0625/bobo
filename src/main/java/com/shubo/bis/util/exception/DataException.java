package com.shubo.bis.util.exception;

import java.io.Serializable;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/15,13:54
 */
public class DataException extends RuntimeException implements Serializable {

    public DataException(String msg) {
        super(msg);
    }

    public DataException() {
        super();
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}
