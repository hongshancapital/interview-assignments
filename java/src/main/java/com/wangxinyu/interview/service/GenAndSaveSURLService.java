package com.wangxinyu.interview.service;

import com.wangxinyu.interview.bean.UrlDTO;
import com.wangxinyu.interview.model.MemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

@Component
public class GenAndSaveSURLService {
    @Value("${biz.genID}")
    private Integer genID;
    private static final Logger LOGGER_SUrlLUrlRelation = LoggerFactory.getLogger("SUrlLUrlRelation");


    //共94个字符，也就是94进制
    public static final char[] ARRAY =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
                    'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
                    'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D',
                    'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M',
                    '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':',
                    ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};

    private static final ArrayBlockingQueue<Character> ARRAY_BLOCKING_QUEUE = new ArrayBlockingQueue<>(ARRAY.length);

    private static Map<Character, Integer> charValueMap = new HashMap<>();

    //初始化map
    static {
        for (int i = 0; i < ARRAY.length; i++) charValueMap.put(ARRAY[i], i);
    }

    //初始化ARRAY_BLOCKING_QUEUE
    static {
        new Thread(GenAndSaveSURLService::addToken).start();
    }

    /**
     * @param LUrl 长域名
     * @return UrlDTO
     * @throws Exception
     */
    public UrlDTO genAndSaveSURL(String LUrl) throws Exception {
        String sUrl = genSURL(LUrl);
        UrlDTO urlDTO = new UrlDTO(LUrl, sUrl);
        saveSURL(urlDTO);
        return urlDTO;
    }

    /**
     * 根据长域名生成短域名的核心方法
     * <p>
     * 根据需求短域名长度要求为8个字符，本程序共选择了94个字符，请参考：GenAndSaveSURLService.ARRAY
     * 本方法采用类似雪花算法的方式，将8位字符分隔成3部分，分别是：
     * 1. 1到6位，共6位，作为时间戳，共支持表达 94^6=6.8e11 个数字，本程序采用0.1秒为一个数字的方式进行换算
     * 2. 第7位，共1位，作为短域名生成器的ID，共支持表达 94 个数字
     * 3. 第8位，共1位，作为一个自增序列，共支持表达 94 个数字
     * <p>
     * 各个部分单独分析：
     * 1. 时间戳部分
     * 支持从1970年1月1日之后的1000年
     * 2. 短域名生成器
     * 共支持94个无状态的服务分布式部署，不会生成重复的数字
     * 3. 自增序列
     * 程序内有令牌桶限速逻辑，单个时间周期（第一部分增长一个数字）内，自增序列只能自增一个循环，之后便会阻塞
     * <p>
     * QPS：
     * 单个短域名生成服务0.1秒内可以生成94个短域名，1秒内可以生成940个短域名
     * 共支持部署94个短域名生成服务，也就是1秒内最多可以生成940*94=88,360个短域名
     *
     * @param LRUL
     * @return
     * @throws Exception
     */
    String genSURL(String LRUL) throws Exception {
        //获得当前的1/10秒数字，从1970年1月1日开始的第多少个0.1秒
        long currentTimePointOneSeconds = System.currentTimeMillis() / 100;
        String timeStampChar = numberConvertToDecimal(currentTimePointOneSeconds, ARRAY.length);
        if (genID < 0 || genID > ARRAY.length - 1) {
            throw new Exception(String.format("genID的范围应该在[0,%d]", ARRAY.length - 1));
        }
        char genIDChar = ARRAY[genID];

        char token = getToken();
        return timeStampChar + genIDChar + token;
    }

    /**
     * 保存长域名和短域名对应关系
     * 本程序做了两步操作：
     * 1. 将对应关系持久化到本地
     * 2. 将对应关系保存到内存中
     * <p>
     * 程序恢复：
     * 从磁盘读取长短域名对应关系，并恢复到内存中
     *
     * @param urlDTO
     */
    void saveSURL(UrlDTO urlDTO) throws Exception{
        //获得短域名对应的十进制数字，在内存中保存Long类型的数字比保存8位字符串更省内存
        long sUrlDecimalNumber = decimalConvertToNumber(urlDTO.getShortURL(), ARRAY.length);
        //输出到磁盘
        LOGGER_SUrlLUrlRelation.info(String.format("%d %s %d", genID, urlDTO.getLongURL(), sUrlDecimalNumber));
        //保存到内存
        MemData.DB.put(sUrlDecimalNumber, urlDTO.getLongURL());
    }

    String numberConvertToDecimal(long number, int decimal) {
        StringBuilder builder = new StringBuilder();
        while (number != 0) {
            builder.append(ARRAY[(int) (number - (number / decimal) * decimal)]);
            number /= decimal;
        }
        return builder.reverse().toString();
    }

    public long decimalConvertToNumber(String decimalStr, int decimal) throws Exception {
        long sum = 0;
        long multiple = 1;
        char[] chars = decimalStr.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            char c = chars[i];
            Integer index = charValueMap.get(c);
            if (index == null) {
                throw new Exception(String.format("contains not support characters %s",c));
            }
            sum += index * multiple;
            multiple *= decimal;
        }
        return sum;
    }

    char getToken() {
        try {
            return ARRAY_BLOCKING_QUEUE.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private static int counter = 0;

    static void addToken() {
        while (true) {
            try {
                for (char aChar : ARRAY) ARRAY_BLOCKING_QUEUE.add(aChar);
            } catch (IllegalStateException e) {
                if (counter % 50 == 0) {
                    System.out.println(e.getMessage() + " " + counter);
                }
                counter++;
            }
            try {
                //每0.1秒向ARRAY_BLOCKING_QUEUE中存放一次数据
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
