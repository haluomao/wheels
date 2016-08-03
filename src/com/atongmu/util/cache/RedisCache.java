package com.atongmu.util.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 操作相关类
 * @author anonymous
 */
public class RedisCache extends ICache {
    private Jedis jedis;
    private JedisPool jedisPool;

    /**
     * 实现构造函数
     */
    public RedisCache() {
        this(null);
    }


    /**
     *
     * @param configName
     */
    public RedisCache(String configName){
        super();
        initJedisPool(configName);
        jedis = jedisPool.getResource();
    }

    /**
     * 初始化jedispool
     */
    private void initJedisPool(String configName) {
        if(StringUtils.isEmpty(configName)){
            //默认redis配置节
            configName = "redis";
        }

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Config.getConfig(configName + ".maxtotal",500));
        config.setMaxIdle(Config.getConfig(configName + ".maxidle",5));
        config.setMaxWaitMillis(Config.getConfig(configName+".timout",-1));

        String host = Config.getConfig(configName+".host");
        int port = Config.getConfig(configName+".port",6379);

        //读取config配置
        jedisPool = new JedisPool(config,host, port);
    }

    /**
     * 设置缓存值
     * @param key
     * @param value
     * @param ttl
     * @param dbIndex 缓存数据库
     * @return
     */
    @Override
    public int set(String key, Object value, int ttl, int dbIndex) {
        try {
            jedis.select(dbIndex);
            //注意：这里做了序列化处理
            jedis.setex(key.getBytes(), ttl, SerializeHelper.serialize(value));
            return Consts.SUCCESS;
        } catch (Exception ex) {
            LogHelper.error("redis set error:[key:{0}:value:{1}:seconds:{2},dbIndex:{3}],ex:{4}", key, value, ttl, dbIndex, ex);
            return Consts.ERROR;
        }
    }

    /**
     * 获取缓存值
     * @param key
     * @param dbIndex 缓存数据库
     * @return
     */
    @Override
    public Object get(String key, int dbIndex) {
        try{
            jedis.select(dbIndex);
            //注意：这里要反序列化
            byte[] bytes = jedis.get(key.getBytes());
            if(bytes!=null)
                return SerializeHelper.unserialize(bytes);
        }catch(Exception ex){
            LogHelper.error("redis get error:[key:{0},dbIndex:{1}],ex:{2}", key, dbIndex, ex);
        }
        return null;
    }

    /**
     * 往队列中插入数据
     * @param key
     * @param value
     * @return
     */
    @Override
    public int lpush(String key, String value) {
        try{
            jedis.lpush(key, value);
            return 0;
        }catch(Exception ex){
            LogHelper.error("redis lpush error:[key:{0},value:{1}],ex:{2}", key, value, ex);
            return -1;
        }

    }

    /**
     * 取队列数据
     * @param key
     * @return
     */
    @Override
    public String rpop(String key) {
        try{
            return jedis.rpop(key);
        }catch(Exception ex){
            LogHelper.error("redis lpush error:[key:{0}],ex:{1}", key, ex);
            return "rpop异常";
        }
    }
}
