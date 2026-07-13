package cn.sequoiacap.links.web.controller;

import cn.sequoiacap.links.base.config.LinkConfig;
import cn.sequoiacap.links.base.utils.LinkUtil;
import cn.sequoiacap.links.entities.Link;
import cn.sequoiacap.links.entities.groups.Insert;
import cn.sequoiacap.links.service.LinkService;
import cn.sequoiacap.links.web.vo.Result;
import cn.sequoiacap.links.web.vo.ResultCodeEnum;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Liu Shide
 * @date : 2022/4/6 11:48
 * @description : URL(链接) 控制类，生成短地址，查询长地址
 */
@Slf4j
@Api(tags = "Link 接口，生成短地址，反查长地址")
@RequestMapping("/link")
@RestController
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 线程索，防止并发击穿缓存
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 随机数
     */
    private static final Random RANDOM = new Random();

    /**
     * 创建短链接
     * @param requestLink
     * @return
     */
    @ApiOperation("长链接创建短链接")
    @PostMapping
    public Result<Link> createShortLink(@RequestBody @Validated(Insert.class) Link requestLink) {
        // 获取 longLink 值
        String longLink = requestLink.getLongLink();

        // 转换code
        String shortCode = LinkUtil.generateShortCode(longLink);

        // 先查找
        Link link = linkService.getLinkByCode(shortCode);

        if(link == null) {
            // 如果是null，需要添加
            link = preventingCachePenetrationAdd(shortCode, longLink);
        } else {
            // code重复的判断
            link = codeRepeatedJudgment(longLink, link);
        }
        //返回
        return Result.success(ResultCodeEnum.CREATED, link);
    }


    /**
     * 根据长地址创建短地址
     * @param shortCode
     * @return
     */
    @ApiOperation("短链接获取长链接")
    @ApiImplicitParam(paramType="path",name = "shortCode", value = "短号", required = true, dataType = "String", example="ra6vAv")
    @GetMapping("/{shortCode}")
    public Result<Link> getLinkByCode( @PathVariable(required = true) String shortCode) {
        int codeLength = shortCode.length();
        // 如果 shortCode 不等于 6 则返回错误
        if( codeLength != LinkConfig.DIGITS ) {
            return Result.failure(ResultCodeEnum.PARAMS_IS_INVALID);
        }
        //查询
        Link link = linkService.getLinkByCode(shortCode);
        if(null == link) {
            // 返回没有找到数据信息
            return Result.failure(ResultCodeEnum.NOT_DATA_FOUND);
        } else {
            // 返回成功信息
            return Result.success(link);
        }

    }

    /**
     * 防止缓存渗透的添加
     * @param shortCode
     * @param longLink
     * @return
     */
    private Link preventingCachePenetrationAdd(String shortCode, String longLink) throws RuntimeException {
        // 为防止 缓存穿透、击穿导致多次操作内存，因场景不复杂，先简单的写成如下内容
        Link link = null;
        lock.lock();  // 开启锁
        try {
            // 高并发下需要双重检查，如果还是空说明真的没有缓存
            link = linkService.getLinkByCode(shortCode);
            if(null == link ) {
                log.info("shortCode={} , link is null, create link ", shortCode);
                // 创建 link 对象，并保存
                link = new Link();
                link.setShortCode(shortCode);
                link.setLongLink(longLink);
                linkService.addLink(link);
            }
        } finally {
            lock.unlock();  // 关闭锁
        }
        return link;
    }


    /**
     * code重复的判断
     * @param longLink
     * @param link
     * @return
     * @throws RuntimeException
     */
    private Link codeRepeatedJudgment(String longLink, Link link) throws RuntimeException {
        // 如果不为空，需要判断是否有重复
        if( !longLink.equals(link.getLongLink()) ) {
            // 如果2个longlink不一样，说明2个不同的link重复了，重新加随机码，重新生成短码
            int randomInt = RANDOM.nextInt(100000);
            String newLongLink = randomInt + longLink;
            // 使用 newLongLink 转换code，
            String shortCode = LinkUtil.generateShortCode(newLongLink);
            // 需要添加，使用新生成的shortCode 加原始的 longLink
            link = preventingCachePenetrationAdd(shortCode, longLink);
        }
        return link;
    }

}
