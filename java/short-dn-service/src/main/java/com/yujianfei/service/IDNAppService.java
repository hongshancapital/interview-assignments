package com.yujianfei.service;

import com.yujianfei.entity.LongDNReqDTO;
import com.yujianfei.entity.LongDNRespDTO;
import com.yujianfei.entity.ShortDNRespDTO;

public interface IDNAppService {

    ShortDNRespDTO saveShortDN(LongDNReqDTO reqdto);

    LongDNRespDTO getLongDN(String path);

}
