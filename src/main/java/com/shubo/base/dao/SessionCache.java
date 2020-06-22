package com.shubo.base.dao;

import java.io.Serializable;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/15,13:56
 */
public class SessionCache implements Serializable {
    /**
     * 用户id
     */
    private long uaid;
    /**
     * 用户类型
     */
    private int type;
    /**
     * 用户登录的seq
     */
    private long seq;
    /**
     * 设置用户登录session的缓存失效时间
     */
    private int session_timeout;

    public SessionCache() {
    }

    public long getUaid() {
        return uaid;
    }

    public void setUaid(long uaid) {
        this.uaid = uaid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public int getSession_timeout() {
        return session_timeout;
    }

    public void setSession_timeout(int session_timeout) {
        this.session_timeout = session_timeout;
    }

    @Override
    public String toString() {
        return "SessionCache{" +
                "uaid=" + uaid +
                ", type=" + type +
                ", seq=" + seq +
                ", session_timeout=" + session_timeout +
                '}';
    }
}
