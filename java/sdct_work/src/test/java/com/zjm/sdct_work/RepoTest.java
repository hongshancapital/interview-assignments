package com.zjm.sdct_work;

import com.zjm.sdct_work.store.ShortcutRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author:   billzzzhang
 * Date:     2022/3/20 下午9:24
 * Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepoTest {

    @Autowired
    private ShortcutRepo repo;


    @Test
    public void testRep() {

        String url = "http://www.baidu.com";
        String shortcut = "8fTdCTOO";

        String existShortcut = repo.getShortcutByUrl(url);
        Assert.assertNull(existShortcut);

        String existUrl = repo.getUrlByShortcut(shortcut);
        Assert.assertNull(existUrl);


        repo.storeUrlByShortcut(shortcut, url);
        String url1 = repo.getUrlByShortcut(shortcut);
        Assert.assertTrue(url1.compareTo(url) == 0);


        repo.storeShortcutByUrl(url, shortcut);
        String shortcut1 = repo.getShortcutByUrl(url);
        Assert.assertTrue(shortcut1.compareTo(shortcut) == 0);

    }


}
