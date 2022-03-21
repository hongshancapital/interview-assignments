package com.zjm.sdct_work.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Author:   billzzzhang
 * Date:     2022/3/20 下午9:03
 * Desc:
 */
@Service
public class TokenService {


    private Set<String> tokens = new HashSet<>();


    @PostConstruct
    public void init() {
        tokens.add("abcdefgh");
    }

    public boolean checkToken(String token) {
        return tokens.contains(token);
    }

}
