package com.qinzp.djt_spider.service.impl;

import com.qinzp.djt_spider.service.RepositoryService;
import org.apache.commons.lang3.StringUtils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @ClassName QueueRepositoryServiceImpl
 * @Description url仓库实现类
 * @Author qinzp
 * @Date 2019/12/23/15:35
 * @Version 1.0
 */
public class QueueRepositoryServiceImpl implements RepositoryService {

    private Queue<String> highLevelQueue = new ConcurrentLinkedQueue<>();
    private Queue<String> lowLevelQueue = new ConcurrentLinkedQueue<>();

    @Override
    public String poll() {
        String url = highLevelQueue.poll();
        if (StringUtils.isBlank(url)) {
            url = lowLevelQueue.poll();

        }
        return url;
    }

    @Override
    public void addHighLevel(String url) {
        highLevelQueue.add(url);
    }

    @Override
    public void addLowLevel(String url) {
        lowLevelQueue.add(url);
    }
}
