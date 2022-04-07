package cn.sequoiacap.links.dao.impl;

import cn.sequoiacap.links.dao.LinkDao;
import cn.sequoiacap.links.entities.Link;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Liushide
 * @date :2022/4/6 11:35
 * @description : 链接Dao缓存实现测试类
 */
@Slf4j
@SpringBootTest
class LinkDaoCacheImplTest {

    @Autowired
    LinkDao linkDaoCache;

    static String longLink = "https://www.abc.com/app/tb-source-app/aiguangjiepc/content/index.html?spm=a21bo.jianhua.201870.4.5af911d9CROzgQ&contentId=5600000340593663082&scm=1007.12846.262044.0&pvid=b611e18f-59ab-4745-9f99-860ded0f7602";
    static String shortCode = "EvaUZj";
    static String shortLink = "https://sc.io/EvaUZj";

    @Test
    @DisplayName("测试 dao 层的 addLink 方法")
    void addLink() {
        log.info("添加 link begin");

        Link link = new Link();
        link.setLongLink(longLink);
        link.setShortCode(shortCode);
        link.setShortLink(shortLink);
        linkDaoCache.addLink(link);

        log.info("添加 link end");
        Link queryLink = linkDaoCache.getLinkByCode(shortCode);
        // 确定存入了
        assertEquals(link, queryLink);
    }

    @Test
    @DisplayName("测试 dao 层的 getLinkByCode 方法")
    void getLinkByCode() {
        log.info("通过 shortCode={} 查询 link begin", shortCode);
        Link link = linkDaoCache.getLinkByCode(shortCode);
        if(link != null) {
            String newLongLink = link.getLongLink();
            log.info("通过 shortCode={} 查询 longLink={}} end",shortCode, newLongLink);
            assertEquals(longLink, newLongLink);
        } else {
            log.info("通过 shortCode={} 没有查询到 link end" , shortCode);
            assertEquals(null, link);
        }
    }
}