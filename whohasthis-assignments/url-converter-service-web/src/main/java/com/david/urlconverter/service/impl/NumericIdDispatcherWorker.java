package com.david.urlconverter.service.impl;

import lombok.*;

import java.util.LinkedList;

/**
 * 号段分发器工作单元
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class NumericIdDispatcherWorker {

    /**
     * 保存当前可用号段
     */
    private LinkedList<Long> idList;

    public synchronized boolean isEmpty() {
        if (idList == null) {
            return true;
        }
        return idList.isEmpty();
    }

    /**
     * 从可用号段中取出第一个号，并从号段中移除
     * @return
     */
    public synchronized Long dispatchId(){
        Long result = null;
        if(idList!=null&&!idList.isEmpty()){
            result = idList.removeFirst();
        }
        return result;
    }

}
