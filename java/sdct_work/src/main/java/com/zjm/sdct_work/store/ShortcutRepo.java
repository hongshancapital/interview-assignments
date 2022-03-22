package com.zjm.sdct_work.store;

/**
 * Author:   billzzzhang
 * Date:     2022/3/20 上午11:20
 * Desc:
 */

public interface ShortcutRepo {

    String getShortcutByUrl(String url);

    String getUrlByShortcut(String shortcut);

    void storeShortcutByUrl(String url, String shortcut);

    void storeUrlByShortcut(String shortcut, String url);
}
