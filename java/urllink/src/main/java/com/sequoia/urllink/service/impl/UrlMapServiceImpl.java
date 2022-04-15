package com.sequoia.urllink.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sequoia.urllink.base.exception.InvalidParamException;
import com.sequoia.urllink.base.model.ResultMessage;
import com.sequoia.urllink.base.model.ResultObject;
import com.sequoia.urllink.bean.GenCodeParam;
import com.sequoia.urllink.bean.UrlMap;
import com.sequoia.urllink.constant.Global;
import com.sequoia.urllink.dao.UrlMapMapper;
import com.sequoia.urllink.service.IUrlMapLockService;
import com.sequoia.urllink.service.IUrlMapService;
import com.sequoia.urllink.util.RedisCacheUtil;
import com.sequoia.urllink.util.ShortCodeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * URL映射表 服务实现类
 * </p>
 * @author liuhai
 * @date 2022.4.15
 */
@Service
public class UrlMapServiceImpl extends ServiceImpl<UrlMapMapper, UrlMap> implements IUrlMapService, InitializingBean {

    public static final String CACHE_PREFIX = "urllink-";
    @Value("${fulu.longUrlPrefix}")
    private String longUrlPrefix;
    @Value("${fulu.shortUrlPrefix}")
    private String shortUrlPrefix;
    @Autowired
    private UrlMapMapper urlMapMapper;
    @Autowired
    private IUrlMapLockService lockService;
    @Autowired
    private DataSource dataSource;
    private AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void afterPropertiesSet() throws Exception {
        createTable();
    }

    @Override
    public ResultMessage createTable() {
        if (!running.compareAndSet(false, true)) {
            return ResultMessage.createSuccessMessage("正在重建中。。。");
        }

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String year = null;
        String tableName = null;
        try {
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            year = now.format(formatter);
            String baseName = "url_map";
            String headYear = "2021";
            if (headYear.equals(year)) {
                return ResultObject.newSuccessMessage(headYear + " ，不需要重建表！", baseName);
            }

            now = now.minusYears(1);
            year = now.format(formatter);

            tableName = String.format("%s_%s", baseName, year);
            String rename = String.format("ALTER TABLE %s RENAME TO %s", baseName, tableName);
            String showTables = "SHOW TABLES";

            String sql = "CREATE TABLE IF NOT EXISTS %s  SELECT * FROM %s LIMIT 0";
            sql = String.format(sql, baseName, tableName);

            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(showTables);
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                if (tableName.equalsIgnoreCase(name)) {
                    return ResultObject.newSuccessMessage(year + "，已经存在！", tableName);
                }
            }

            statement.execute(rename);
            statement.execute(sql);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            running.set(false);
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }

