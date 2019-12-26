package com.qinzp.djt_spider.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * @ClassName RedisUtil
 * @Description 操作Jedis的工具类
 * @Author qinzp
 * @Date 2019/12/23/20:07
 * @Version 1.0
 */
public class RedisUtil {

    // redis中列表key的名称
    public static String highkey = "spider.highlevel";
    public static String lowkey = "spider.lowlevel";
    public static String starturl = "start.url";
    private  JedisPool jedisPool = null;

    public RedisUtil() {
        JedisPoolConfig pollConfig = new JedisPoolConfig();

        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        pollConfig.setMaxTotal(100);

        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        pollConfig.setMaxIdle(10);

        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        pollConfig.setMaxWaitMillis(1000 * 10);

        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        pollConfig.setTestOnBorrow(true);

        //redis未设置密码：
        jedisPool = new JedisPool(pollConfig, "127.0.0.1",6379);
    }

    /**
     * 获取指定范围的记录，可以做为分页使用
     * @param key String
     * @param start long
     * @param end long
     * @return List
     * */

    public List<String> lrange(String key, long start, long end) {
        Jedis resource = jedisPool.getResource();
        List<String> list = resource.lrange(key, start, end);
        jedisPool.returnResourceObject(resource);
        return list;
    }

    public void add(String key,String url){
        Jedis resource = jedisPool.getResource();
        resource.lpush(key, url);
        jedisPool.returnResourceObject(resource);
    }

    public String poll(String key) {
        Jedis resource = jedisPool.getResource();
        String result = resource.rpop(key);
        jedisPool.returnResourceObject(resource);
        return result;
    }

    public static void main(String[] args) {
        RedisUtil redisUtil = new RedisUtil();
        String url = "https://v.qq.com/channel/tv?_all=1&channel=tv&listpage=1&sort=18&year=859";
        redisUtil.add(starturl,url);
    }
}
