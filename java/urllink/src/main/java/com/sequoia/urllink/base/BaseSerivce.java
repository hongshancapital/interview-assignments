package com.sequoia.urllink.base;

import com.sequoia.urllink.base.model.AbstractPojo;
import com.sequoia.urllink.base.model.PageObject;
import com.sequoia.urllink.base.model.ResultObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuhai
 * @date 2022.4.15
 */
public interface BaseSerivce<T extends AbstractPojo> {
    String MSG_ERROR = "%s method not implements,unsupported invocation!";

    default int save(T t) {
        throw new UnsupportedOperationException(String.format(MSG_ERROR, "insert"));
    }

    default int save(List<T> list) {
        throw new UnsupportedOperationException(String.format(MSG_ERROR, "batchInsert"));
    }

    /**
     * 更新非空字段
     *
     * @param t t
     */
    default int updateById(T t) {
        throw new UnsupportedOperationException(String.format(MSG_ERROR, "updateById"));
    }

    /**
     * 更新所有字段，包含空字段
     *
     * @param t t
     */
    default int updateDataById(T t) {
        throw new UnsupportedOperationException(String.format(MSG_ERROR, "updateDataById"));
    }

    default int deleteById(Serializable id) {
        throw new UnsupportedOperationException(String.format(MSG_ERROR, "deleteById"));
    }

    default int logicDeleteById(Serializable id) {
        throw new UnsupportedOperationException(String.format(MSG_ERROR, "logicDeleteById"));
    }

    default T queryById(Serializable id) {
        throw new UnsupportedOperationException(String.format(MSG_ERROR, "queryById"));
    }

    default void queryPage(PageObject<T> t) {
        throw new UnsupportedOperationException(String.format(MSG_ERROR, "queryPage"));
    }

    default List<T> queryAll() {
        throw new UnsupportedOperationException(String.format(MSG_ERROR, "queryAll"));
    }

    default void queryByCondition(ResultObject<T> t) {
        throw new UnsupportedOperationException(String.format(MSG_ERROR, "queryByCondition"));
    }
}
