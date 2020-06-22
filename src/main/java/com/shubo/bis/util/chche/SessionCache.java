package com.shubo.bis.util.chche;

import com.shubo.bis.util.ObjectUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/15,14:07
 */
public class SessionCache extends BaseCache<String,SessionCache> {

    @Override
    public byte[] getSingleKey(String key) {
        return ("Session:"+key).getBytes();
    }

    @Override
    public byte[] value2Byte(SessionCache var1) {
        if(var1 ==null){
            return null;
        }
        return ObjectUtils.object2Byte(var1);
    }

    @Override
    public SessionCache byte2Value(byte[] var1) {
        if(ArrayUtils.isEmpty(var1)){
            return null;
        }
        return ObjectUtils.byte2Object(var1);
    }
}
