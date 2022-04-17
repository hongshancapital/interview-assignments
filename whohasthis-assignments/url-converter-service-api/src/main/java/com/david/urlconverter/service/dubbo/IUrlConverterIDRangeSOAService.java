package com.david.urlconverter.service.dubbo;

import com.david.urlconverter.model.web.IdRange;

public interface IUrlConverterIDRangeSOAService {

    /**
     * 分配当前可用号段
     * @return
     */
    public IdRange getNextAvailableRange();

}
