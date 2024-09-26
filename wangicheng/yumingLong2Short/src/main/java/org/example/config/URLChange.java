package org.example.config;

public class URLChange {

    private static final char[] cs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static int[] url8Selected = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

    public static String getNew8URL() {
        int nowCharIndex = 0;
        while (true) {
            int nowCharIndexValue = url8Selected[nowCharIndex];
            nowCharIndexValue++;

            if (nowCharIndexValue >= cs.length) {
                url8Selected[nowCharIndex] = 0;
                nowCharIndex++;
                if (nowCharIndex < url8Selected.length) {
                    url8Selected[nowCharIndex] = url8Selected[nowCharIndex] + 1;
                    break;
                } else {
                    nowCharIndex = 0;
                }
            } else {
                url8Selected[nowCharIndex] = nowCharIndexValue;
                break;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = url8Selected.length - 1; i >= 0; i--) {
            stringBuilder.append(cs[url8Selected[i]]);
        }
        return stringBuilder.toString();
    }

}
