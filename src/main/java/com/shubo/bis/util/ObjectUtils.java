package com.shubo.bis.util;

import com.shubo.bis.util.cache.BaseLog;

import java.io.*;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/15,10:57
 * 序列化与反序列化类
 */
public class ObjectUtils {
    public ObjectUtils() {

    }

    /**
     * 序列号成字节流
     * @param object
     * @return
     */
    public static<T> byte[] object2Byte(T object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        Object var4;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            byte[] var3 = bos.toByteArray();
            return var3;
        } catch (IOException e) {
            BaseLog.getErrorLog().error("Serializ object exception", e);
            var4 = null;
        } finally {
            ObjectUtils.closeIO(oos);
            ObjectUtils.closeIO(bos);
        }
        return (byte[]) var4;
    }

    /**
     * 反序列化字节流
     * @param bytes
     * @return
     */
    public static <T> T byte2Object(byte[] bytes) {
        Object var4;
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            Object var3 = ois.readObject();
            return (T) var3;
        } catch (Exception e) {
            BaseLog.getErrorLog().error("Serializ bytes exception", e);
            var4 = null;
        } finally {
            ObjectUtils.closeIO(bis);
            ObjectUtils.closeIO(ois);
        }
        return (T) var4;
    }

    public static void closeIO(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException var2) {
            }
        }
    }
}
