package com.d00216118.demo.controller;

import com.d00216118.demo.dto.TinyUrlRequestDTO;
import com.d00216118.demo.dto.TinyUrlResponseDTO;
import com.d00216118.demo.dto.UrlRequestDTO;
import com.d00216118.demo.dto.UrlResponseDTO;
import com.d00216118.demo.model.InfoUrl;
import com.d00216118.demo.model.User;
import com.d00216118.demo.service.SecurityServiceImpl;
import com.d00216118.demo.service.UrlServiceImpl;
import com.d00216118.demo.service.UserServiceImpl;
import com.d00216118.demo.util.ReponseMessage;
import com.d00216118.demo.util.VerifyCenter;
import lombok.SneakyThrows;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.util.Map;

@RequestMapping("/url")
@RestController
public class TinyURLController {

    private final UrlServiceImpl urlService;

    private final SecurityServiceImpl securityService;

    private final UserServiceImpl userService;

    public TinyURLController(UrlServiceImpl urlService, SecurityServiceImpl securityService, UserServiceImpl userService) {
        this.urlService = urlService;
        this.securityService = securityService;
        this.userService = userService;
    }

    @SneakyThrows
    @PostMapping("/createurl")
    public Map<String, Object> convertTinyUrl(@Valid @RequestBody UrlRequestDTO urlRequestDTO) {
        if (!VerifyCenter.verifyUrlRequestDTO(urlRequestDTO)) {
            return ReponseMessage.failedResult("1", "the Sign is error!");
        }

        if (!VerifyCenter.veriryUrl(urlRequestDTO.getUrl())) {
            return ReponseMessage.failedResult("1", "The url format is wrong, it must start with http, https, ftp, mailto, etc!");
        }

        String userId = securityService.getSubject(urlRequestDTO.getToken());
        if (userId != null && !userId.isEmpty()) {
            User u = userService.getUser(Long.parseLong(userId));
            if (u.getUsername().compareToIgnoreCase(urlRequestDTO.getUsername()) == 0) {
                if (!urlService.checkUrlExist(urlRequestDTO.getUrl(), u.getId())) {
                    InfoUrl saveInfoUrl = new InfoUrl();
                    saveInfoUrl.setUrl(URLDecoder.decode(urlRequestDTO.getUrl(), "utf-8"));
                    saveInfoUrl.setUserId(u.getId());
                    InfoUrl infoUrl = urlService.convertToTinyUrl(saveInfoUrl);
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    String sign = infoUrl.getTinyUrl() + "|" + u.getUsername() + "|" + timestamp;
                    return ReponseMessage.successResult(new UrlResponseDTO(timestamp, "http://xx.xx/" + infoUrl.getTinyUrl(), u.getUsername(), DigestUtils.md5DigestAsHex(sign.getBytes())));
                } else {
                    return ReponseMessage.failedResult("1", "the url already exists !");
                }
            } else {
                return ReponseMessage.failedResult("1", "the user is not authorized !");
            }
        } else {
            return ReponseMessage.failedResult("1", "the user is not authorized !");
        }
    }

    @SneakyThrows
    @PostMapping("/retrieveurl")
    public Map<String, Object> retrievetUrl(@Valid @RequestBody TinyUrlRequestDTO tinyUrlRequestDTO) {

        if (!VerifyCenter.verifyTinyUrlRequestDTO(tinyUrlRequestDTO)) {
            return ReponseMessage.failedResult("1", "the Sign is error!");
        }

        if (!VerifyCenter.veriryUrl(tinyUrlRequestDTO.getTinyUrl())) {
            return ReponseMessage.failedResult("1", "The tiny url format is wrong, it must start with http, https, ftp, mailto, etc!");
        }

        String userId = securityService.getSubject(tinyUrlRequestDTO.getToken());
        if (userId != null && !userId.isEmpty()) {
            User u = userService.getUser(Long.parseLong(userId));
            if (u.getUsername().compareToIgnoreCase(tinyUrlRequestDTO.getUsername()) == 0) {
                String tinyUrl = URLDecoder.decode(tinyUrlRequestDTO.getTinyUrl(), "utf-8");
                String temp = tinyUrl.substring(tinyUrl.length() - 8, tinyUrl.length());
                InfoUrl infoUrl = urlService.getUrlByTinyUrl(temp, u.getId());
                if (infoUrl != null) {
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    String sign = infoUrl.getUrl() + "|" + u.getUsername() + "|" + timestamp;
                    return ReponseMessage.successResult(new TinyUrlResponseDTO(timestamp, infoUrl.getUrl(), u.getUsername(), DigestUtils.md5DigestAsHex(sign.getBytes())));
                } else {
                    return ReponseMessage.failedResult("1", "the tiny url is error!");
                }
            } else {
                return ReponseMessage.failedResult("1", "the user is not authorized !");
            }
        } else {
            return ReponseMessage.failedResult("1", "the user is not authorized !");
        }
    }

}
