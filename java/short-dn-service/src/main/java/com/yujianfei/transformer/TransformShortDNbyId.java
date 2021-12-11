package com.yujianfei.transformer;

import com.yujianfei.entity.LongDN;
import com.yujianfei.entity.ShortDN;
import com.yujianfei.transformer.ITransformShortDN;
import org.springframework.stereotype.Service;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TransformShortDNbyId implements ITransformShortDN {

    public final static AtomicLong number=new AtomicLong(1);

    public static final char[] array = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h',
            'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '8', '5', '2', '7', '3', '6', '4', '0', '9', '1', 'Q',
            'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C',
            'V', 'B', 'N', 'M', '+', '-' };

    /**
     * 编码,从10进制转换到64进制
     * @param number long类型的10进制数,该数必须大于0
     * @return string类型的64进制数
     */
    private static String encode(long number) {
        long rest = number;
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder(0);
        while (rest >= 1) {
            stack.add(array[new Long(rest % 64).intValue()]);
            rest = rest / 64;
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();

    }

    /**
     * 计算短域名
     */
    @Override
    public ShortDN transform(LongDN longDN) {
        String path=encode(number.getAndIncrement());
        return new ShortDN(path);
    }

}
