package com.wyd.shoturl;

class SHA1 {
    static String generate(byte[] dataBytes) throws Exception {
 
        byte[] fillBytes = new byte[64 * ((dataBytes.length + 8) / 64 + 1)];
        int i;
        for (i = 0; i < dataBytes.length; i++) {
            fillBytes[i] = dataBytes[i];
        }
 
        //fill 100000.....00
        fillBytes[i] = (byte) 0x80;
 
        //fill length
        long len = dataBytes.length * 8L;
        for (int j = fillBytes.length - 8, k = 0; j < fillBytes.length; j++, k++) {
            fillBytes[j] = (byte) (len >> ((7 - k) * 8));
        }
        //cast bytes to ints
        int[] bytes2Ints = byteArrToIntArr(fillBytes);
 
        int[] k = {0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xCA62C1D6};
        int[] h = {0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};
 
        for (int j = 0; j < bytes2Ints.length; j += 16) {
            int[] w = new int[80];
            System.arraycopy(bytes2Ints, 0, w, 0, 16);
 
            int a = h[0], b = h[1], c = h[2], d = h[3], e = h[4];
 
            for (int t = 0; t < 80; t++) {
                if (t >= 16) {
                    w[t] = s(1, w[t - 16] ^ w[t - 14] ^ w[t - 8] ^ w[t - 3]);
                }
                int temp = s(5, a) + f(t, b, c, d) + e + w[t] + k[t / 20];
                e = d;
                d = c;
                c = s(30, b);
                b = a;
                a = temp;
            }
 
            h[0] += a;
            h[1] += b;
            h[2] += c;
            h[3] += d;
            h[4] += e;
        }
        return String.format("%08x%08x%08x%08x%08x", h[0], h[1], h[2], h[3], h[4]);
    }
 
    private static int f(int t, int b, int c, int d) {
        switch (t / 20) {
            case 0:
                return (b & c) | (~b & d);
            case 2:
                return (b & c) | (b & d) | (c & d);
            default:
                return b ^ c ^ d;
        }
    }
 
    private static int s(int lmov, int num) {
//        return num << lmov | num >> (32 - lmov);
        return num << lmov | num >>> (32 - lmov);
    }
 
    private static int[] byteArrToIntArr(byte[] bytes) throws Exception {
        if (bytes.length % 4 != 0) {
            throw new Exception("Parse Error");
        }
        int[] intArr = new int[bytes.length / 4];
        for (int i = 0; i < intArr.length; i++) {
            intArr[i] = bytes[i * 4 + 3] & 0x000000ff |
                    bytes[i * 4 + 2] << 8 & 0x0000ff00 |
                    bytes[i * 4 + 1] << 16 & 0x00ff0000 |
                    bytes[i * 4] << 24 & 0xff000000;
        }
        return intArr;
    }
}
