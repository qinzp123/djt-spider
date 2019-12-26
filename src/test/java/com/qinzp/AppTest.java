package com.qinzp;

import com.qinzp.djt_spider.entity.Page;
import com.qinzp.djt_spider.service.impl.PageDownLoadServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:bean.xml")
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Autowired
    private PageDownLoadServiceImpl pageDownLoadService;

    @Test
    public void downLoadTest() throws IOException {
    /*    // 得到spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        // 从容器中得到Account对象
        PageDownLoadServiceImpl pageDownLoadService = (PageDownLoadServiceImpl) ac.getBean("PageDownLoadServiceImpl");*/
        String url = "https://v.qq.com/x/cover/rjae621myqca41h.html";
        Page page = pageDownLoadService.downLoad(url);
        System.out.println(page.getContent());
    }
}
