package com.qinzp.djt_spider.service;

import com.qinzp.djt_spider.entity.Page;

import java.io.IOException;

/**
 * @ClassName PageDownLoadService
 * @Description 页面下载接口
 * @Author qinzp
 * @Date 2019/12/18/21:11
 * @Version 1.0
 */
public interface PageDownLoadService {
    public Page downLoad(String url) throws IOException;
}
