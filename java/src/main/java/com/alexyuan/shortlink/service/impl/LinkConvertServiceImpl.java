package com.alexyuan.shortlink.service.impl;

import com.alexyuan.shortlink.common.functions.AutoIncreNumSender;
import com.alexyuan.shortlink.common.functions.CodecProcessor;
import com.alexyuan.shortlink.common.variant.CacheVariant;
import com.alexyuan.shortlink.config.SystemConfig;
import com.alexyuan.shortlink.exceptions.ImproperValueException;
import com.alexyuan.shortlink.exceptions.SystemErrorException;
import com.alexyuan.shortlink.service.LinkConvertService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Service
public class LinkConvertServiceImpl implements LinkConvertService {

    private static final Logger logger = LoggerFactory.getLogger(LinkConvertServiceImpl.class);

    @Autowired
    private SystemConfig config;

    private List<AutoIncreNumSender> autoIncreNumSenders;

    @Autowired
    private CaffeineCacheServiceImpl caffeineCacheService;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // TODO：分行的行机器ID。即八位短域名码的第一位。若实现以 proxy - searcher 配合负载均衡的多行多列模式，可实现平行扩展
    // TODO：该ID即为机器码。系统后台可通过RPC等方式从Master服务中获取自身机器码。
    private long row_id;

    /**
     * @description     服务初始化时首先构建发号器队列。该队列发号器数量由配置文件中的发号器位数决定。(当前设置为1位,故最多62个发号器)
     *                  同时初始化号码最大值.
     * TODO:            若实现多种发号器,可将初始化方法单独构建成一个通用方法. 例如使用Hash来计算短链(如Google MurmurHash等)
     */
    @PostConstruct
    private void init() {
        autoIncreNumSenders = new ArrayList<AutoIncreNumSender>();

        for (Long i = 0L; i < config.MAX_COL_NUM; i++) {
            autoIncreNumSenders.add(new AutoIncreNumSender(i, config.MAX_CODE_NUM));
            logger.debug("Initializing code generator..Generator[" + i + "] initialized.");
        }
    }

    /**
     * @description     短域名生成方法，简单的使用锁来确保读写的数据一致性
     * TODO:            后续可以不用锁, 以消息队列的形式来进行异步生成,发号器号段用满可自动停止读取,从而提升性能
     * @param long_link  输入的长域名String
     * @return
     */
    @Override
    public CacheVariant shortLinkGenerator(String long_link, boolean read_cache) {

        if (StringUtils.isEmpty(long_link)) {
            throw new ImproperValueException("Empty long_url.. Please Check");
        }

        // 是否开启优先检查现有缓存 (为方便压测设置一个开关选择不读取)
        if (read_cache) {
            CacheVariant temp_var = caffeineCacheService.cacheGet(long_link);
            if (null != temp_var) {
                return temp_var;
            }
        }

        AutoIncreNumSender selected_sender = null;
        Long cur_code;
        boolean succeed = false;

        String uniq_code = "";

        // 持续尝试获取, 直到所有分号器均不工作为止.
        // TODO: 可设置超时时间避免RT太久。
        // TODO: 查询扩展TODO写在Cache Service 中, 完整系统设计需考虑数据持久化, 如考虑使用离线+搜索引擎的方式可以在service中增加
        //       二阶段查询的方法

        // 记录当前时间准备
        long start_time = System.currentTimeMillis();
        while(!succeed) {
            try {
                readWriteLock.readLock().lock();
                if (null == autoIncreNumSenders || autoIncreNumSenders.size() == 0) {
                    throw new SystemErrorException("No working generator, Please contact the staff");
                }
                logger.debug("autoIncreNumSenders size:[" + autoIncreNumSenders.size()+']');
                // 随机选择一个发号器
                // TODO: 如为完整设计, 多行多列模式下, 行选择可交给负载均衡, 列选择也可以自定义多种均衡算法. 例如可以扩展多种不同类型的
                //       发号器并使用Tag进行区分来选择, 或者使用配置化的Weight权重来进行选择等方法
                int rand_idx = new Random().nextInt(autoIncreNumSenders.size());
                selected_sender = autoIncreNumSenders.get(rand_idx);
            } finally {
                readWriteLock.readLock().unlock();
            }

            cur_code = selected_sender.generateNum();
            if (null == cur_code) {
                try {
                    readWriteLock.writeLock().lock();
                    // 发号器列表仍然有可用机器
                    if (autoIncreNumSenders.size() > 0) {
                        autoIncreNumSenders.remove(selected_sender);
                    } else {
                        throw new SystemErrorException("All Num Sender run out their counter. Stop Service");
                    }
                } finally {
                    readWriteLock.writeLock().unlock();
                }

                // TODO：记录当前时间 若与 start_time 差值超过配置超时时间则直接跳出 返回错误
                long end_time = System.currentTimeMillis();
                logger.debug("Removing GeneratorID[" + selected_sender.getCol_num() + "] from working list.");
            } else {
                // TODO: 配置存在行ID时说明多行状态生效，即先计算行ID
                if (config.ROW_LENGTH > 0) {
                    uniq_code = uniq_code + CodecProcessor.encodeFromDecimal(this.row_id, config.ROW_LENGTH, config.SCALE);
                }

                if (config.COL_LENGTH > 0) {
                    uniq_code = uniq_code + CodecProcessor.encodeFromDecimal(selected_sender.getCol_num(), config.COL_LENGTH,
                            config.SCALE);
                }

                uniq_code = uniq_code + CodecProcessor.encodeFromDecimal(cur_code, config.CODE_LENGTH, config.SCALE);

                succeed = true;
            }
        }
        CacheVariant cacheVariant = new CacheVariant(uniq_code, uniq_code, long_link, String.valueOf(System.currentTimeMillis()));
        CacheVariant cacheRevert = new CacheVariant(long_link, uniq_code, long_link, String.valueOf(System.currentTimeMillis()));
        logger.debug("Url:[" + long_link + "], has been converted to: [" + uniq_code + "], Generator ID: [" + selected_sender.getCol_num() + "].");
        caffeineCacheService.cachePut(uniq_code, cacheVariant);
        caffeineCacheService.cachePut(long_link, cacheRevert);

        return cacheVariant;
    }

    /**
     * @decription       长域名查找要比短域名生成简单, 本项目主要是针对Caffeine Cache进行查找
     * @param short_link  输入的短域名String
     * @return
     */
    @Override
    public CacheVariant longLinkSearcher(String short_link) {
        if (StringUtils.isEmpty(short_link)) {
            throw new ImproperValueException("Empty short_link.. Please Check");
        }

        Object obj = caffeineCacheService.cacheGet(short_link);

        return null == obj ? null : ((CacheVariant) obj);
    }
}
