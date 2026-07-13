package com.coderdream.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * 文件操作辅助类
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
@Service
@Slf4j
public class FileOperateHelper {
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
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            log.error("writeFile error", e);
        }
    }
}
