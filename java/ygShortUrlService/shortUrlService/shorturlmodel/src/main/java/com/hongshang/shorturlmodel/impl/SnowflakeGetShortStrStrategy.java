package com.hongshang.shorturlmodel.impl;

import com.hongshang.shorturlmodel.api.IGetShortStrStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用雪花算法产生短地址的策略模式实现类
 *
 * @author yangguowen
 * @date 2021/12/17
 */
@Component("snowflakeGetShortStrStrategy")
public class SnowflakeGetShortStrStrategy implements IGetShortStrStrategy {

    public static final int NUMBER_OF_CHAR = 8;

    private  SnowFlake snowFlakeCreator = new SnowFlake(2, 3);

    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    @Override
    public synchronized String getNextShortStr() {
        Long snowFlake = snowFlakeCreator.nextId();
        return compressNumber(snowFlake);
    }

    /**
     * 将雪花数变为8位字符
     *
     * @param snowFlake Long
     * @return String
     */
    private String compressNumber(Long snowFlake) {
        List<Integer> ll = toSixTwoList(snowFlake);
        char[] buf = new char[ll.size()];
        short charPos = (short) ll.size();
        for(int index=0;index<ll.size();index++){
            buf[--charPos] = digits[(int) (ll.get(index))];
        }
        return new String(buf, charPos, (ll.size() - charPos));
    }

    /**
     * 雪花数变为8位62进行数
     *
     * @param snowFlake Long
     * @return List 8位62进行数
     */
    private List<Integer> toSixTwoList(Long snowFlake){
        List<Integer> result = new ArrayList<>();
        Long remainder = snowFlake;
        int index = 0;
        while(remainder!=0&&index++< NUMBER_OF_CHAR){
            result.add((int) (remainder%digits.length));
            remainder = remainder/digits.length;
        }
        return  result;
    }
}
