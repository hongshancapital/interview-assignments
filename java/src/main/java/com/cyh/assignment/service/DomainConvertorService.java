package com.cyh.assignment.service;


import com.cyh.assignment.cache.LRUCache;
import com.cyh.assignment.constant.Constants;
import com.cyh.assignment.constant.Exceptions;
import com.cyh.assignment.util.DomainConvertUtil;
import com.cyh.assignment.util.DomainIndexGeneratorUtil;
import com.cyh.assignment.util.UrlLegalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class DomainConvertorService implements IDomainConvertorService{

    private static final LRUCache<String,String> fullToShort = new LRUCache<>();

    private static final LRUCache<String,String> shortToFull = new LRUCache<>();

    @Override
    public String getFullDomain(String shortDomain) throws Exception {
        String fullDomain = operateCache(null,shortDomain,Constants.ONLY_OPERATE_SHORT);
        if (StringUtils.isEmpty(fullDomain)) {
            throw new Exception(Exceptions.ERROR_SHORT_NOT_FOUND);
        }
        return fullDomain;
    }

    @Override
    public String saveShortDomain(String fullDomain) throws Exception {
        if (Constants.IS_MEMORY_ENOUGH.get()) {
            throw new Exception(Exceptions.ERROR_MEMORY_NOT_ENOUGH);
        }
        if (!UrlLegalUtil.isUrlLegal(fullDomain)) {
            throw new Exception(Exceptions.ERROR_FULL_DOMAIN_ILLEGAL);
        }
        String shortDomain = operateCache(fullDomain,null,Constants.ONLY_OPERATE_FULL);
        if (StringUtils.isEmpty(shortDomain)) {
            long domainIndex = DomainIndexGeneratorUtil.generateNextDomainIndex();
            shortDomain = DomainConvertUtil.indexToShort(domainIndex);
        }
        operateCache(fullDomain,shortDomain,Constants.OPERATE_BOTH);
        return shortDomain;
    }

    // 将对cache的操作集中到一个synchronized方法中，保证数据一致性和LRU的准确性
    private synchronized String operateCache(String fullDomain, String shortDomain,int mode) {
        // TODO: 添加断言，检查输入的参数是否正确
        String res = null;
        switch (mode) {
            case Constants.ONLY_OPERATE_SHORT:
                res = shortToFull.get(shortDomain);
                fullToShort.get(res);
                break;
            case Constants.ONLY_OPERATE_FULL:
                res = fullToShort.get(fullDomain);
                shortToFull.get(res);
                break;
            case Constants.OPERATE_BOTH:
                fullToShort.put(fullDomain,shortDomain);
                shortToFull.put(shortDomain,fullDomain);
                break;
            default:
                res = "input error";
        }
        return res;
    }


}
