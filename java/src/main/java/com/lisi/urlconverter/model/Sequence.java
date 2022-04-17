package com.lisi.urlconverter.model;


/**
 * @description: 保存共享变量值，并使其不与其它变量共享缓存行
 * @author: li si
 */
public class Sequence<T> {
    protected long p1, p2, p3, p4, p5, p6, p7, p8;
    protected volatile T value;
    protected long p10, p11, p12, p13, p14, p15, p16, p17;

    public Sequence(T t){
        this.value = t;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
