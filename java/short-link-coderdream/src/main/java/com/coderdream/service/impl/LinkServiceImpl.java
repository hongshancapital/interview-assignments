package com.coderdream.service.impl;

import com.coderdream.bean.ShortLinkBean;
import com.coderdream.helper.BloomFilterHelper;
import com.coderdream.helper.FileOperateHelper;
import com.coderdream.helper.GuavaCacheHelper;
import com.coderdream.helper.ShortLinkHelper;
import com.coderdream.service.LinkService;
import com.coderdream.utils.CommonUtil;
import com.coderdream.utils.Config;
import com.coderdream.utils.DuplicatedEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class LinkServiceImpl implements LinkService {
    @Resource
    private Config config;

    @Resource
    private FileOperateHelper fleOperateHelper;

    @Resource
    private ShortLinkHelper shortLinkHelper;

    @Resource
    private GuavaCacheHelper guavaCacheHelper;

    @Resource
    private BloomFilterHelper bloomFilterHelper;

    @Override
    public String getShortLink(String longLink) {
        // 校验longLink
        if (longLink == null || "".equals(longLink)) {
            // 记录日志
            log.error("入参错误，不能为空：" + longLink);
            return "";
        }
        // 获取机器ID，这里写在文件中，后期通过ZooKeeper管理
        String machineId = fleOperateHelper.readFile("machineId");
        // 生成短链接
        String code = shortLinkHelper.createShortLinkCode(longLink);

        // 记录日志
        //log.error("缓存大小：" + guavaCacheHelper.size());
        int zeroBit = config.TOTAL_BIT - config.MACHINE_BIT - code.length();
        // 记录日志
        String generateCode = CommonUtil.generateZeroString(zeroBit);
        // 机器ID作为前缀
        code = machineId + generateCode + code;

        // 创建短链接Bean
        ShortLinkBean shortLinkBean = new ShortLinkBean();
        shortLinkBean.setShortLink(code);
        shortLinkBean.setLongLink(longLink);
        shortLinkBean.setExpireTime(System.currentTimeMillis() + config.EXPIRE_SEC * 1000);
        // 如果存在（可能误判）
        if (bloomFilterHelper.mightContain(code)) {
            // 从缓存中取对象
            ShortLinkBean oldShortLinkBean = (ShortLinkBean) guavaCacheHelper.get(code);
            // 如果不存在误判为存在，则直接将新的数据写入缓存中
            if (oldShortLinkBean == null) {
                // 把短链接放入Guava缓存中
                guavaCacheHelper.put(code, shortLinkBean);
                // 把短链接放入布隆过滤器
                bloomFilterHelper.put(code);
                // 记录日志
                log.warn("布隆过滤器误判： new code: " + code + " ; new link: " + longLink);
            }
            // 如果确实存在
            else {
                String oldLongLink = oldShortLinkBean.getLongLink();
                // 判断是否Hash冲突了(code相同，长链接url不同)
                if (code.equals(oldShortLinkBean.getShortLink()) && !longLink.equals(oldLongLink)) {
                    // 记录日志
                    log.warn("Hash冲突, old and new code: " + code + "; old link: " + oldLongLink + " ; new link: "
                            + longLink);
                    // 构造新code
                    String newHashCode = shortLinkHelper.createShortLinkCode(
                            DuplicatedEnum.DUPLICATED.getKey() + "_" + oldLongLink);
                    // 新的补0位数
                    zeroBit = config.TOTAL_BIT - config.MACHINE_BIT - newHashCode.length();
                    // 补0字符串
                    generateCode = CommonUtil.generateZeroString(zeroBit);
                    // code加上枚举前缀后再取Hash，生成新的短链接
                    String newCode = machineId + generateCode + newHashCode;
                    // 长链接加上前缀
                    String newLongLink = DuplicatedEnum.DUPLICATED.getKey() + "_" + longLink;
                    log.error("Hash冲突解决： new code: " + newCode + "; old link: " + oldShortLinkBean.getLongLink()
                            + " ; new link: " + newLongLink);
                    // 设置新的短链接
                    shortLinkBean.setShortLink(newCode);
                    // 设置新的长链接
                    shortLinkBean.setLongLink(newLongLink);
                    // 把短链接放入Guava缓存中
                    guavaCacheHelper.put(newCode, shortLinkBean);
                    // 把短链接放入布隆过滤器
                    bloomFilterHelper.put(newCode);
                    // 将新的短链接返回给调用方
                    return newCode;
                }
                // 未冲突，已存在数据，不做处理，既不放到缓存中，也不放到过滤器中
                else {
                    // 记录日志
                    log.info("已存在： code " + code + " ; longLink: " + longLink);
                }
            }
        }
        // 通过布隆过滤器判断：如果不存在（100%正确），则直接放入缓存中
        else {
            // 把短链接放入Guava缓存中
            guavaCacheHelper.put(code, shortLinkBean);
            // 把短链接放入布隆过滤器
            bloomFilterHelper.put(code);
        }
        // 记录日志
        // log.info("Hash冲突集合大小： " + hashCodeSet.size());
        // 将短链接返回给调用方
        return code;
    }

    @Override
    public String getLongLink(String shortLink) {
        // 校验
        if (shortLink == null || "".equals(shortLink)) {
            // 记录日志
            log.error("入参错误，不能为空：" + shortLink);
            return "";
        }
        // 从缓存中获取对象
        ShortLinkBean shortLinkBean = (ShortLinkBean) guavaCacheHelper.get(shortLink);
        // 如果不存在，则记日志，然后返回空
        if (shortLinkBean == null) {
            log.warn("缓存中不存在 " + shortLink + " 对应的长链接");
            return "";
        }
        // 如果存在，处理长链接
        else {
            // 取出长链接
            String longLink = shortLinkBean.getLongLink();
            // 如果不存在Hash冲突标记，则直接返回长链接
            if (!longLink.startsWith(DuplicatedEnum.DUPLICATED.getKey())) {
                return longLink;
            }
            // 否则去掉冲突标记后再返回
            else {
                log.warn("去掉冲突标记后再返回：" + longLink);
                longLink = longLink.replace(DuplicatedEnum.DUPLICATED.getKey() + "_", "");
            }
            // 将长链接返回给调用方
            return longLink;
        }
    }
}