package com.ttts.urlshortener.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 发号片段
 */
@Data
public class SenderNums implements Serializable {
    //唯一ID，主键
    private Long id;
    private Long startNum;
    private Long endNum;
}
