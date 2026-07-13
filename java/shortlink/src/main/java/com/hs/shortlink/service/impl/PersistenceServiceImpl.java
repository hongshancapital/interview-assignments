package com.hs.shortlink.service.impl;

import com.google.common.base.Charsets;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.hs.shortlink.service.PersistenceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Dangerous
 * @time: 2022/5/14 0:13
 */
@Service
public class PersistenceServiceImpl implements PersistenceService {

    @Value("${shortlink.storePath}")
    private String storePath;

    @Override
    public Map<String, String> load() throws IOException {
        Map<String, String> linkMap = new HashMap<>();
        File file = new File(storePath);
        if (!file.exists()) {
            file.createNewFile();
            return linkMap;
        }
        List<String> lineList = Files.readLines(file, Charsets.UTF_8);
        for (String lineItem : lineList) {
            linkMap.put(lineItem.substring(0, 8), lineItem.substring(8));
        }
        return linkMap;
    }

    @Override
    public void persist(String source, String target) throws IOException {
        File file = new File(storePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.asCharSink(file, Charsets.UTF_8, FileWriteMode.APPEND).write(source + target + System.lineSeparator());
    }
}
