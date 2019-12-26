package com.qinzp.djt_spider.service.impl;

import com.qinzp.djt_spider.service.RepositoryService;
import com.qinzp.djt_spider.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName RedisRepositoryServiceImpl
 * @Description TODO
 * @Author qinzp
 * @Date 2019/12/23/22:47
 * @Version 1.0
 */
public class RedisRepositoryServiceImpl implements RepositoryService {

    RedisUtil redisUtil = new RedisUtil();

    @Override
    public String poll() {
        String url = redisUtil.poll(RedisUtil.highkey);
        if (StringUtils.isBlank(url)) {
            url = redisUtil.poll(RedisUtil.lowkey);
        }
        return url;
    }

    @Override
    public void addHighLevel(String url) {
        redisUtil.add(RedisUtil.highkey,url);
    }

    @Override
    public void addLowLevel(String url) {
        redisUtil.add(RedisUtil.lowkey,url);
    }
}
