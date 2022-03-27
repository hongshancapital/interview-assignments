package com.polly.shorturl.tools;

/**
 * @author polly
 * @date 2022.03.21 12:57:56
 */
public class UrlTransfer {
    // 源进制
    private int sReg = 10;
    // 目标进制
    private int dReg = 62;

    public int charToInt(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        } else if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 36;
        }
        return 0;
    }

    public char intToChar(int i) {
        char[] c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        return c[i];
    }

    /**
     * 进制转换方法
     *
     * @param sStr 源数据
     * @return
     */
    public String convert(String sStr) {
        if ("0".equals(sStr)) {
            return "0";
        }

        //判断是否是负数
        boolean negativeFlag = false;
        if (sStr.startsWith("-")) {
            sStr = sStr.substring(1);
            negativeFlag = true;
        }

        char[] sChar = sStr.toCharArray();
        int n = sStr.length();
        int decimalInt = 0;
        String dStr = "";
        //先转换为十进制
        for (int i = 0; i < n; i++) {
            decimalInt += charToInt(sChar[i]) * Math.pow(sReg, n - i - 1);
        }
        while (decimalInt != 0) {
            dStr = intToChar(decimalInt % dReg) + dStr;
            decimalInt = decimalInt / dReg;
        }
        //如果是负数
        if (negativeFlag) {
            return "-" + dStr;
        }
        return dStr;
    }
}
