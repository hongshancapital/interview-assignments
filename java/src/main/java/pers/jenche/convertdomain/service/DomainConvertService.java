package pers.jenche.convertdomain.service;

import pers.jenche.convertdomain.core.SystemException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author jenche E-mail:jenchecn@outlook.com
 * @project convertdomain
 * @date 2021/11/15 13:59
 * @description 这个类的作用是进行长域名和短域名的互转。
 */
public interface DomainConvertService {

    /**
     * 将一个长URL包含URL全部相关信息转换成短URL形式。
     *
     * @param strOriginalDomain {@link String} 待转字符串
     * @return {@link String} 转换完成的短URL
     * @throws SystemException 系统异常
     */
    String toShort(String strOriginalDomain) throws SystemException;

    /**
     * 将一个长URL包含URL全部相关信息转换成短URL形式。
     *
     * @param strOriginalDomain {@link String} 待转字符串
     * @param strShortDomain 短域名选择
     * @return {@link String} 转换完成的短URL
     * @throws SystemException 系统异常
     */
    String toShort(String strOriginalDomain, String strShortDomain) throws SystemException;


    /**
     * 将一个短URL转换还原为原有URL
     * @param strShortUri {@link String} 短URL
     * @return {@link String} 转换完成的短URL
     * @throws SystemException 系统异常
     */
    String toOriginal(String strShortUri) throws SystemException;
}
