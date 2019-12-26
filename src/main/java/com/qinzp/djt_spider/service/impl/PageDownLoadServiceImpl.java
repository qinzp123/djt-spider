package com.qinzp.djt_spider.service.impl;

import com.qinzp.djt_spider.entity.Page;
import com.qinzp.djt_spider.service.PageDownLoadService;
import com.qinzp.djt_spider.util.PageDownLoadUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @ClassName HttpClientDownLoadService
 * @Description 页面下载实现类
 * @Author qinzp
 * @Date 2019/12/18/21:15
 * @Version 1.0
 */
@Service
public class PageDownLoadServiceImpl implements PageDownLoadService {
    @Override
    public Page downLoad(String url) throws IOException {
        Page page = new Page();
        page.setUrl(url);
        page.setContent(PageDownLoadUtil.getPageContent(url));
        return page;
    }
}
