package com.zoujing.shortlink.service;

import com.zoujing.shortlink.generator.IDGenerator;
import com.zoujing.shortlink.generator.SimpleIDGenerator;
import com.zoujing.shortlink.model.ShortLinkAppConfig;
import com.zoujing.shortlink.utils.HexTransformatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service("LinkGeneratorService")
@Slf4j
public class LinkGeneratorServiceImpl implements LinkGeneratorService {

    /**
     * 发号器列表
     */
    private List<IDGenerator> idGeneratorList;

    /**
     * app config
     */
    @Autowired
    private ShortLinkAppConfig shortLinkAppConfig;

    /**
     * 缓存服务
     */
    @Autowired
    private CacheStoreService cacheStoreService;

    @PostConstruct
    public void initIdGeneratorList() {
        List<IDGenerator> list = new ArrayList<>();
        for (long i = 0l; i < shortLinkAppConfig.IDGeneratorMaxSize; i++) {
            // todo 如果宕机重启，可从磁盘中获取当前发号器发号水位。
            list.add(new SimpleIDGenerator(i, 0l, shortLinkAppConfig.IDMaxNum));
        }
        this.idGeneratorList = list;
        log.info("initIdGeneratorList success. list size = {}", list.size());
    }

    @Override
    public String getShortLink(long sourceApp, String longLink) {
        if (StringUtils.isEmpty(longLink)) {
            return null;
        }

        // 首先看缓存中是否存在，存在则直接返回，不重复发号
        if (cacheStoreService.get(longLink) != null) {
            return (String) cacheStoreService.get(longLink);
        }

        // 具体使用发号器
        IDGenerator generator = null;
        // 对长连接进行md5
        String linkMd5 = DigestUtils.md5DigestAsHex(longLink.getBytes(StandardCharsets.UTF_8));
        // 使用md5最后两位转10进制作为分片编号
        int linkShardNo = HexTransformatUtil.hexAnlyTo10(shortLinkAppConfig.characterSet, linkMd5.substring(linkMd5.length() - 2, linkMd5.length()));
        // 按分片号对机器发号器个数进行取余
        // 获取路由到的发号器
        Long no = linkShardNo % shortLinkAppConfig.IDGeneratorMaxSize;
        if (no != null && no < idGeneratorList.size()) {
            generator = idGeneratorList.get(no.intValue());
        }

        if (null == generator) {
            return null;
        }

        Long ID = generator.getNextId();
        if (ID == null) {
            return null;
        }

        // 应用编号+机器编号+发号器编号+ID编号
        String link = HexTransformatUtil.hex10ToAnly(shortLinkAppConfig.characterSet, sourceApp, shortLinkAppConfig.sourceAppBitSize)
                + HexTransformatUtil.hex10ToAnly(shortLinkAppConfig.characterSet, shortLinkAppConfig.testMachineId, shortLinkAppConfig.machineBitSize)
                + HexTransformatUtil.hex10ToAnly(shortLinkAppConfig.characterSet, generator.getGeneratorNo(), shortLinkAppConfig.IDGeneratorNumBitSize)
                + HexTransformatUtil.hex10ToAnly(shortLinkAppConfig.characterSet, generator.getNextId(), shortLinkAppConfig.IDBitSize);

        // 存入缓存
        cacheStoreService.save(link, longLink);
        return link;
    }

    @Override
    public String getLongLink(long sourceApp, String shortLink) {
        Object result = cacheStoreService.get(shortLink);
        if (result == null) {
            // todo 路由到对应分片获取数据，若在对应分片缓存则记录异常日志
            return null;
        }
        return (String) result;
    }
}
