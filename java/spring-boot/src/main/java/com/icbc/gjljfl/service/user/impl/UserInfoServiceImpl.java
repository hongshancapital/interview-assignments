package com.icbc.gjljfl.service.user.impl;

import com.icbc.gjljfl.common.util.EncryptUtil;
import com.icbc.gjljfl.entity.UserInfo;
import com.icbc.gjljfl.entity.dto.user.UserInfoDTO;
import com.icbc.gjljfl.entity.dto.user.UserInfoDetailDTO;
import com.icbc.gjljfl.entity.dto.user.UserInfoRegisterDTO;
import com.icbc.gjljfl.entity.enums.CommonEnum;
import com.icbc.gjljfl.entity.exception.ParamErrorException;
import com.icbc.gjljfl.mapper.UserInfoMapper;
import com.icbc.gjljfl.service.user.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: gaowh
 * @Description:
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 用户信息保存
     * 不包含修改用户名、密码、手机号操作
     *
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo save(UserInfo userInfo) {
        String userId = userInfo.getUserId();
        Date date = new Date();
        if (userId == null || userId.isEmpty()) {
            // 新增
            userId = userIdGenerate();
            userInfo.setUserId(userId);

            userInfo.setCreateTime(date);
            userInfo.setModifyTime(date);
            userInfo.setLastLoginTime(date);
            userInfo.setUserStatusc(CommonEnum.ABLE.getKey());

            String userPwd = EncryptUtil.pwdEncrypt(userInfo.getUserPwd(), userInfo.getUserId());
            userInfo.setUserPwd(userPwd);

            userInfoMapper.insert(userInfo);
        } else {
            // 修改
            UserInfo originalUserInfo = getUserByUserId(userId);
            if (originalUserInfo == null) {
                throw new ParamErrorException("该用户不存在");
            }
            originalUserInfo.setUserSex(userInfo.getUserSex());
            originalUserInfo.setName(userInfo.getName());
            originalUserInfo.setAddress(userInfo.getAddress());
            originalUserInfo.setCommunityId(userInfo.getCommunityId());
            originalUserInfo.setModifyTime(date);
            userInfoMapper.updateDetail(originalUserInfo);
        }
        return userInfo;
    }

    /**
     * 更新用户登录信息
     *
     * @param userInfo 用户登录信息
     * @return
     */
    @Override
    public void updateLoginMsg(UserInfo userInfo) {
        userInfoMapper.updateLoginMsg(userInfo);
    }

    /**
     * 更新用户密码
     *
     * @param userInfo
     */
    @Override
    public void updatePwd(UserInfo userInfo) {
        String userPwd = EncryptUtil.pwdEncrypt(userInfo.getUserPwd(), userInfo.getUserId());
        userInfo.setUserPwd(userPwd);
        userInfoMapper.updatePwd(userInfo);
    }

    /**
     * 获取用户信息
     *
     * @param userName 用户名
     * @param mobile   手机号码
     * @param userType 用户类型
     * @return
     */
    @Override
    public UserInfo getUser(String userName, String mobile, String userType) {
        return userInfoMapper.getByUserNameOrMobile(userName, mobile, userType);
    }

    /**
     * 校验用户信息唯一
     *
     * @param userName 用户名
     * @param mobile   手机号码
     * @param userType 用户类型
     * @return
     */
    @Override
    public void unique(String userName, String mobile, String userType) {
        UserInfo user = getUser(userName, mobile, userType);
        if (user != null) {
            if (userName.equals(user.getUserName())) {
                throw new ParamErrorException("该用户名已被占用");
            } else if (mobile.equals(user.getMobile())) {
                throw new ParamErrorException("该手机号码已被占用");
            }
        }
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public UserInfo getUserByUserId(String userId) {
        return userInfoMapper.getByUserId(userId);
    }

    /**
     * 获取用户信息
     *
     * @param userName 用户名
     * @param userType 用户类型
     * @return
     */
    @Override
    public UserInfo getUserByUserName(String userName, String userType) {
        return userInfoMapper.getByUserName(userName, userType);
    }

    /**
     * 获取用户信息
     *
     * @param mobile   手机号码
     * @param userType 用户类型
     * @return
     */
    @Override
    public UserInfo getUserByMobile(String mobile, String userType) {
        return userInfoMapper.getByMobile(mobile, userType);
    }

    /**
     * 密码比较器
     *
     * @param userName
     * @param userType
     * @param pwd
     * @return
     */
    @Override
    public Boolean pwdCompare(String userName, String userType, String pwd) {
        UserInfo user = getUserByUserName(userName, userType);
        if (user == null) {
            throw new ParamErrorException("该用户不存在");
        }
        return user.getUserPwd().equals(EncryptUtil.pwdEncrypt(pwd, user.getUserId()));
    }

    /**
     * 将传输对象转换为数据库对象
     *
     * @param dto 传输对象
     * @return 数据库对象
     */
    @Override
    public UserInfo convertDO(UserInfoRegisterDTO dto) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(dto, userInfo);
        return userInfo;
    }

    /**
     * 将传输对象转换为数据库对象
     *
     * @param dto 传输对象
     * @return 数据库对象
     */
    @Override
    public UserInfo convertDO(UserInfoDetailDTO dto) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(dto, userInfo);
        return userInfo;
    }

    /**
     * 将数据库对象转换为传输对象
     *
     * @param userInfo
     * @return
     */
    @Override
    public UserInfoDTO convertDTO(UserInfo userInfo) {
        UserInfoDTO dto = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo, dto);
        return dto;
    }

    /**
     * userId 生成规则
     *
     * @return
     */
    private String userIdGenerate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
