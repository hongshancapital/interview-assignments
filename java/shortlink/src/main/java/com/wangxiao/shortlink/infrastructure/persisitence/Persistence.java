package com.wangxiao.shortlink.infrastructure.persisitence;

import com.google.common.base.Charsets;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.wangxiao.shortlink.infrastructure.properties.ShortLinkProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class Persistence {

    @Resource
    private ShortLinkProperties shortLinkProperties;

    public Map<String, String> load() throws IOException {
        Map<String, String> linkMap = new HashMap<>();
        File file = new File(shortLinkProperties.getStorePath());
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

    public void persist(String shortLink, String longLink) throws IOException {
        File file = new File(shortLinkProperties.getStorePath());
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.asCharSink( file, Charsets.UTF_8, FileWriteMode.APPEND).write(shortLink + longLink + System.lineSeparator());
    }

}
