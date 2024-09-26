package shorturl.server.server.application.server;

import com.github.benmanes.caffeine.cache.Cache;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import shorturl.server.server.application.dto.UrlRequest;
import shorturl.server.server.application.dto.UrlResponse;
import shorturl.server.server.application.handler.Handler;
import shorturl.server.server.application.handler.StatisticsHandler;
import shorturl.server.server.application.handler.UrlMapHandler;
import shorturl.server.server.application.util.ConversionUtil;
import shorturl.server.server.application.util.IdWorkerInstance;

@Slf4j
@Service
public class DefualtUrlMapServer implements UrlMapServer{

    private static final String LONG2SHORTURL = "长链接转短连接";
    private static final String SHORT2LONGURL = "根据短链接获取长链接";

    @Autowired
    UrlMapHandler urlMaphandler;

    @Autowired
    StatisticsHandler statisticsHandler;

    @Override
    public ResponseEntity getShortUrl(UrlRequest longUrlReq){
        //1.先在内存中查询关键字longUrl是否存在
        log.info("{} long Url {}",  longUrlReq.getRequestId(), longUrlReq.getLongUrl());
        UrlResponse resp = new UrlResponse();
        resp.setDescription(LONG2SHORTURL);
        urlMaphandler.handle(longUrlReq, resp);
        statisticsHandler.handle(longUrlReq, resp);
        return ResponseEntity.ok(resp);

    }

    @Override
    public ResponseEntity getLongUrl(UrlRequest shortUrlReq){
        log.info("{} short request {}",  shortUrlReq.getRequestId(), shortUrlReq.getShortUrl());
        UrlResponse resp = new UrlResponse();
        resp.setDescription(SHORT2LONGURL);
        urlMaphandler.handle(shortUrlReq, resp);
        statisticsHandler.handle(shortUrlReq, resp);
        return ResponseEntity.ok(resp);
    }

}
