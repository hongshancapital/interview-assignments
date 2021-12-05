package com.wwwang.assignment.shortenurl.compress;

import com.wwwang.assignment.shortenurl.compress.compressor.ICompressor;

/**
 * 压缩执行者
 */
public abstract class AbstractCompress {

    protected ICompressor compressor;

    protected AbstractCompress(ICompressor compressor){
        this.compressor = compressor;
    }

    /**
     * 执行压缩
     * @param bytes
     * @return
     */
    public abstract byte[] compress(byte[] bytes);

    /**
     * 执行解压
     * @param bytes
     * @return
     */
    public abstract byte[] deCompress(byte[] bytes);

}
