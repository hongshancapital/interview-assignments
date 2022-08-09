package com.hongshan.link.service.service;

import com.hongshan.link.service.repository.LinkDataRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author heshineng
 * created by 2022/8/8
 * link数据转换类业务逻辑
 * <p>
 * 待实现逻辑
 * 1.controller 层校验
 * 2.数据层处理
 * 3.逻辑全部校验并自测
 * 4.补全单元测试，并测试单测覆盖率，截图
 * 5.gitignore 不需要的文件处理
 * 6.文档架构图，设计思路 及 一些其他改进优化项 Markdown 格式
 */
@Service
public class LinkConvertService {

    @Resource
    private LinkDataRepository linkDataRepository;

    /**
     * 根据长链接转换成短链接
     *
     * @param longLink 长链接
     * @return 短连接
     */
    public String getShortLink(String longLink) {
        if (linkDataRepository.isContainLinkByLinkMap(longLink)) {
            return linkDataRepository.getShortLink(longLink);
        }
        return linkDataRepository.createShortLink(longLink);
    }

    /**
     * 根据短链接转换成长链接
     *
     * @param shortLink 短连接
     * @return 长链接
     */
    public String getLongLink(String shortLink) {
        int index = shortLink.lastIndexOf("/");
        String id = shortLink.substring(index + 1);
        if (linkDataRepository.isContainIdByIdMap(id)) {
            return linkDataRepository.getLongLink(id);
        }
        return null;
    }
}
