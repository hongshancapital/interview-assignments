package com.ascmix.surl.controller;

import com.ascmix.surl.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping("/api/v1/link")
    public ResponseEntity<String> create(@RequestBody Map<String, String> params) {
        if (!params.containsKey("url")) {
            return ResponseEntity.badRequest().body("missing param 'url'");
        }
        String url = params.get("url");
        String key = linkService.shorten(url);
        return ResponseEntity.ok(key);
    }

    @DeleteMapping("/api/v1/link/{key}")
    public ResponseEntity<String> delete(@PathVariable("key") String key) {
        linkService.delete(key);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{key}")
    public ResponseEntity<String> redirect(@PathVariable("key") String key) {
        String url = linkService.get(key);
        if (!StringUtils.hasText(url)) {
            return ResponseEntity.badRequest().body("unknown key: " + key);
        }
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, url).build();
    }
}
