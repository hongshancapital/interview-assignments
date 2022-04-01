package com.zz.generator;

import com.zz.exception.BusinessException;
import com.zz.exception.code.ShortUrlErrorCodeEnum;
import com.zz.util.Constants;
import com.zz.util.ConvertToShortCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 自动递增序列短链接生成器
 * 参考snowflake分布式序列号生成方式
 * 使用的编码进制是62进制包括 a-z A-Z 0-9
 * 因为最长的编码个数是8个，62^8 近似 2^47 - 2^48，使用47位表示最终的编码
 * 序列的组成是 时间序号(38位)+工作id(3位)+递增序号(6)
 * 时间序号(38位) 记录当前时间和2022-01-01 00:00:00 之间的毫秒的差值，目前可以支持10年
 * 工作id(3位) 初始值位0，如果发生时间回拨，累加1，如果>=2^3，从0开始
 * 递增序号(6) 遇到同一毫秒相同时，递增的序号
 * 最终支持的短链接个数是2^44
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
@Slf4j
@Service
public class AutoIncSeq_8DigitShortUrlGenerator implements ShortUrlGenerator, InitializingBean {
    /**
     * 最大工作id
     */
    private static final int MAX_WORD_ID = 7;
    /*
     * 最大递增序列号
     */
    private static final int MAX_SAME_SEQ = 63;
    /**
     * 用于匹配8位字符编码
     */
    private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{8}$");
    /**
     * 上次更新的时间
     */
    private volatile long lastTime = 0;
    /**
     * 工作id
     */
    private volatile int workId = 0;
    /**
     * 时间冲突的时候递增序号
     */
    private volatile int sameSeq = 0;
    /**
     * 随机编码
     */
    @Value("${shortCode.randomCode}")
    private String randomCode;
    @Value("${log4j.appender.stdout}")
    private String ss;
    /**
     * 编辑编码的字符数组
     */
    private char[] ranCS;

    /**
     * 生成短链接编码，本次只是涉及根据进制来生成相应的编码信息
     *
     * @return
     */
    @Override
    public String convertToCode(String url) throws BusinessException {
        try {
            //获取当前的序列号
            Random random = new Random();
            long incSeq = getIncSeq();

            //转换成62进制的位数
            List<Integer> digitList = ConvertToShortCode.digitTo62Code(incSeq);
            if (digitList.size() < 8) {
                for (int i = 0; i < 8 - digitList.size(); i++) {
                    digitList.add(random.nextInt(Constants.CODE_DIGIT_62));
                }
            }

            //通过位数获取相应的字符编码
            return getShortCode(digitList);
        } catch (Exception e) {
            log.error("获取序号失败" + url, e);
            throw new BusinessException(ShortUrlErrorCodeEnum.SU_004, e);
        }
    }

    /**
     * 根据位数获取短码
     *
     * @param digitList
     * @return
     */
    private String getShortCode(List<Integer> digitList) {
        StringBuilder sb = new StringBuilder();
        for (Integer i : digitList) {
            sb.append(ranCS[i]);
        }
        return sb.toString();
    }

    /**
     * 获取当前的自增的序列号
     *
     * @return
     */
    private synchronized long getIncSeq() throws Exception {
        //获取当前时间戳，比对是否发生时间回拨
        long curTime = System.currentTimeMillis();
        if (curTime < lastTime) {
            //时间回拨，更新工作id,如果新的wordId大于最大值，更新成0
            workId = (workId + 1) % (MAX_WORD_ID + 1);
            sameSeq = 0;
        } else if (curTime == lastTime) {
            //时间冲突了，累加序列号
            sameSeq++;
            //如果同一毫秒冲突，递增序号已经达到最大值，需要sleep 1ms，更新新的时间
            if (sameSeq > MAX_SAME_SEQ) {
                Thread.sleep(1);
                return getIncSeq();
            }
        } else {
            sameSeq = 0;
        }
        lastTime = curTime;

        String curSeq = curTime + "" + workId + "" + sameSeq;
        return Long.parseLong(curSeq);
    }

    @Override
    public boolean isValid(String shortCode) {
        return pattern.matcher(shortCode).matches();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.ranCS = this.randomCode.toCharArray();
    }
}
