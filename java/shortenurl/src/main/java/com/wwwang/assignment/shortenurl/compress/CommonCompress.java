package com.wwwang.assignment.shortenurl.compress;

import com.wwwang.assignment.shortenurl.compress.compressor.ICompressor;

/**
 * 无特殊处理的压缩执行者，直接调度压缩器
 */
public class CommonCompress extends AbstractCompress{

    public CommonCompress(ICompressor compressor) {
        super(compressor);
    }

    @Override
    public byte[] compress(byte[] bytes) {
        return compressor.doCompress(bytes);
    }

    @Override
    public byte[] deCompress(byte[] bytes) {
        return compressor.doDeCompress(bytes);
    }
}
