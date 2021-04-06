package com.d00216118.demo.controller;

import com.d00216118.demo.dto.UserRequestDTO;
import com.d00216118.demo.dto.UserResponseDTO;
import com.d00216118.demo.model.User;
import com.d00216118.demo.service.SecurityServiceImpl;
import com.d00216118.demo.service.UserServiceImpl;
import com.d00216118.demo.util.ReponseMessage;
import com.d00216118.demo.util.VerifyCenter;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 7:21 下午 2021/3/30
 **/

@RestController
@RequestMapping("/user")
public class UserController {

    //    @Autowired
    private final UserServiceImpl userService;

    //    @Autowired
    private final SecurityServiceImpl securityService;

    public UserController(UserServiceImpl userService, SecurityServiceImpl securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }


    /**
     * this method is used to obtain token
     *
     */
    @PostMapping("/authorize")

    public Map<String, Object> createToken(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User user = userService.getUser(userRequestDTO.getUsername(), userRequestDTO.getPassword());

        if (null == user) {
            return ReponseMessage.failedResult("1", "the user not found!");
        }

        //Check whether the sign is correct, used to verify the security during transmission
        if (!VerifyCenter.verifyUserRequestDTO(userRequestDTO)) {
            return ReponseMessage.failedResult("1", "the Sign is error!");
        }

//        The token expiration time is 30 minutes and user ID is object
        String token = securityService.createToken(user.getId() + "", (30 * 60 * 1000));
        String timestamp = String.valueOf(System.currentTimeMillis());
        String sign = token + "|" + user.getUsername() + "|" + timestamp;
        return ReponseMessage.successResult(new UserResponseDTO(token, timestamp, user.getUsername(), DigestUtils.md5DigestAsHex(sign.getBytes())));
    }


}
