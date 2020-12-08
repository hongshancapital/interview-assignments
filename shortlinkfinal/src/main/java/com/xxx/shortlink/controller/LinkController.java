package com.xxx.shortlink.controller;

import com.xxx.shortlink.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkController {

    @Autowired
    private LinkService linkService;

    @RequestMapping("/generate")
    public String getShortLink(String link) {
        return linkService.save(link);
    }

    @RequestMapping("/getlink/{shortLink}")
    public String getLink(@PathVariable String shortLink){
        return linkService.get(shortLink);
    }

}
