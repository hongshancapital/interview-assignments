package shorturl.server.server.application.handler;


import com.csvreader.CsvWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import shorturl.server.server.application.dto.UrlRequest;
import shorturl.server.server.application.dto.UrlResponse;
import shorturl.server.server.application.threadpool.CommonExecutor;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
@Component
public class StatisticsHandler implements Handler{

    @Autowired
    CommonExecutor commonExecutor;

    @Override
    public void handle(UrlRequest urlReq, UrlResponse urlResponse){
        //
        commonExecutor.runTask(
                ()->{
                    log.info("requestId {}async StaticsHandler", urlReq.getRequestId());
                    /*to Statistic */}
        );

    }



}
