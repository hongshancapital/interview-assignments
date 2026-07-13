package com.example.sequoiahomework;

import com.example.sequoiahomework.common.response.DataResult;
import com.example.sequoiahomework.config.Container;
import com.example.sequoiahomework.controller.ChangeUrlController;
import com.example.sequoiahomework.rest.MyRequest;
import com.example.sequoiahomework.vo.url.ChangeUrlVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Irvin
 * @description 接口测试类
 * @date 2021/10/10 13:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApiTest {

    @Resource
    private MyRequest myRequest;

    @Resource
    private Container container;

    @Resource
    private ChangeUrlController changeUrlController;

    @Test
    public void tMethod() {
        //获取一批url
        List<String> urlList = getUrlList();
        urlList.forEach(url -> {
            //发起请求，常转短
            DataResult<String> resultForLts = request2(url, 1, "请求常转短，请求为{}，返回为{}");
            //判断请求结果
            if (resultForLts.getCode() == HttpStatus.OK.value()) {
                //获取短链接并请求短转长
                String shortUrl = resultForLts.getData();
                request2(shortUrl, 2, "请求短转长，请求为{}，返回为{}");

                //验证另一个功能，同一个长链接返回短短链接是否一样
                log.info("第二次常转短之前的容器size{}", container.longKey.size());
                //发起请求，常转短
                DataResult<String> resultForLts2 = request2(url, 1, "2次请求常转短，请求为{}，返回为{}");
                if (resultForLts2.getCode() == HttpStatus.OK.value()) {
                    log.info("两次长转短的短链接比对结果{}", shortUrl.equals(resultForLts2.getData()));
                    log.info("第二次常转短之后的容器size{}", container.longKey.size());
                }
            }
        });
    }


    @Test
    public void tMethodE() {
        request2("2131", 1, "请求常转短，请求为{}，返回为{}");

        request2("sad", 2, "请求常转短，请求为{}，返回为{}");
    }


    @Test
    public void tApi() {
        //获取一批url
        List<String> urlList = getUrlList();
        urlList.forEach(url -> {
            //发起请求，常转短
            DataResult<String> resultForLts = request(url, 1, "请求常转短，请求为{}，返回为{}");
            //判断请求结果
            if (resultForLts.getCode() == HttpStatus.OK.value()) {
                //获取短链接并请求短转长
                String shortUrl = resultForLts.getData();
                request(shortUrl, 2, "请求短转长，请求为{}，返回为{}");

                //验证另一个功能，同一个长链接返回短短链接是否一样
                log.info("第二次常转短之前的容器size{}", container.longKey.size());
                //发起请求，常转短
                DataResult<String> resultForLts2 = request(url, 1, "2次请求常转短，请求为{}，返回为{}");
                if (resultForLts2.getCode() == HttpStatus.OK.value()) {
                    log.info("两次长转短的短链接比对结果{}", shortUrl.equals(resultForLts2.getData()));
                    log.info("第二次常转短之后的容器size{}", container.longKey.size());
                }
            }
        });
        //异常请求
        DataResult<String> resultForLtsE1 = request("sad", 1, "请求常转短，请求为{}，返回为{}");
        DataResult<String> resultForLtsE2 = request(null, 1, "请求常转短，请求为{}，返回为{}");
        DataResult<String> resultForLtsE3 = request("sad", 2, "请求常转短，请求为{}，返回为{}");
        DataResult<String> resultForLtsE4 = request(null, 2, "请求常转短，请求为{}，返回为{}");
    }

    private DataResult<String> request(String url, int type, String logMessage) {
        ChangeUrlVo vo = new ChangeUrlVo();
        vo.setOriginalUrl(url);

        DataResult<String> result;
        if (type == 1) {
            result = myRequest.longToShort(vo);
        } else {
            result = myRequest.shortToLong(vo);
        }
        log.info(logMessage, vo, result);
        return result;
    }


    private DataResult<String> request2(String url, int type, String logMessage) {
        ChangeUrlVo vo = new ChangeUrlVo();
        vo.setOriginalUrl(url);

        DataResult<String> result;
        if (type == 1) {
            result = changeUrlController.longToShort(vo);
        } else {
            result = changeUrlController.shortToLong(vo);
        }
        log.info(logMessage, vo, result);
        return result;
    }

    private List<String> getUrlList() {
        String ou = "https://github.com/scdt-china/interview-assignments";
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            urlList.add(ou + i);
        }
        return urlList;
    }
}
