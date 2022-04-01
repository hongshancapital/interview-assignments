package com.getao.urlconverter.dto.generator;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/*
设计思路是每个IdGenerator生成一个字符开头的URL，所有总共有62个IdGenerator
 */
@Data
@Slf4j
public class IdGenerator {

    public IdGenerator(int id, int curNum, long size) {
        this.id = id;
        this.curNum = curNum;
        this.size = size;
    }

    /**
     * 发号器id，代表该发号器给出号码的开头字符
     */
    private int id;

    /**
     * 发号器当前发送的号码
     */
    private int curNum;

    /**
     * 发号器号码上限
     */
    private long size;

    /**
     * 发送号码
     * @return
     */
    public synchronized Long getCurNum() {
        if(curNum > size) {
            log.error("发号器: {}, 号码已用尽", this.id);
            return null;
        }
        long numToReturn = curNum;
        curNum++;
        return numToReturn;
    }

}
