package com.hongshang.shorturlmodel.po;

import lombok.Data;

import java.util.Date;

/**
 * 用于保存长地址，短地址的po类
 */
@Data
public class AddrUrlPo {

    /**
     * 长地址 或 短地址
     */
    private String urlStr;

    /**
     * 数据保存的起始日期
     */
    private Date StartDate;
}
