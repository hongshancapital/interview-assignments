package com.icbc.gjljfl.controller;

import com.alibaba.fastjson.JSONObject;
import com.icbc.gjljfl.common.ResponseEntity;
import com.icbc.gjljfl.common.util.EncryptUtil;
import com.icbc.gjljfl.entity.UserInfo;
import com.icbc.gjljfl.entity.dto.user.*;
import com.icbc.gjljfl.entity.enums.UserTypeEnum;
import com.icbc.gjljfl.entity.exception.ParamErrorException;
import com.icbc.gjljfl.service.user.UserInfoService;
import com.icbc.gjljfl.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @Author: gaowh
 * @Description: 用户注册、登录、信息变更
 **/
@RestController
@RequestMapping("/user/")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户注册，注册成功无需再度登录
     * 这里的注册仅为普通用户注册
     *
     * @param dto
     * @return
     */
    @PostMapping("info/register/v1")
    public ResponseEntity register(@Valid @RequestBody UserInfoRegisterDTO dto, HttpServletRequest request) {
        UserInfo userInfo = userInfoService.convertDO(dto);
        // 这里的注册仅为普通用户注册
        userInfo.setUserType(UserTypeEnum.USER.getKey());
        // 校验用户名/手机号码是否被注册过（用户名+用户类型唯一，手机号码+用户类型唯一）
        userInfoService.unique(userInfo.getUserName(), userInfo.getMobile(), userInfo.getUserType());
        // 注册并登录
        userInfo = setLoginMsg(userInfo, request);
        userInfo = userInfoService.save(userInfo);
        return ResponseUtils.toSuccess(userInfo.getUserId());
    }

    /**
     * 完善用户信息
     *
     * @param dto
     * @return
     */
    @PostMapping("info/complete/v1")
    public ResponseEntity detail(@Valid @RequestBody UserInfoDetailDTO dto) {
        UserInfo userInfo = userInfoService.convertDO(dto);
        userInfoService.save(userInfo);
        return ResponseUtils.toSuccess();
    }

    /**
     * 用户更改密码
     *
     * @param dto
     * @return
     */
    @PostMapping("info/pwd/v1")
    public ResponseEntity changePwd(@Valid @RequestBody UserInfoChangePwdDTO dto) {
        UserInfo user = userInfoService.getUserByUserId(dto.getUserId());
        if (user == null) {
            throw new ParamErrorException("该用户不存在");
        } else {
            Boolean success = userInfoService.pwdCompare(user.getUserName(), user.getUserType(), dto.getUserOldPwd());
            if (success) {
                user.setUserPwd(dto.getUserNewPwd());
                userInfoService.updatePwd(user);
                return ResponseUtils.toSuccess();
            } else {
                throw new ParamErrorException("旧密码错误");
            }
        }
    }

    /**
     * 用户密码登录
     *
     * @param dto
     * @return
     */
    @PostMapping("login/pwd/v1")
    public ResponseEntity loginByPwd(@Valid @RequestBody UserInfoLoginByPwdDTO dto, HttpServletRequest request) {
        UserInfo user = userInfoService.getUserByUserName(dto.getUserName(), dto.getUserType());
        if (user == null) {
            throw new ParamErrorException("该用户不存在");
        }
        Boolean success = userInfoService.pwdCompare(dto.getUserName(), dto.getUserType(), dto.getUserPwd());
        if (success) {
            user = updateLoginMsg(user, request);
            JSONObject result = new JSONObject();
            result.put("userId", user.getUserId());
            result.put("communityId", user.getCommunityId());
            result.put("userType",user.getUserType());
            return ResponseUtils.toSuccess(result);
        } else {
            throw new ParamErrorException("密码错误");
        }
    }

    /**
     * 校验用户信息唯一
     *
     * @param userName 用户名
     * @param mobile   手机号码
     * @param userType 用户类型
     * @return
     */
    @GetMapping("info/unique/v1")
    public ResponseEntity unique(String userName, String mobile, String userType) {
        userInfoService.unique(userName, mobile, userType);
        return ResponseUtils.toSuccess();
    }

    /**
     * 获取用户信息接口
     *
     * @param userId
     * @return
     */
    @GetMapping("info/get/v1")
    public ResponseEntity getUserInfo(String userId) {
        UserInfo user = userInfoService.getUserByUserId(userId);
        if (user == null) {
            throw new ParamErrorException("该用户不存在");
        } else {
            UserInfoDTO dto = userInfoService.convertDTO(user);
            return ResponseUtils.toSuccess(dto);
        }
    }

    /**
     * 用户加密
     * @param userId  用户id
     * @return
     */
    @GetMapping("pwdEncrypt/v1")
    public ResponseEntity pwdEncrypt(String userId, String password) {
        return ResponseUtils.toSuccess(EncryptUtil.salt(password + userId));
    }

    @GetMapping("salt/v1")
    public ResponseEntity salt(String userId) {
        UserInfo user = userInfoService.getUserByUserId(userId);
        if (user == null) {
            throw new ParamErrorException("该用户不存在");
        }
        return ResponseUtils.toSuccess(EncryptUtil.salt(userId));
    }

    /**
     * 设置登录信息
     *
     * @param userInfo
     * @param request
     * @return
     */
    private UserInfo setLoginMsg(UserInfo userInfo, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        userInfo.setLoginIp(ip);
        userInfo.setLastLoginTime(new Date());
        return userInfo;
    }

    /**
     * 更新登录信息
     *
     * @param userInfo
     * @param request
     * @return
     */
    private UserInfo updateLoginMsg(UserInfo userInfo, HttpServletRequest request) {
        userInfo = setLoginMsg(userInfo, request);
        userInfoService.updateLoginMsg(userInfo);
        return userInfo;
    }
}
