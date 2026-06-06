package com.duoji.shortlink.ability;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 17:49:00
 */
@Service
@Slf4j
public class FileOperateAbility {

    public String readFile(String fileName) {

        ClassPathResource classPathResource = new ClassPathResource(fileName);
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            inputStream = classPathResource.getInputStream();
            char[] buf = new char[1024];
            Reader r = new InputStreamReader(inputStream, "UTF-8");
            while (true) {
                int n = r.read(buf);
                if (n < 0) {
                    break;
                }
                stringBuilder.append(buf, 0, n);
            }
        } catch (IOException e) {
            log.error("file error", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    log.error("file close error", e);
                }
            }
        }
        return stringBuilder.toString();
    }

    public void writeFile(String fileName, String content) {

        try {
            String basePath = ResourceUtils.getURL("classpath:").getPath() + "/info/";
            File fileExist = new File(basePath);
            // 文件夹不存在，则新建
            if (!fileExist.exists()) {
                fileExist.mkdirs();
            }
            // 获取文件对象
            File file = new File(basePath, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file);
            fileWritter.write(content);
            fileWritter.flush();
            fileWritter.close();
        } catch (Exception e) {
            log.error("writeFile error", e);
        }
    }


}
