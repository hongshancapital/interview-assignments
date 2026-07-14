package com.zm.demo.sequence;

/**
 * @ClassName SequenceCreator
 * @Description 发号器
 * @Author zhaomin
 * @Date 2021/10/29 17:10
 **/
public interface SequenceCreator {

    /**
    * 生成一个序号
    * @Param: longUrl
    * @return: java.lang.Long
    * @Author: zhaomin
    * @Date: 2021/10/29 17:11
    */
    public Long createSeq(long maxThreshold);

}
