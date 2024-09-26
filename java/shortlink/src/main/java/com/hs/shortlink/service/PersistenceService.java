package com.hs.shortlink.service;

import java.io.IOException;
import java.util.Map;

public interface PersistenceService {
    /**
     * 加载短长链映射
     *
     * @return 短长链映射
     * @throws IOException
     */
    Map<String, String> load() throws IOException;

    /**
     * 持久化 短长链接映射
     *
     * @param source 源串
     * @param target  目的串
     * @throws IOException
     */
    void persist(String source, String target) throws IOException;
}
