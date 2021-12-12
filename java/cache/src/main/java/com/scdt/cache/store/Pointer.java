package com.scdt.cache.store;

/**
 * 二级缓存 位置信息
 */
public class Pointer {
    protected int position;

    protected int length;

    public Pointer(int position, int length) {
        this.position = position;
        this.length = length;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
