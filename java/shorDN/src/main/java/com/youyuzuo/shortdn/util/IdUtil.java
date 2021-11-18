package com.youyuzuo.shortdn.util;

public class IdUtil {
    private Long currentValue = 0L;
    private int seq = 0;
    private static IdUtil instance = new IdUtil();

    private IdUtil(){

    }

    public synchronized Long generateId(){
        Long time = System.currentTimeMillis();
        if(time.longValue() != currentValue.longValue()){
            currentValue = time;
            seq = 0;
            return currentValue * 10000;
        }
        seq = seq + 1;
        return currentValue* 10000 + seq;
    }

    public static Long nexId(){
        return instance.generateId();
    }


    public static void main(String[] args){
        for(int i=0;i<10;i++) {
            System.out.println(IdUtil.nexId());
        }
    }
}
