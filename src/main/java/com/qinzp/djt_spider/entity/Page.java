package com.qinzp.djt_spider.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Page
 * @Description 存储页面信息实体类
 * @Author qinzp
 * @Date 2019/12/18/20:54
 * @Version 1.0
 */
@Data
public class Page {
    // 页面内容啊啊
    private String content;
    // 评分
    private String score;
    // 总播放量
    private String allNumber;
    // 评论数
    private String commentNumber;
    // 赞
    private String supportNumber;
    // 踩
    private String againstNumber;
    // 电视剧名称
    private String tvName;
    // 详情页面url
    private String url;
    // 集数
    private String episodeNumber;

    // 包含列表url和详情页url
    private List<String> urlList = new ArrayList<>();

    public void addUrl(String url) {
        urlList.add(url);
    }

    @Override
    public String toString() {
        return "Page{" +
                "score='" + score + '\'' +
                ", allNumber='" + allNumber + '\'' +
                ", commentNumber='" + commentNumber + '\'' +
                ", supportNumber='" + supportNumber + '\'' +
                ", againstNumber='" + againstNumber + '\'' +
                ", tvName='" + tvName + '\'' +
                ", url='" + url + '\'' +
                ", episodeNumber='" + episodeNumber + '\'' +
                ", urlList=" + urlList +
                '}';
    }
}
