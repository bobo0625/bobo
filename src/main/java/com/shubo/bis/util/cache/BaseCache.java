package com.shubo.bis.util.cache;

import com.shubo.bis.util.exception.SystemException;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/15,10:37
 * 基础操作缓存类（redis）
 */
public abstract class BaseCache<K, V> implements ICache<K, V> {
    public BaseCache() {
    }

    /**
     * @param key, timeout
     * @return void
     * @Description: 设置过期时间
     * @author ruanshubo
     * @date 2020/6/15 14:27
     */
    public void expire(K key, int timeout) {
        if (StringUtils.isEmpty(key)) {
            throw new SystemException("设置缓存key[" + String.valueOf(this.getSingleKey(key)) + "]超时时间参数错误");
        }
        Jedis jedis = this.getJedis();
        try {
            jedis.expire(this.getSingleKey(key), timeout);
            BaseLog.getDailyLog().info("设置缓存key：{}的过期时间为：{}" + key, timeout);
        } catch (Exception e) {
            throw new SystemException("设置缓存key[" + String.valueOf(this.getSingleKey(key)) + "]超时时间参数错误", e);
        } finally {
            jedis.close();
        }
    }

    /**
     * @param key
     * @return boolean
     * @Description: 判断是否存在key
     * @author ruanshubo
     * @date 2020/6/15 14:27
     */
    public boolean exists(K key) {
        if (StringUtils.isEmpty(key)) {
            throw new SystemException("判断缓存key[" + String.valueOf(this.getSingleKey(key)) + "]参数错误");
        }
        Jedis jedis = this.getJedis();
        boolean var3;
        try {
            var3 = jedis.exists(this.getSingleKey(key));
        } catch (Exception e) {
            throw new SystemException("判断缓存key[" + String.valueOf(this.getSingleKey(key)) + "]异常", e);
        } finally {
            jedis.close();
        }
        return var3;
    }

    /**
     * @param key, value
     * @return void
     * @Description:保存数据
     * @author ruanshubo
     * @date 2020/6/15 14:26
     */
    public void save(K key, V value) {
        Jedis jedis = this.getJedis();
        try {
            jedis.set(this.getSingleKey(key), this.value2Byte(value));
            BaseLog.getDailyLog().info("保存缓存数据，key：{},value：{}" + key, value);
        } catch (Exception e) {
            throw new SystemException("保存缓存key[" + String.valueOf(this.getSingleKey(key)) + "]异常", e);
        } finally {
            jedis.close();
        }
    }


    public void saveIfAbsent(K key, V value) {
        Jedis jedis = this.getJedis();
        try {
            jedis.setnx(this.getSingleKey(key), this.value2Byte(value));
            BaseLog.getDailyLog().info("保存缓存数据，key：{},value：{}" + key, value);
        } catch (Exception e) {
            throw new SystemException("保存缓存key[" + String.valueOf(this.getSingleKey(key)) + "]异常", e);
        } finally {
            jedis.close();
        }
    }

    /**
     * @param key
     * @return V
     * @Description: 获取key的值
     * @author ruanshubo
     * @date 2020/6/15 14:26
     */
    public V get(K key) {
        Jedis jedis = this.getJedis();
        Object var3;
        try {
            var3 = this.byte2Value(jedis.get(this.getSingleKey(key)));
        } catch (Exception e) {
            throw new SystemException("获取缓存[" + String.valueOf(this.getSingleKey(key)) + "]失败", e);
        } finally {
            jedis.close();
        }
        return (V) var3;
    }

    /**
     * @param key
     * @return java.lang.Long
     * @Description: 删除key
     * @author ruanshubo
     * @date 2020/6/15 14:25
     */
    public Long del(K key) {
        Long var = null;
        if (!this.exists(key)) {
            var = 0L;
        }
        Jedis jedis = this.getJedis();
        try {
            var = jedis.del(this.getSingleKey(key));
        } catch (Exception e) {
            throw new SystemException("删除缓存key[" + String.valueOf(this.getSingleKey(key)) + "]异常", e);
        } finally {
            jedis.close();
        }
        return var;
    }


    public static JedisPool initJedisPool() {
        //TODO 操作池可改为读取redis.properties配置文件
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(18);
        return new JedisPool(config, "localhost", 6379, 3000, "password");
    }

    protected Jedis getJedis() {
        return initJedisPool().getResource();
    }
}
