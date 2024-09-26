package org.example.service;

import org.example.config.URLCatch;
import org.example.config.URLChange;
import org.example.config.URLNode;
import org.example.dao.LongUrl2ShortEsDAO;
import org.example.model.SearchDataResult;

import java.util.*;


public class YumingService {

    private URLCatch urlCatch = new URLCatch();
    private LongUrl2ShortEsDAO longUrl2ShortEsDAO = new LongUrl2ShortEsDAO();

    public String long2Short(String longUrl) throws Exception {
        String result = this.urlCatch.long2Short(longUrl);

        if (result == null) {
            Map<String, Object> conditionMap = new HashMap<>();
            conditionMap.put("long_url", longUrl);
            SearchDataResult searchDataResult = this.longUrl2ShortEsDAO.search(conditionMap, 0, 10);
            // 在数据库中查询
            if (searchDataResult.getCount() > 0) {
                // 数据库中存在
                List<Map<String, Object>> datas = searchDataResult.getDatas();

                for (Map<String, Object> dataMap : datas) {
                    result = dataMap.get("short_url").toString();
                }

                // 添加到 缓存中
                URLNode newURLNode = new URLNode(longUrl, result);
                this.urlCatch.addNewURLNode(newURLNode);
            } else {
                // 数据库中不存在 则 在数据库中添加 最新的 长域名与短域名的映射关系
                // 在缓存中添加 长域名与短域名的映射关系
                String shortURL = URLChange.getNew8URL();
                URLNode newURLNode = new URLNode(longUrl, shortURL);
                this.urlCatch.addNewURLNode(newURLNode);

                List<Map<String, Object>> dataMapList = new ArrayList<>();
                Map<String, Object> dataMap = new HashMap<>(100);

                dataMap.put("id", UUID.randomUUID().toString());
                dataMap.put("long_url", longUrl);
                dataMap.put("short_url", shortURL);
                dataMap.put("create_time", System.currentTimeMillis());

                dataMapList.add(dataMap);

                this.longUrl2ShortEsDAO.pitchAdd(dataMapList);

                result = newURLNode.shortUrl;
            }
        }

        return result;
    }

    public String short2Long(String shortUrl) throws Exception {
        String result = this.urlCatch.short2Long(shortUrl);

        if (result == null) {
            // 数据库中查询
            Map<String, Object> conditionMap = new HashMap<>();
            conditionMap.put("short_url", shortUrl);
            SearchDataResult searchDataResult = this.longUrl2ShortEsDAO.search(conditionMap, 0, 10);

            if (searchDataResult.getCount() > 0) {
                // 数据库中存在
                List<Map<String, Object>> datas = searchDataResult.getDatas();

                for (Map<String, Object> dataMap : datas) {
                    result = dataMap.get("long_url").toString();
                }

                // 添加到 缓存中
                URLNode newURLNode = new URLNode(shortUrl, result);
                this.urlCatch.addNewURLNode(newURLNode);
            } else {
                result = null;
            }
        }

        return result;
    }
}
