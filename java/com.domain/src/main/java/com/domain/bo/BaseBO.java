package com.domain.bo;

import lombok.Data;

/**
 * 基础BO
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@Data
public class BaseBO implements java.io.Serializable {

    private Boolean isSuccess;

    private String msg;
}
