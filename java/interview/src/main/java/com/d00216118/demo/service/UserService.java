package com.d00216118.demo.service;

import com.d00216118.demo.model.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 7:25 下午 2021/3/30
 **/
public interface UserService {

    User getUser(Long id);

    User getUser(String username, String password);


}
