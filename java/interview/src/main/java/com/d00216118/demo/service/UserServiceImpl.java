package com.d00216118.demo.service;

import com.d00216118.demo.model.User;
import com.d00216118.demo.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 7:30 下午 2021/3/30
 **/
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(@NotEmpty Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    //'unless' means is if the method returns the result is null then does not cache
    @Cacheable(value="user", unless="#result == null")
    public User getUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }


}
