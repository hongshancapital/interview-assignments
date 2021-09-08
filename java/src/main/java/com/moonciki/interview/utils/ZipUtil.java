package com.moonciki.interview.utils;

import lombok.Data;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;

/**
 * rar 压缩工具
 */
@Data
public class ZipUtil {

    private OutputStream output;
    private ZipArchiveOutputStream zipOut;

    /**
     * 缓存 1MB
     * 可酌情修改
     */
    int bufferSize = 1024 * 1024;

    byte[] cacheBuff;

    public ZipUtil(OutputStream output) throws IOException {
        init(output);
    }

    public ZipUtil(String outPath) throws IOException {
        File resultFile = CommonUtil.createFile(outPath);

        OutputStream output = new FileOutputStream(resultFile);

        init(output);
    }
    public ZipUtil(File outFile) throws IOException {
        OutputStream output = new FileOutputStream(outFile);
        init(output);
    }

    private void init(OutputStream output){
        this.output = output;
        zipOut = new ZipArchiveOutputStream(output);
        cacheBuff = new byte[bufferSize];
    }

    public void putEntry(File srcFile, String name) throws IOException {
        InputStream input = new FileInputStream(srcFile);
        putEntry(input, name);
    }

    public void putEntry(byte[] byteData, String name) throws IOException {
        InputStream input = new ByteArrayInputStream(byteData);
        putEntry(input, name);
    }

    public void putEntry(InputStream input, String name) throws IOException {
        ZipArchiveEntry zipEntry = new ZipArchiveEntry(name);

        zipOut.putArchiveEntry(zipEntry);

        int readLen;
        try {
            while ((readLen = input.read(cacheBuff)) > -1){
                zipOut.write(cacheBuff, 0, readLen);
            }
            zipOut.flush();
            zipOut.closeArchiveEntry();
        } finally {
            input.close();
        }
    }

    /**
     * 关闭资源
     */
    public void close(){
        if(zipOut != null){
            try {
                zipOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(output != null){
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(cacheBuff != null){
            cacheBuff = null;
        }

    }

    public static void main(String[] args) {

        String outPath = "D:/source-files/test/rarTestOut/testZipOut.zip";

        String readFolder = "D:/source-files/test/rarTest-1";

        try {
            ZipUtil zipUtil = new ZipUtil(outPath);

            File folder = new File(readFolder);

            File[] files = folder.listFiles();

            String stName = "/教学点中文/";

            for(File oneF: files){
                String fileName = stName + oneF.getName();

                zipUtil.putEntry(oneF, fileName);
            }

            zipUtil.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("finished ... ");
    }

}
