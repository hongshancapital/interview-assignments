package com.icbc.gjljfl.common.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @Author:
 * @Version: 1.0
 * @Description:
 * @Date: 2020/5/14
 */
public class PageHelperUtils {

    /**
     *  分页拦截   sql之前调用
     * @param pageNumber
     * @param pageSize
     * @param initpageSize  初始换分页大小  必填
     * @return
     */
    public static Page<Object> startPage(Integer pageNumber, Integer pageSize, Integer initpageSize){
        if (null == pageNumber || 0 == pageNumber) {
            pageNumber = 1;
        }
        if (null == pageSize || 0 == pageSize) {
            pageSize = initpageSize;
        }
        return PageHelper.startPage(pageNumber, pageSize);
    }
}
