package com.zjm.sdct_work.service;

import com.zjm.sdct_work.store.ShortcutRepo;
import com.zjm.sdct_work.util.ShortcutUtil;
import com.zjm.sdct_work.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:   billzzzhang
 * Date:     2022/3/19 下午4:44
 * Desc:
 */

@Slf4j
@Service
public class ShortcutService {

    @Autowired
    private ShortcutRepo repo;

    @Autowired
    private ShortcutUtil shortcutUtil;


    public String getShortcutByUrl(String url) {
        return repo.getShortcutByUrl(url);
    }

    public String getUrlByShortCut(String url) {
        return repo.getUrlByShortcut(url);
    }

    public String storeUrlAndShortcut(String url, String shortcut) {
        //短链已存在
        String existUrl = getUrlByShortCut(shortcut);
        if (!StringUtil.isEmpty(existUrl)) {
            log.warn("find exist shortcut {}", shortcut);
            if (url.compareTo(existUrl) == 0) {
                return shortcut;
            } else {
                log.warn("build new shortcut {}", shortcut);
                shortcut = shortcutUtil.generatorRandomStr();
            }
        }
        repo.storeShortcutByUrl(url, shortcut);
        repo.storeUrlByShortcut(shortcut, url);
        return shortcut;
    }


    public String createShortcut(String url) {

        String shortcut = shortcutUtil.generatorRandomStr();
        log.info("createShortcut get new UUID {}", shortcut);
        return storeUrlAndShortcut(url, shortcut);
    }


}
