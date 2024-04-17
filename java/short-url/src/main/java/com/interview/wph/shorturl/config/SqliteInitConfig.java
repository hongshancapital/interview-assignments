package com.interview.wph.shorturl.config;

import cn.hutool.core.date.SystemClock;
import com.interview.wph.shorturl.common.utils.BloomUtil;
import com.interview.wph.shorturl.common.utils.LogUtil;
import com.interview.wph.shorturl.service.impl.ShortUrlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteException;

import javax.annotation.PostConstruct;
import java.sql.*;

/**
 * @author wangpenghao
 * @date 2022/4/19 19:38
 */
@Configuration
public class SqliteInitConfig {
    @Value("${spring.datasource.url}")
    private String sqliteUrl;
    @Autowired
    private ShortUrlServiceImpl shortUrlService;


    @PostConstruct
    public void initShortUrlDB() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(sqliteUrl);
            Statement stmt = conn.createStatement();
            String createTableSql = "create table if not exists t_short_url_v2(`short_url_id` int PRIMARY KEY  NOT NULL,  `long_url`  varchar(1024) NOT NULL" +
                    ",UNIQUE INDEX `long_url` (`long_url`) USING HASH" +
                    ");";


            String createSql = "CREATE TABLE if not exists `t_short_url` (\n" +
                    "  `short_url_id` bigint(20) NOT NULL,\n" +
                    "  `long_url_hash` int(11) NOT NULL,\n" +
                    "  `long_url` varchar(1024) NOT NULL,\n" +
                    "  PRIMARY KEY (`short_url_id`)\n" +
                    ");";
            String indexSql = "CREATE INDEX if not exists h_index ON t_short_url (long_url_hash);";
            try {
                //建表、建索引
               stmt.executeUpdate(createSql);
               stmt.executeUpdate(indexSql);
            }catch (SQLiteException e){
                System.out.println("创建表失败");
                throw e;
            }
            //添加到布隆过滤其中,查找所有数据并shortUrlService.getBaseMapper().listAllShortId()
            long start = SystemClock.now();
            shortUrlService.getBaseMapper().listAllShortId().parallelStream().forEach(BloomUtil::addId);
            LogUtil.info(this.getClass(), "更新bloom用时:{}ms,共更新{}条记录", (SystemClock.now() - start), shortUrlService.count());
        }catch (SQLException sqlE){
            System.out.println("获取连接失败");
            sqlE.printStackTrace();
            throw sqlE;
        }
    }
}
