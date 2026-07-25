package com.bingl.web.util;

public class ByteUtil {

    public static byte[] intToBytesHighHead(int value){
        byte[] src=new byte[4];
        src[0]=(byte)((value >> 24) & 0xff);
        src[1]=(byte)((value >> 16) & 0xff);
        src[2]=(byte)((value >> 8) & 0xff);
        src[3]=(byte)((value ) & 0xff);
        return src;
    }
    public static int byteToIntHighHead(byte[] src,int offset){
        return (src[offset]&0xff)<<24
                | (src[offset+1]&0xff)<<16
                | (src[offset+2]&0xff)<<8
                | (src[offset+3]&0xff);
    }
}
