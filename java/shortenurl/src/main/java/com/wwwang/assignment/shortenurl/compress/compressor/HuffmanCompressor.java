package com.wwwang.assignment.shortenurl.compress.compressor;

import com.avast.huffman.HttpUriHuffmanCompressor;

import java.nio.ByteBuffer;

/**
 * 霍夫曼压缩器
 */
public class HuffmanCompressor implements ICompressor{

    @Override
    public byte[] doCompress(byte[] bytes) {
        return bytebuffer2ByteArray(HttpUriHuffmanCompressor.compress(byte2Byffer(bytes)));
    }

    @Override
    public byte[] doDeCompress(byte[] bytes) {
        return bytebuffer2ByteArray(HttpUriHuffmanCompressor.decompress(byte2Byffer(bytes)));
    }

    /**
     * byte 数组转byteBuffer
     * @param byteArray
     */
    public static ByteBuffer byte2Byffer(byte[] byteArray) {
        ByteBuffer buffer=ByteBuffer.allocate(byteArray.length);
        buffer.put(byteArray);
        buffer.flip();
        return buffer;
    }

    /**
     * byteBuffer 转 byte数组
     * @param buffer
     * @return
     */
    public static byte[] bytebuffer2ByteArray(ByteBuffer buffer) {
        int len=buffer.limit() - buffer.position();
        byte [] bytes=new byte[len];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i]=buffer.get();
        }
        return bytes;
    }
}
