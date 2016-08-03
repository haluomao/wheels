package com.atongmu.util.cache;


import com.qidian.common.Consts;
import com.qidian.common.util.LogHelper;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import java.util.Date;

/**
 * Memcache 类
 * Created by wangjun04 on 2015/11/12.
 */
public class MemCache  extends ICache{
    private static MemCachedClient memCachedClient = null;

    public MemCache(){
        this(null);
    }


    public MemCache(String configName){
        super();
        initMemcache(configName);
    }

    /**
     * 初始化memcache
     */
    private void initMemcache(String configName) {
        String[] addr = {"127.0.0.1:11211"};
        Integer[] weights = {3};
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(addr);
        pool.setWeights(weights);
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(200);
        pool.setMaxIdle(1000 * 30 * 30);
        pool.setMaintSleep(30);
        pool.setNagle(false);
        pool.setSocketTO(30);
        pool.setSocketConnectTO(0);
        pool.initialize();

        if (memCachedClient == null)
            memCachedClient = new MemCachedClient();
    }

    @Override
    public int set(String key, Object value,int seconds,int dbIndex) {
        try{
            Date date = new Date();
            memCachedClient.set(key,value,new Date(date.getTime()+seconds*1000));
            return Consts.SUCCESS;
        }
        catch(Exception ex){
            LogHelper.error(String.format("memcache set [key:{0}:value:{1}:seconds:{2}]", key, value, seconds),ex);
            return Consts.ERROR;
        }
    }

    @Override
    public Object get(String key,int dbIndex) {
        try{
            return memCachedClient.get(key);
        }catch(Exception ex) {
            LogHelper.error(String.format("memcache get [key:{0}]", key), ex);
            return null;
        }
    }


    @Deprecated
    @Override
    public int lpush(String key, String value) {
        //不实现
        return -1;
    }

    @Deprecated
    @Override
    public String rpop(String key) {
        return "未实现";
    }
}
