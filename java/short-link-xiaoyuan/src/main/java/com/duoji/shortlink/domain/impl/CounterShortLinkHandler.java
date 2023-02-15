package com.duoji.shortlink.domain.impl;

import com.duoji.shortlink.ability.FileOperateAbility;
import com.duoji.shortlink.ability.GuavaCacheStoreAbility;
import com.duoji.shortlink.ability.NumberGeneratorAbility;
import com.duoji.shortlink.ability.NumberGeneratorSelectAbility;
import com.duoji.shortlink.ability.generator.NumberGenerator;
import com.duoji.shortlink.ability.model.ShortLinkModel;
import com.duoji.shortlink.common.Config;
import com.duoji.shortlink.common.ConvertorUtil;
import com.duoji.shortlink.domain.ShortLinkHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月17日 20:52:00
 */
@Service
@ConditionalOnProperty(name = "app.model",havingValue = "counter")
@Slf4j
public class CounterShortLinkHandler implements ShortLinkHandler {

    @Autowired
    private Config config;

    /**
     * 号码生成器列表
     */
    private List<NumberGenerator> numberGeneratorList;

    @Autowired
    private NumberGeneratorAbility numberGeneratorAbility;

    @Autowired
    private NumberGeneratorSelectAbility numberGeneratorSelectAbility;

    @Autowired
    private GuavaCacheStoreAbility guavaCacheStoreAbility;

    @Autowired
    private FileOperateAbility fileOperateAbility;

    /**
     * 机器号
     */
    private Long machineId;


    @PostConstruct
    public void init() {
        numberGeneratorList = numberGeneratorAbility.createAutoincrementNumberGenerator(config.COUNTER_CNT, config.CODE_NUM_MIX, 1000L);
        machineId = Long.parseLong(fileOperateAbility.readFile("machineId"));
    }


    @Override
    public String generateShortLink(String longLink) {

        if (StringUtils.isEmpty(longLink)) {
            throw new IllegalArgumentException("longLink不可为空");
        }

        NumberGenerator numberGenerator = numberGeneratorSelectAbility.selectOneRandom(numberGeneratorList);
        if (numberGenerator == null) {
            throw new IllegalArgumentException("计数器已用尽");
        }
        Long generateCode = numberGenerator.generateCode();
        if (null == generateCode) {
            //计数器的号已用尽,需要排除掉
            log.info("计数器id={}的码号已用尽", numberGenerator.ownId());
            numberGeneratorSelectAbility.removeOneNumberGenerator(numberGeneratorList, numberGenerator);
            throw new IllegalArgumentException("计数器号码已用尽");
        }

        String machineIdStr = "";
        if (config.MACHINE_BIT > 0) {
            machineIdStr = ConvertorUtil.encode10ToScale(machineId, config.MACHINE_BIT, config.REDIX.intValue());
        }
        String code = machineIdStr
                + ConvertorUtil.encode10ToScale(numberGenerator.ownId(), config.COUNTER_BIT, config.REDIX.intValue())
                + ConvertorUtil.encode10ToScale(generateCode, config.CODE_BIT, config.REDIX.intValue());
        log.info("machineId={},numberGeneratorId={},code={}", machineIdStr, numberGenerator.ownId(), generateCode);
        ShortLinkModel shortLinkModel = new ShortLinkModel();
        shortLinkModel.setShortLink(code);
        shortLinkModel.setLongLink(longLink);
        shortLinkModel.setExpireTime(System.currentTimeMillis() + config.EXPIRE_SEC * 1000);
        guavaCacheStoreAbility.put(code, shortLinkModel);
        return code;
    }

    @Override
    public String findLongLink(String shortLink) {
        Object value = guavaCacheStoreAbility.get(shortLink);
        if (value == null) {
            return null;
        }
        return ((ShortLinkModel) value).getLongLink();
    }
}
