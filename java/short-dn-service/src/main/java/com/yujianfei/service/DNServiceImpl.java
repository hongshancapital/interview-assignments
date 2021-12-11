package com.yujianfei.service;

import com.yujianfei.entity.DNLongShortDto;
import com.yujianfei.exception.ServiceException;
import com.yujianfei.transformer.ITransformShortDN;
import com.yujianfei.wraper.DNLongShortRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DNServiceImpl implements DNService {

    @Autowired
    private ITransformShortDN transformShortDNbyMD5;

    @Autowired
    private DNLongShortRepo dnLongShortRepo;

    /**
     * 存储域名实体提对象
     * @param dnLongShortDto 装配对象
     * @return 域名实体对象
     */
    public DNLongShortDto save(DNLongShortDto dnLongShortDto) {
        if (!(dnLongShortDto.getLongDN().verify())) {
            throw new ServiceException("1001", "长域名格式验证失败！");
        }

        DNLongShortDto obj = dnLongShortRepo.getByMD5(dnLongShortDto.getLongDN());
        if (obj != null) {
            return obj;
        }

        dnLongShortDto.setShortDN(transformShortDNbyMD5.transform(dnLongShortDto.getLongDN()));
        dnLongShortRepo.save(dnLongShortDto);
        return dnLongShortDto;
    }

    /**
     * 读取域名实体对象
     * @param dnLongShortDto  装配对象
     * @return 域名实体对象
     */
    public DNLongShortDto get(DNLongShortDto dnLongShortDto) {
        if (!(dnLongShortDto.getShortDN().verify())) {
            throw new ServiceException("1002", "短域名路径验证失败！请检查是否在8个字符以内！");
        }
        dnLongShortDto = dnLongShortRepo.get(dnLongShortDto.getShortDN());
        if (dnLongShortDto == null) {
            throw new ServiceException("1003", "无长域名信息！");
        }
        return dnLongShortDto;
    }

}
