package com.qinzp.djt_spider.start;

import com.qinzp.djt_spider.entity.Page;
import com.qinzp.djt_spider.service.DataStoreService;
import com.qinzp.djt_spider.service.PageDownLoadService;
import com.qinzp.djt_spider.service.PageProcessService;
import com.qinzp.djt_spider.service.RepositoryService;
import com.qinzp.djt_spider.service.impl.DataStoreServiceImpl;
import com.qinzp.djt_spider.service.impl.PageDownLoadServiceImpl;
import com.qinzp.djt_spider.service.impl.RedisRepositoryServiceImpl;
import com.qinzp.djt_spider.service.impl.TencentProcessServiceImpl;
import com.qinzp.djt_spider.util.LoadPropertyUtil;
import com.qinzp.djt_spider.util.ThreadUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName StartTVCount
 * @Description 电视剧爬虫执行入口类
 * @Author qinzp
 * @Date 2019/12/19/11:39
 * @Version 1.0
 */
public class StartTVCount {

    private PageDownLoadService pageDownLoadService;

    private PageProcessService pageProcessService;

    private DataStoreService dataStoreService;

//    private Queue<String> urlQueue = new ConcurrentLinkedQueue<>();

    private RepositoryService repositoryService;

    private ExecutorService threadPool = Executors.newFixedThreadPool(Integer.parseInt(LoadPropertyUtil.getValue("threadNum")));


    public static void main(String[] args) throws IOException {
        StartTVCount startTVCount = new StartTVCount();
        startTVCount.setPageDownLoadService(new PageDownLoadServiceImpl());
        startTVCount.setPageProcessService(new TencentProcessServiceImpl());
        startTVCount.setDataStoreService(new DataStoreServiceImpl());
//        startTVCount.setRepositoryService(new QueueRepositoryServiceImpl());
        startTVCount.setRepositoryService(new RedisRepositoryServiceImpl());

        String url = "https://v.qq.com/channel/tv?_all=1&channel=tv&listpage=1&sort=18&year="+859;
        startTVCount.repositoryService.addHighLevel(url);
//        startTVCount.urlQueue.add(url);
        startTVCount.startSpider();
    }

    public void startSpider() throws IOException {
        while (true) {
//            String url = urlQueue.poll();
            final String url = repositoryService.poll();
            if (StringUtils.isNotBlank(url)) {
                // 开启多线程同时处理存量url
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("当前启动第"+Thread.currentThread().getId()+"个线程！");
                        Page page = null;
                        try {
                            page = StartTVCount.this.downLoadPage(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        processPage(page);
                        List<String> urlList = page.getUrlList();
                        for (String eachUrl : urlList) {
//                  urlQueue.add(eachUrl);
                            if (eachUrl.startsWith("https://v.qq.com/channel")) {
                                repositoryService.addHighLevel(eachUrl);
                                System.out.println("添加列表页至队列成功！" + eachUrl);
                            } else {
                                repositoryService.addLowLevel(url);
                                System.out.println("添加详情页至队列成功！" + eachUrl);
                            }
                        }
                        if (page.getUrl().startsWith("https://v.qq.com/x/cover")) {
                            storeData(page);
                        }
                        ThreadUtil.sleep(Long.parseLong(LoadPropertyUtil.getValue("millions")));
                    }
                });
            } else { // 队列里没有url先休息会
                ThreadUtil.sleep(Long.parseLong(LoadPropertyUtil.getValue("millions")));
            }
        }
    }

    /**
     * 下载页面
     * @param url
     * @return
     * @throws IOException
     */
    public Page downLoadPage(String url) throws IOException {
        return pageDownLoadService.downLoad(url);
    }

    public void processPage(Page page){
         pageProcessService.process(page);
    }

    public void storeData(Page page) {
        dataStoreService.store(page);
    }

    public PageDownLoadService getPageDownLoadService() {
        return pageDownLoadService;
    }

    public void setPageDownLoadService(PageDownLoadService pageDownLoadService) {
        this.pageDownLoadService = pageDownLoadService;
    }

    public PageProcessService getPageProcessService() {
        return pageProcessService;
    }

    public void setPageProcessService(PageProcessService pageProcessService) {
        this.pageProcessService = pageProcessService;
    }

    public DataStoreService getDataStoreService() {
        return dataStoreService;
    }

    public void setDataStoreService(DataStoreService dataStoreService) {
        this.dataStoreService = dataStoreService;
    }

    public RepositoryService getRepositoryService() {
        return repositoryService;
    }

    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }
}
