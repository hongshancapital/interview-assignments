package me.huchao.sd.domain;

/**
 * @author huchao
 */
public interface Domainable<T> {

    void from(T t);

    T to();
}