        return ResultObject.newSuccessMessage(year + "，更名并重建成功！", tableName);
    }

    @Override
    @Transactional
    public List<String> genCodes(GenCodeParam param) {
        if (StringUtils.isBlank(param.getLongUrl())) {
            throw new InvalidParamException("长链不能为空！");
        }
        if (CollectionUtils.isNotEmpty(param.getAttachList()) && param.getAttachList().size() > 200) {
            throw new InvalidParamException("attachList大小不能超过200！");
        }

        int size = 1;
        LambdaQueryWrapper<UrlMap> wrapper = new QueryWrapper<UrlMap>().lambda().eq(UrlMap::getLongUrl, param.getLongUrl());
        if (CollectionUtils.isNotEmpty(param.getAttachList())) {
            wrapper.in(UrlMap::getAttach, param.getAttachList());
            size = param.getAttachList().size();
        } else {
            wrapper.isNull(UrlMap::getAttach);
            param.setAttachList(Arrays.asList(StringUtils.EMPTY));
        }
        List<String> resultList = Lists.newArrayListWithCapacity(size);

        /**
         * 处理存在的code
         */
        List<UrlMap> exists = urlMapMapper.selectList(wrapper);
        int existsSize = 0;
        Map<String, String> existsMap = Collections.EMPTY_MAP;
        if (CollectionUtils.isNotEmpty(exists)) {
            existsSize = exists.size();
            existsMap = Maps.newHashMap();
            for (UrlMap map : exists) {
                existsMap.put(map.getAttach(), map.getShortUrl());
            }
        }

        /**
         * 所有的都存在
         */
        if (existsSize > 0 && (size == 1 || size == existsSize)) {
            if (size == 1) {
                return Arrays.asList(exists.get(0).getShortUrl());
            }

            for (String attach : param.getAttachList()) {
                resultList.add(existsMap.get(attach));
            }
            return resultList;
        }

        /**
         * 有的手机号没有shortcode，需要生成
         */
        int genSize = size - existsSize;
        long start = lockService.takeRange(genSize);
        List<String> codeList = ShortCodeUtil.genShortCode(start, genSize);
        Set<String> codeSet = Sets.newHashSet();
        int idx = 0;
        List<UrlMap> urlMapList = Lists.newArrayListWithCapacity(genSize);
        UrlMap map = null;
        for (String attach : param.getAttachList()) {
            if (existsMap.containsKey(attach)) {
                resultList.add(existsMap.get(attach));
                continue;
            }

            map = new UrlMap();
            map.setShortCode(codeList.get(idx++));
            codeSet.add(map.getShortCode());
            map.setLongUrl(param.getLongUrl());
            map.setShortUrl(shortUrlPrefix + Global.SHORT_MIDDLE_PATH + "/" + map.getShortCode());
            map.setAttach(StringUtils.isBlank(attach) ? null : attach);
            map.setUrlStatus(UrlMap.Status.NORMAL.getCode());
            resultList.add(map.getShortUrl());
            urlMapList.add(map);

            RedisCacheUtil.getRedisTemplate().opsForValue().set(CACHE_PREFIX + map.getShortCode(),
                    JSON.toJSONString(map), 1, TimeUnit.DAYS);

            if (urlMapList.size() >= 100) {
                saveBatch(urlMapList, 100);
                urlMapList.clear();
            }
        }

        if (CollectionUtils.isNotEmpty(urlMapList)) {
            saveBatch(urlMapList, 100);
        }
        return resultList;
    }

    @Override
    @Transactional
    public String genCode(String longUrl, String attach) {

        GenCodeParam param = new GenCodeParam();
        param.setLongUrl(longUrl);
        if (StringUtils.isNotBlank(attach)) {
            param.setAttachList(Arrays.asList(attach));
        }
        List<String> codeList = genCodes(param);
        if (CollectionUtils.isNotEmpty(codeList)) {
            return codeList.get(0);
        }
        return null;
    }

    @Override
    public String getLongUrl(String shortCode) {
        // 自增url访问次数 todo 根据需求补充其他业务逻辑
        urlMapMapper.incrementVisit(shortCode);

        if (!RedisCacheUtil.getRedisTemplate().hasKey(CACHE_PREFIX + shortCode)) {
            return getAndCache(shortCode).getLongUrl();
        }
        UrlMap exists = JSON.parseObject(RedisCacheUtil.getRedisTemplate().opsForValue()
                .get(CACHE_PREFIX + shortCode).toString(), UrlMap.class);
        return exists.getLongUrl();
        // todo return 404 url
    }

    private UrlMap getAndCache(String shortCode) {
        // todo 缓存过期是否还能获取长链？
        UrlMap urlMap = urlMapMapper.selectOne(new QueryWrapper<UrlMap>().lambda().eq(UrlMap::getShortCode, shortCode));
        if (Objects.isNull(urlMap)) {
            return new UrlMap();
        }
        RedisCacheUtil.getRedisTemplate().opsForValue().set(CACHE_PREFIX + urlMap.getShortCode(),
                JSON.toJSONString(urlMap), 1, TimeUnit.DAYS);
        return urlMap;
    }

}
