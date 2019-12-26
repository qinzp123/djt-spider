package com.qinzp.djt_spider.service.impl;

import com.qinzp.djt_spider.entity.Page;
import com.qinzp.djt_spider.service.DataStoreService;

/**
 * @ClassName DataStoreServiceImpl
 * @Description TODO
 * @Author qinzp
 * @Date 2019/12/19/18:04
 * @Version 1.0
 */
public class DataStoreServiceImpl implements DataStoreService {
    @Override
    public void store(Page page) {
        System.out.println(page.toString());
    }
}
