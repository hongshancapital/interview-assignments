package com.icbc.gjljfl.service.user;

import com.icbc.gjljfl.entity.UserInfo;
import com.icbc.gjljfl.entity.dto.user.UserInfoDTO;
import com.icbc.gjljfl.entity.dto.user.UserInfoDetailDTO;
import com.icbc.gjljfl.entity.dto.user.UserInfoRegisterDTO;

/**
 * @Author: gaowh
 * @Description:
 **/
public interface UserInfoService {

    /**
     * 用户信息保存
     *
     * @param userInfo
     * @return
     */
    UserInfo save(UserInfo userInfo);

    /**
     * 更新用户登录信息
     *
     * @param userInfo 用户登录信息
     * @return
     */
    void updateLoginMsg(UserInfo userInfo);

    /**
     * 更新用户密码
     *
     * @param userInfo
     */
    void updatePwd(UserInfo userInfo);

    /**
     * 获取用户信息
     *
     * @param userName 用户名
     * @param mobile   手机号码
     * @param userType 用户类型
     * @return
     */
    UserInfo getUser(String userName, String mobile, String userType);

    /**
     * 校验用户信息唯一
     * 若不唯一，则抛出异常
     *
     * @param userName 用户名
     * @param mobile   手机号码
     * @param userType 用户类型
     */
    void unique(String userName, String mobile, String userType);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return
     */
    UserInfo getUserByUserId(String userId);

    /**
     * 获取用户信息
     *
     * @param userName 用户名
     * @param userType 用户类型
     * @return
     */
    UserInfo getUserByUserName(String userName, String userType);

    /**
     * 获取用户信息
     *
     * @param mobile   手机号码
     * @param userType 用户类型
     * @return
     */
    UserInfo getUserByMobile(String mobile, String userType);

    /**
     * 密码比较器
     *
     * @param userName
     * @param userType
     * @param pwd
     * @return
     */
    Boolean pwdCompare(String userName, String userType, String pwd);

    /**
     * 将传输对象转换为数据库对象
     *
     * @param dto 传输对象
     * @return 数据库对象
     */
    UserInfo convertDO(UserInfoRegisterDTO dto);

    /**
     * 将传输对象转换为数据库对象
     *
     * @param dto 传输对象
     * @return 数据库对象
     */
    UserInfo convertDO(UserInfoDetailDTO dto);

    /**
     * 将数据库对象转换为传输对象
     *
     * @param userInfo
     * @return
     */
    UserInfoDTO convertDTO(UserInfo userInfo);

}