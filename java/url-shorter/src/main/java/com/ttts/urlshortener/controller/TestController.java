package com.ttts.urlshortener.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.ttts.urlshortener.base.model.BaseResult;
import com.ttts.urlshortener.domain.ExcelShortUrl;
import com.ttts.urlshortener.service.UrlQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(value = "测试接口")
public class TestController {

    private UrlQueryService urlQueryService;

    @Autowired
    public TestController(UrlQueryService urlQueryService) {
        this.urlQueryService = urlQueryService;
    }

    @SneakyThrows(IOException.class)
    @ApiOperation(value = "导出短链数据")
    @RequestMapping(value = "/surl/export", method = RequestMethod.GET)
    public void exportShortUrls(HttpServletResponse response) {
        setExcelRespProp(response, String.format("shorturl-%s", LocalDateTimeUtil.formatNormal(
            LocalDateTime.now())));
        List<ExcelShortUrl> records = urlQueryService.listAllExcelShortUrl();
        EasyExcel.write(response.getOutputStream())
            .registerConverter(new LongStringConverter())
            .head(ExcelShortUrl.class)
            .excelType(ExcelTypeEnum.XLSX)
            .sheet("短链数据")
            .doWrite(records);
    }

    /**
     * 设置excel下载响应头属性
     */
    private void setExcelRespProp(HttpServletResponse response, String rawFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(rawFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }
}
