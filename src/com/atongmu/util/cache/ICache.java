package com.atongmu.util.cache;

import com.qidian.common.Consts;

/**
 * Created by wangjun04 on 2015/11/13.
 */
public abstract class ICache {

    public static ICache getInstance(Consts.CACHETYPE cacheType,String configName) {
        switch (cacheType) {
            case MEMCACHE:
                return new MemCache(configName);
            case REDIS:
            default:
                return new RedisCache(configName);
        }
    }

    /**
     * 设置缓存
     * @param key
     * @param value
     * @param ttl 失效时间
     * @param dbIndex 缓存数据库
     * @return
     */
    public abstract int set(String key,Object value,int ttl,int dbIndex);

    /**
     * 读取缓存
     * @param key
     * @param dbIndex 缓存数据库
     * @return
     */
    public abstract Object get(String key,int dbIndex);

    /**
     * 上传数据到队列
     * @param key
     * @param value
     * @return
     */
    public abstract int lpush(String key,String value);


    /**
     * 取队列数据
     * @param key
     * @return
     */
    public abstract String rpop(String key);
}
