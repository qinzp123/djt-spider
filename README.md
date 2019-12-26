## 数据爬虫项目

#### 多维度统计基于视频网站的各项指标

* 总播放数
* 每日播放增量
* 评论数
* 收藏数
* 赞
* 踩

----

#### 难点分析-课程概要

* 网站采取反爬策略
* 网站模板不定期变动
* 网站URL抓取失败
* IP被封

#### 网站采取反爬策略-解决方案

* 模拟浏览器访问

#### 网站模板不定期变动-解决方案

* 不同配置文件配置不同的模板规则
* 数据库存储不同的模板规则

#### 网站URL抓取失败-解决方案

* HttpClient默认处理方式
* Storm实时解析失败日志，将失败URL重新加入抓取仓库，超过3次就放弃

#### 网站频繁抓取IP被封-解决方案

* 购买代理IP库，随即抓取IP获取数据
* 部署多个应用分别抓取，降低单节点频繁访问
* 设置每个页面抓取时间间隔，降低被封概率

----
#### 总体架构解析

![总体架构解析](https://github.com/qinzp123/djt-spider/blob/master/img/1.jpg?raw=true)

----

#### 模块划分

![模块划分](https://github.com/qinzp123/djt-spider/blob/master/img/2.jpg?raw=true)

* 数据采集模块解读
    * 页面下载
        - HttpClient
    * 页面解析
        - HTMLCleaner+Xpath
        - Jsoup
        - 正则表达式
    * 数据接入
        - 存储数据库
        - 存储到HDFS

* 报表管理模块解读

![模块划分](https://github.com/qinzp123/djt-spider/blob/master/img/3.jpg?raw=true)

* 系统管理与监控模块解读
![模块划分](https://github.com/qinzp123/djt-spider/blob/master/img/4.jpg?raw=true)

----

#### 技术选型

* 数据采集层
    * HttpClient
    * HTMLCleaner+Xpath
    * 正则表达式
* 数据存储层
    * Hbase
    * Redis
* 数据处理层
    * solr
    * es
* 数据展示层
    * SpringMVC
    * freemarker
    * Jquery+HighChart
 ----

#### 部署方案
* 爬虫项目：多台服务器
* 网站爬虫分类URL定时项目：一台服务器
* Hbase数据库：集群
* Solr服务器：集群
* Redis服务器：集群
* 爬虫监控项目：一台服务器
* Web项目：多台服务器
* Zookeeper服务器：集群


```
graph LR
URL定时项目-->URL仓库Redis
URL仓库Redis-->爬虫项目
爬虫项目-->Hbase存储
Hbase存储 --> Solr索引


```

```
graph RL
Solr索引 --> Web展示
 Web展示 --> ZK监控
 ZK监控 --> 邮件提醒
```
----

#### 爬虫代码实现一

* 爬虫目标：腾讯视频
* 抓取字段：评分，总播放数，评论数，赞，踩，电视剧名称，集数

#### 爬虫代码实现二
* 实现功能：优化解析代码，提取公共代码到工具类，提取解析规则到配置文件

#### 爬虫代码实现三
* 实现功能：实现数据存储流程

#### 爬虫代码实现四
* 实现功能：Hbase存储爬虫数据
* Hbase表设计（这里偷懒没有操作）
    * 窄表：列少行多，表中的每一行尽可能保持唯一
    * 宽表：列多行少，通过时间戳版本进行区分取值
    * Hbase表设计：窄表：
    ![窄表](https://github.com/qinzp123/djt-spider/blob/master/img/5.jpg?raw=true)
    宽表：
    ![宽表](https://github.com/qinzp123/djt-spider/blob/master/img/6.jpg?raw=true)
    Hbase表设计方案
    ![方案](https://github.com/qinzp123/djt-spider/blob/master/img/7.jpg?raw=true)


#### 爬虫代码实现五
* 实现功能：解析电视剧列表页并优化实现类
这里大致逻辑是：进入一个链接，解析页面，如果该链接是详情页的就解析，否则就是列表页，把下一页地址放到urlList中，把该页面所有的详情页地址也放进去

#### 爬虫代码实现六
* 实现功能：添加url队列Queue，循环解析电视剧列表页和详情页

#### 爬虫代码实现七
* 实现功能：把Queue拆分为高优先级队列和低优先级队列，高级放列表页url，低级放详情页url

#### 爬虫代码实现八
* 实现功能：Redis实现高低优先级队列，从而实现分布式采集，
也可以直接从中断处执行

#### 爬虫代码实现九
* 实现功能：多线程爬虫

#### 爬虫代码实现十
* 实现功能：定时爬虫功能，通过运行RedisUtil的main来添加starturl至redis，一台服务器负责定时添加（比如每天几点）初始电视剧列表url（starturl）至redis仓库highlevel，供爬虫集群读取（爬虫集群是一直运行的），这里用Quartz,定时任务代码没写
* 定时器1：Linux Crontab
* 定时器2：Timer，简单易用，串行执行任务，前一个任务的异常会影响后续
* ScheduledExecutor：基于线程池并发执行任务，执行时间之外在轮询状态
* Quartz：核心类包括Scheduler，Job和Trigger。Job负责定义任务，Trigger设置调度策略，Scheduler将二者组装在一起并触发任务开始执行。

#### 爬虫代码实现十一
* 实现功能：完善字段（略）

#### 全文检索
* 使用solr
* 过程大体两步：创建索引（Indexing）和搜索索引（Search）
* Solr+Hbase组合原理
![使用solr](https://github.com/qinzp123/djt-spider/blob/master/img/8.jpg?raw=true)

从这开始就没做了参考[大讲台爬虫项目](https://www.bilibili.com/video/av52433071)
