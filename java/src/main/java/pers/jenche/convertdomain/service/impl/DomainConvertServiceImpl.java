package pers.jenche.convertdomain.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.jenche.convertdomain.component.ConvertComponent;
import pers.jenche.convertdomain.core.ExceptionMessage;
import pers.jenche.convertdomain.core.SystemConfig;
import pers.jenche.convertdomain.core.SystemException;
import pers.jenche.convertdomain.service.DomainConvertService;
import pers.jenche.convertdomain.utilitie.MatchesUtil;
import pers.jenche.convertdomain.utilitie.VerificationUtil;

/**
 * Created with IntelliJ IDEA.
 *
 * @author jenche E-mail:jenchecn@outlook.com
 * @project convertdomain
 * @date 2021/11/15 13:59
 * @description 域名转换服务
 */
@Slf4j
@Service
public class DomainConvertServiceImpl implements DomainConvertService {

    final ConvertComponent convertComponent;

    @Autowired
    public DomainConvertServiceImpl(ConvertComponent convertComponent) {
        this.convertComponent = convertComponent;
    }

    @Override
    public String toShort(String strOriginalDomain) throws SystemException {
        return toShort(strOriginalDomain, "");
    }

    @Override
    public String toShort(String strOriginalDomain, String strShortDomain) throws SystemException {
        //检查传进来的参数是不是空的，如果是空抛出异常返回至调用者
        if (StringUtils.isBlank(strOriginalDomain))
            throw new SystemException(ExceptionMessage.S_20_IS_BLANK_PARAMS);

        if (!VerificationUtil.validateURI(strOriginalDomain))
            throw new SystemException(ExceptionMessage.S_20_NOT_URI);

        int intShortLength = 8;

        //开始对URL进行转换,将shortDomain短域名已盐的方式加进去
        String convertResult = convertComponent.getShort(strShortDomain.concat(strOriginalDomain), intShortLength);

        //储存到临时系统变量中
        SystemConfig.SHORT_URI_STORE.put(convertResult, strOriginalDomain);
        log.info("当前存储了{}条数据；", SystemConfig.SHORT_URI_STORE.size());
        return strShortDomain.concat(convertResult);
    }

    @Override
    public String toOriginal(String strShortUri) throws SystemException {
        //检查传进来的参数是不是空的，如果是空抛出异常返回至调用者
        if (StringUtils.isBlank(strShortUri))
            throw new SystemException(ExceptionMessage.S_20_IS_BLANK_PARAMS);

        if (!VerificationUtil.validateURI(strShortUri))
            throw new SystemException(ExceptionMessage.S_20_NOT_URI);

        String singleKey = MatchesUtil.getSingleKey(strShortUri);
        return SystemConfig.SHORT_URI_STORE.get(singleKey);
    }
}
