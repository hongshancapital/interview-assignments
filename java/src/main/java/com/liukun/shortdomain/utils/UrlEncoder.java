package com.liukun.shortdomain.utils;

/**
 * <p>
 * <b>Class name</b>:
 * </p>
 * <p>
 * <b>Class description</b>: Class description goes here.
 * </p>
 * <p>
 * <b>Author</b>: kunliu
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 2021/10/6       kunliu        1.0          Initial Creation
 *
 * </pre>
 *
 * @author kunliu
 * @date 2021/10/6
 * </p>
 */
public class UrlEncoder {
    static final char[] DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '-', '_'};


    public String shorten() {
        long seq = SeqInstance.getInstance().getSeq();
        return toShortString(seq);
    }

    private String toShortString(long seq) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int remainder = (int) (seq % DIGITS.length);
            sb.append(DIGITS[remainder]);
            seq = seq / DIGITS.length;
            if (seq == 0) {
                break;
            }
        }
        return sb.toString();
    }

}
