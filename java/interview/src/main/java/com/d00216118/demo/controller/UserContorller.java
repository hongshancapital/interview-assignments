package com.d00216118.demo.controller;

import com.d00216118.demo.model.User;
import com.d00216118.demo.model.UserPostData;
import com.d00216118.demo.service.SecurityTokenServiceImpl;
import com.d00216118.demo.service.UserServiceImpl;
import com.d00216118.demo.util.ReponseMessage;
import com.d00216118.demo.util.VerifyCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


//import javax.validation.Valid;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 7:21 下午 2021/3/30
 **/

@RestController
@RequestMapping("/user")
public class UserContorller {

    private  UserServiceImpl userService;

    @Autowired
    private  SecurityTokenServiceImpl securityTokenService;

    public UserContorller(UserServiceImpl userService){
        this.userService=userService;
    }


    /**
     * this method is used to obtain token
     * @param userPostData
     * @return
     */
    @PostMapping("/authorize")
    public Map<String, Object> createToken(@Valid @RequestBody UserPostData userPostData){

        User user= userService.getUser(userPostData.getUsername(), userPostData.getPassword());
//        System.out.println(user);

        if(null==user){
            return ReponseMessage.failedResult("1","the user not found!");
        }

        //Check whether the sign is correct, used to verify the security during transmission
        if(!VerifyCenter.veriry(userPostData.getSign(),userPostData.getUsername(), userPostData.getPassword(),userPostData.getTimestamp())){
            return ReponseMessage.failedResult("1","the Sign is error!");
        }

        //The token expiration time is 30 minutes
        String token= securityTokenService.createToken(user.getId()+"", (30 * 60 * 1000));
        return ReponseMessage.successResult(token);

    }

}
