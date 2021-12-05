package com.wwwang.assignment.shortenurl.entity;

import java.util.Arrays;

public class ByteArrObj {

    public byte[] data;

    public ByteArrObj(byte[] data){
        this.data = data;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ByteArrObj byteArrObj = (ByteArrObj) obj;
        return Arrays.equals(this.data,byteArrObj.data);
    }
}
