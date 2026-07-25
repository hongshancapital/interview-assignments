package ks.sequoia.aware.impl;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import ks.sequoia.aware.CacheServiceAware;
import ks.sequoia.bobj.DomainBObj;
import ks.sequoia.eobj.DomainEObj;
import ks.sequoia.eobj.LRU;
import ks.sequoia.utils.IdFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public abstract class AbstractCacheServiceImpl implements CacheServiceAware {

    /**
     * 计算因子，011111b,计算去除digits的数组下标
     */
    private static final int DEFAULT_FACTOR = (1 << 6) - 1;
    /**
     * 长域名大于8时，最大的迭代次数
     */
    private static final int TOTAL = 1 << 3;
    /**
     * 每次迭代的跨度
     */
    private static final int SPAN = 1 << 2;
    /**
     * 前7段和最后一段的计算因子
     */
    private static final int LAST_CAL_FACTOR = (TOTAL - 1) * SPAN;
    /**
     * int类型的最大bit
     */
    private static final int MAX_BIT = 1 << 5;

    /**
     * 初始化缓存容量
     */
    protected static final int INITIAL_CAPACITY = 102400;

    //2^6
    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '-', '_'};

    @Resource
    private DomainBObj domainBObj;
    /**
     * 长域名的内存存储对象,初始化容量是10240W,避免内存移除,保存热点数据
     */
    protected Map<String, DomainEObj> longMappingMap = new ConcurrentHashMap<>(INITIAL_CAPACITY);
    /**
     * 短域名的内存存储对象,初始化容量是1W,避免内存移除,保存热点数据
     */
    protected Map<String, DomainEObj> shortMappingMap = new ConcurrentHashMap<>(INITIAL_CAPACITY);

    /**
     * LRU队列 ,当内存不足以写入新数据时候，移除最近少使用的key
     */
    protected LRU<Long, DomainEObj> lruList = new LRU<>(INITIAL_CAPACITY);

    @PostConstruct
    private void init() {
        List<DomainEObj> domainEObjList = domainBObj.queryLatest10000Times();
        if (domainEObjList == null || domainEObjList.size() == 0) {
            return;
        }
        for (DomainEObj domainEObj : domainEObjList) {
            lruList.put(domainEObj.getDomainId(), domainEObj);
            addCache(domainEObj.getLongDomain(), domainEObj, longMappingMap);
            addCache(domainEObj.getShortDomain(), domainEObj, shortMappingMap);
        }
    }

    //短域名长度最大为 8 个字符
    protected DomainEObj transformShortDomain(String longDomain) {
        DomainEObj domainEObj = longMappingMap.get(longDomain);
        //使用HashCode方法和equals生成短域名
        if (domainEObj == null) {
            //得到longDomain的Hash值，^和>>>减少Hash碰撞
            StringBuffer sb = new StringBuffer();
            int pos = hash(longDomain) & DEFAULT_FACTOR;//元素的第一个位置
            sb.append(digits[pos]);
            if (longDomain.length() < 8) {
                char[] chars = longDomain.toCharArray();
                for (char c : chars) {
                    int p = hash(c) & DEFAULT_FACTOR;
                    sb.append(digits[p]);
                }
            } else {
                int hashCode = longDomain.hashCode();
                //将hashCode一分为8，最后一位参与^运算，减少hash碰撞
                for (int ind = 1; ind < TOTAL; ind++) {
                    int cal = hashCode >> ((MAX_BIT - ind * SPAN)) ^ (hashCode >>> LAST_CAL_FACTOR);
                    int p = cal & DEFAULT_FACTOR;
                    sb.append(digits[p]);
                }
            }
            DomainEObj domain = this.getDomainBObj().queryEObjByShortDomain(sb.toString());
            if (domain != null) {
                DomainEObj one = new DomainEObj();
                domainEObj.setLongDomain(longDomain);
                domainEObj.setShortDomain(sb.toString());
                domainEObj.setCreateTime(new Timestamp(System.currentTimeMillis()));
                domainEObj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                return one;
            }
            return addDomainEObj(longDomain, sb.toString());
        }
        return domainEObj;
    }


    private DomainEObj addDomainEObj(String longDomain, String shortDomain) {
        DomainEObj domainEObj = new DomainEObj();
        domainEObj.setLongDomain(longDomain);
        domainEObj.setShortDomain(shortDomain);
        domainEObj.setCreateTime(new Timestamp(System.currentTimeMillis()));
        domainEObj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        this.getDomainBObj().addEObj(domainEObj);
        return domainEObj;
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private void addCache(String flag, DomainEObj domainEObj, Map<String, DomainEObj> cacheMap) {
        if (cacheMap.get(flag) == null) {
            cacheMap.put(flag, domainEObj);
        } else {
            throw new RuntimeException("repeat domain name[" + flag + "]");
        }
    }

    public DomainBObj getDomainBObj() {
        return domainBObj;
    }

}