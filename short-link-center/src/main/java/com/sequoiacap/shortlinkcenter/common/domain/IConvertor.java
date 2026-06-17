package com.sequoiacap.shortlinkcenter.common.domain;

/**
 * DO <---> Domain Object
 */
public interface IConvertor<E, DO> {

    DO convert(E entity);

    E deConvert(DO record);
}
