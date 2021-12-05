package com.wwwang.assignment.shortenurl.compress.compressor;

/**
 * 压缩器接口定义
 */
public interface ICompressor {

    /**
     * 压缩
     * @param bytes
     * @return
     */
    byte[] doCompress(byte[] bytes);

    /**
     * 解压缩
     * @param bytes
     * @return
     */
    byte[] doDeCompress(byte[] bytes);

}
