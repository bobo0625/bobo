package com.shubo.base.dao;

import java.io.Serializable;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/15,14:53
 */
public class UserInfo implements Serializable {
    private int uaId;
    private int age;

    public UserInfo() {

    }

    public int getUaId() {
        return uaId;
    }

    public void setUaId(int uaId) {
        this.uaId = uaId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
