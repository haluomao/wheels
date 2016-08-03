package com.atongmu.util.cache;

import com.qidian.common.cache.ICache;

/**
 * 缓存封装类
 * 默认是走redis存储
 * Created by wangjun04 on 2015/11/12.
 */
public class CacheService {
    private static ICache cache = null;
    private static String cacheType = "redis";
    /**
     * 获取实例化对象
     * @return
     */
    public static CacheService getInstance(){
        return getInstance(cacheType,null);
    }


    /**
     * 获取实例化对象
     * @param cachetype
     * @return
     */
    public static CacheService getInstance(Consts.CACHETYPE cachetype){
        return getInstance(cachetype,null);
    }


    /**
     * 获取实例化对象
     * @param cachetype
     * @return
     */
    public static CacheService getInstance(String configName){
        return getInstance(Consts.CACHETYPE.REDIS,configName);
    }


    /**
     * 获取实例化对象
     * @param cachetype
     * @param configName
     * @return
     */
    public static CacheService getInstance(Consts.CACHETYPE cachetype,String configName) {
        if (cache == null)
            cache = ICache.getInstance(cachetype, configName);

        return new CacheService();
    }


    /**
     * 默认redis存储，默认dbIndex=0
     * @param key
     * @param value
     * @param ttl
     * @return
     */
    public int set(String key,Object value,int ttl) {
        return cache.set(key, value, ttl, 0);
    }

    /**
     * 默认redis存储
     * @param key
     * @param value
     * @param ttl
     * @param dbIndex
     * @return
     */
    public int set(String key,Object value,int ttl,int dbIndex) {
        return cache.set(key, value, ttl, dbIndex);
    }



    /**
     * 获取缓存值，默认dbIndex=0
     * @param key
     * @return
     */
    public Object get(String key){
        return get(key, 0);
    }

    /**
     * 获取缓存
     * @param key
     * @param dbIndex
     * @return
     */
    public Object get(String key,int dbIndex) {
        return cache.get(key, dbIndex);
    }

    /**
     * 入队列数据
     * @param key
     * @param value
     * @return
     */
    public int lpush(String key,String value){
        return cache.lpush(key, value);
    }

    /**
     * 取队列数据
     * @param key
     * @return
     */
    public String rpop(String key){
        return cache.rpop(key);
    }
}
