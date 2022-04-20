package pers.zhufan.shorturl.storage;

import org.springframework.stereotype.Component;
import pers.zhufan.shorturl.domain.shorturl.ShorterUrl;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author zhufan
 * @Date 2022-04-18 20:50
 * @ClassName ShorterStorageMemory
 * @Description 采用ConcurrentHashMap存储短链接与长链接
 */
@Component
public class ShorterStorageMemory implements ShorterStorage {
    /**
     * 存储shorter,url
     */
    Map<ShorterUrl, String> shorterMap = new ConcurrentHashMap<ShorterUrl, String>();
    /**
     * 存储url,shorter
     */
    Map<String, ShorterUrl> urlMap = new ConcurrentHashMap<String, ShorterUrl>();
    /**
     * 存储shorter.shorter,shorter
     */
    Map<String, ShorterUrl> shorterUrlMap = new ConcurrentHashMap<String, ShorterUrl>();

    @Override
    public String get(String shorterKey) {
        ShorterUrl shorter = shorterUrlMap.get(shorterKey);
        if (shorter != null) {
            return shorterMap.get(shorter);
        }
        return null;
    }

    @Override
    public void clean(String url) {
        ShorterUrl shorter = urlMap.get(url);
        if (shorter != null) {
            urlMap.remove(url);
            shorterMap.remove(shorter);
            shorterUrlMap.remove(shorter.getShorter());
        }
    }

    @Override
    public void cleanShorter(String shorterKey) {
        ShorterUrl shorter = shorterUrlMap.get(shorterKey);
        if (shorter != null) {
            urlMap.remove(shorterMap.get(shorter));
            shorterMap.remove(shorter);
            shorterUrlMap.remove(shorter.getShorter());
        }

    }

    @Override
    public void save(String url, ShorterUrl shorter) {
        urlMap.put(url, shorter);
        shorterMap.put(shorter, url);
        shorterUrlMap.put(shorter.getShorter(), shorter);
    }

    @Override
    public void clean() {
        shorterMap.clear();
        shorterUrlMap.clear();
        urlMap.clear();
    }
}
