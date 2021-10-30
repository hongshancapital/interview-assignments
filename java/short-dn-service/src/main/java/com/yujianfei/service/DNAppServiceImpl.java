package com.yujianfei.service;

import com.yujianfei.entity.*;
import com.yujianfei.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DNAppServiceImpl implements IDNAppService {

    @Autowired
    private DNService dnService;

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * @param reqdto 长域名
     * @return ShortDNRespDTO 短域名对象
     */
    public ShortDNRespDTO saveShortDN(LongDNReqDTO reqdto) {
        if (reqdto == null || StringUtils.isEmpty(reqdto.getLongDN().trim()))
            throw new ServiceException("1004", "长域名输入不能为空");

        if (reqdto.getLongDN().trim().length() > 50)
            throw new ServiceException("1005", "长域名过长请修改!");

        DNLongShortDto req = new DNLongShortDto();
        LongDN longDN = new LongDN(reqdto.getLongDN());
        req.setLongDN(longDN);
        DNLongShortDto entity = dnService.save(req);
        return new ShortDNRespDTO(entity.getShortDN().getUrl());
    }

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     * @param path 短域名
     * @return LongDNRespDTO 长域名信息
     */
    public LongDNRespDTO getLongDN(String path) {
        if (path.trim().length() > 8)
            throw new ServiceException("1006", "路径过长请修改！");

        ShortDNReqDTO reqdto = new ShortDNReqDTO(path);
        ShortDN shortDN = new ShortDN(reqdto.getShortDN());
        DNLongShortDto req = new DNLongShortDto();
        req.setShortDN(shortDN);
        DNLongShortDto entity = dnService.get(req);
        return new LongDNRespDTO(entity.getLongDN().getUrl());
    }

}
