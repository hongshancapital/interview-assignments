package cn.sequoiacap.links.service.impl;

import cn.sequoiacap.links.entities.Link;
import cn.sequoiacap.links.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : Liushide
 * @date :2022/4/6 14:48
 * @description : 链接服务实现测试类
 */
@Slf4j
@SpringBootTest
class LinkServiceImplTest {

    @Autowired
    LinkService linkService;


    static String longLink = "https://www.abc.com/app/tb-source-app/aiguangjiepc/content/index.html?spm=a21bo.jianhua.201870.4.5af911d9CROzgQ&contentId=5600000340593663082&scm=1007.12846.262044.0&pvid=b611e18f-59ab-4745-9f99-860ded0f7602";
    static String shortCode = "EvaUZj";

    @Test
    @DisplayName("测试service层的 addLink 方法")
    void addLink() {
        log.info("添加 longLink begin");

        Link link = new Link();
        link.setLongLink(longLink);
        link.setShortCode(shortCode);
        linkService.addLink(link);

        log.info("添加 longLink end");
        // 确定存入了
        Link queryLink = linkService.getLinkByCode(shortCode);
        assertEquals(link, queryLink);
    }

    @Test
    @DisplayName("测试service层的 getLinkByCode 方法")
    void getLinkByCode() {
        log.info("通过 shortCode={} 查询 link begin", shortCode);
        Link link = linkService.getLinkByCode(shortCode);
        if(link != null) {
            String newLongLink = link.getLongLink();
            log.info("通过 shortCode={} 查询 longLink={} end",shortCode, newLongLink );
            assertEquals(longLink, newLongLink);
        } else {
            log.info("通过 shortCode={} 没有查询到 link end" , shortCode);
            assertEquals(null, link);
        }
    }

    @Test
    @DisplayName("测试service层的 getLinkByCode 方法, shortCode is null")
    void getLinkByCodeIsNon() {
        final String shortCodeNull = "aaaaaaabbbbbbcccc";
        log.info("通过 shortCode= null 查询 link begin");
        Link link = linkService.getLinkByCode(shortCodeNull);
        if(link != null) {
            String newLongLink = link.getLongLink();
            log.info("通过 shortCode={} 查询 longLink={} end",shortCode, newLongLink );
            assertEquals(longLink, newLongLink);
        } else {
            log.info("通过 shortCode={} 没有查询到 link end" , shortCode);
            assertEquals(null, link);
        }
    }

    @Test
    @DisplayName("测试service层的 getLinkByCode 方法, shortCode 是不存在的值")
    void getLinkByCodeNonExistent() {
        String shortCodeNull = "aabbcc";
        log.info("通过 shortCode= null 查询 link begin");
        Link link = linkService.getLinkByCode(shortCodeNull);
        if(link != null) {
            String newLongLink = link.getLongLink();
            log.info("通过 shortCode={} 查询 longLink={} end",shortCode, newLongLink );
            assertEquals(longLink, newLongLink);
        } else {
            log.info("通过 shortCode={} 没有查询到 link end" , shortCode);
            assertEquals(null, link);
        }
    }
}