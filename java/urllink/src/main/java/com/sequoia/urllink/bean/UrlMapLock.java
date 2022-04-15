package com.sequoia.urllink.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * URL映射表
 * </p>
 * @author liuhai
 * @date 2022.4.15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UrlMapLock implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
}
