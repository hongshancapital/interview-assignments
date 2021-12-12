package org.example.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.exception.UrlConvertorException;
import org.example.model.UrlConvertor;
import org.example.model.request.UrlRequest;
import org.example.model.response.ApiResult;
import org.example.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class DomainController {

    @Autowired
    private UrlConvertor urlConvertor;

    @ApiOperation("短域名存储接口：接受长域名信息，返回短域名信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "正常返回", response = ApiResult.class),
            @ApiResponse(code = 500, message = "服务器错误"),
    })
    @PostMapping(value = "/getShortUrl", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<String>> longToShort(@RequestBody UrlRequest urlRequest) {
        ApiResult<String> result = new ApiResult<>();
        if (!UrlUtils.checkUrl(urlRequest.getUrl())) {
            result.setErrorCode(ApiResult.INVALID_URL_CODE);
            result.setMessage("Invalid URL");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        try {
            result.setData(urlConvertor.createShortUrl(urlRequest.getUrl()));
        } catch (UrlConvertorException e) {
            result.setErrorCode(ApiResult.CONVERT_URL_FAILED_CODE);
            result.setMessage("Convert long URL failed");
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation("短域名读取接口：接受短域名信息，返回长域名信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "正常返回", response = ApiResult.class),
            @ApiResponse(code = 500, message = "服务器错误"),
    })
    @PostMapping(value = "/getLongUrl", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<String>> shortToLong(@RequestBody UrlRequest urlRequest) {
        ApiResult<String> result = new ApiResult<>();
        if (!UrlUtils.checkUrl(urlRequest.getUrl())) {
            result.setErrorCode(ApiResult.INVALID_URL_CODE);
            result.setMessage("Invalid URL");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        URI uri = URI.create(urlRequest.getUrl());
        result.setData(urlConvertor.getLongUrlByShortCode(uri.getPath().replaceAll("/", "")));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
