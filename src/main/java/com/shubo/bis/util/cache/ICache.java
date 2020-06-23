package com.shubo.bis.util.cache;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/15,13:46
 */
public interface ICache<K, V> {
        byte[] getSingleKey(K var1) ;

        byte[] value2Byte(V var1);

        V byte2Value(byte[] var1) ;
}
