package net.iapploft.springboot.service.impl;

import net.iapploft.springboot.service.DatabaseRangeManager;
import net.iapploft.springboot.service.IShortLinkService;
import net.iapploft.springboot.utils.LRULinkedHashMap;
import net.iapploft.springboot.utils.NumericConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class IShortLinkServiceImpl implements IShortLinkService {

    /**
     * 假设一条记录最大为1024个字节 以16G内存为例 最大容量为 1024 * 1024 * 1024 * 16
     * 防止内存溢出使用LRU算法丢弃最近最少使用缓存对象
     */
    private final static LRULinkedHashMap<String,String> linkMap = new LRULinkedHashMap<String,String>(1024 * 1024 * 1024 * 8L);
    private final static LRULinkedHashMap<String,String> codeMap = new LRULinkedHashMap<String,String>(1024 * 1024 * 1024 * 8L);


    // 从1000亿开始
    private AtomicReference<BigInteger> atomicBigInteger = new AtomicReference<>(new BigInteger("100000000000"));

    @Autowired
    private DatabaseRangeManager databaseRangeManager;

    public final BigInteger getAndIncrement() {
        for ( ; ; ) {
            BigInteger current = atomicBigInteger.get();
            BigInteger next = current.add(BigInteger.ONE);
            if (atomicBigInteger.compareAndSet(current, next)) {
                return next;
            }
        }
    }
    // 基地址
    @Value("${base.url}")
    private String baseUrl;

    @Override
    public String getShortLinkByLink(String link) {
        String shortCode = "";
        // 检查是否已经生成过
        if ( linkMap.get(link) != null ) {
            shortCode = NumericConvertUtils.Base10ToBase62(new BigInteger(linkMap.get(link)));
        }else {
            // 添加缓存
            BigInteger code = getAndIncrement();
            shortCode = NumericConvertUtils.Base10ToBase62(code);
            linkMap.put(link, code.toString());
            codeMap.put(code.toString(),link);
            // 存储DB
            ConcurrentHashMap<String,String> db = databaseRangeManager.getAbleDatabaseByCode(code);
            db.put(code.toString(),link);
            db.put(code.toString(),link);
        }
        return baseUrl + shortCode;
    }

    @Override
    public String getLinkByShortLink(String shortLink) throws Exception {
        // 获取短连接码
        String shortCode = shortLink.substring(baseUrl.length());
        BigInteger bigInteger = NumericConvertUtils.Base62ToBase10(shortCode);
        // 检查是否缓存
        String orgLink = codeMap.get(bigInteger.toString());
        if (orgLink == null) {
            // 从数据库查找shortKey 如果存在则 加入lruLinkedHashMap 并返回orgLink
            ConcurrentHashMap<String,String> db = databaseRangeManager.getAbleDatabaseByCode(bigInteger);
            orgLink = db.get(shortCode);
            if (orgLink == null) {
                // 不存在则抛出异常
                throw new Exception("该连接码不存在！");
            }
            // 添加缓存
            linkMap.put(orgLink, shortCode);
            codeMap.put(bigInteger.toString(),orgLink);
        }
        return orgLink;

    }

//    public static void main(String[] args) {
//        BigInteger bigInteger = new BigInteger("100000000000000");
//        //LRULinkedHashMap<String,String> linkMap = new LRULinkedHashMap<String,String>(999);
//        for (int i = 0 ; i < 10000 ; i++) {
//            linkMap.put(bigInteger.toString(),bigInteger.toString()+bigInteger.toString()+bigInteger.toString()+bigInteger.toString()+bigInteger.toString()+bigInteger.toString());
//            bigInteger = bigInteger.add(BigInteger.ONE);
//        }
//        System.out.println(linkMap.size());
//    }
}
