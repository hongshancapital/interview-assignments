package com.icbc.gjljfl.mapper;

import com.icbc.gjljfl.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    @Select("SELECT * FROM user_info " +
            "WHERE user_type = #{userType} " +
            "AND (user_name = #{userName} OR mobile = #{mobile})")
    UserInfo getByUserNameOrMobile(@Param("userName") String userName,
                                   @Param("mobile") String mobile,
                                   @Param("userType") String userType);

    @Select("SELECT * FROM user_info " +
            "WHERE user_id = #{userId}")
    UserInfo getByUserId(@Param("userId") String userId);

    @Select("SELECT * FROM user_info " +
            "WHERE user_type = #{userType} AND user_name = #{userName}")
    UserInfo getByUserName(@Param("userName") String userName,
                           @Param("userType") String userType);

    @Select("SELECT * FROM user_info " +
            "WHERE user_type = #{userType} AND mobile = #{mobile}")
    UserInfo getByMobile(@Param("mobile") String mobile,
                         @Param("userType") String userType);

    @Update("UPDATE user_info " +
            "SET " +
            "user_sex = #{userSex}, name = #{name}, address = #{address}, " +
            "community_id = #{communityId}, modify_time = #{modifyTime} " +
            "WHERE id = #{id}")
    int updateDetail(UserInfo userInfo);

    @Update("UPDATE user_info " +
            "SET " +
            "last_login_time = #{lastLoginTime}, login_ip = #{loginIp} " +
            "WHERE id = #{id}")
    int updateLoginMsg(UserInfo userInfo);

    @Update("UPDATE user_info " +
            "SET " +
            "user_pwd = #{userPwd} " +
            "WHERE id = #{id}")
    int updatePwd(UserInfo userInfo);

    /*根据用户名,用户类型,用户状态查询该用户是否是否存在*/
    List<UserInfo> selectByUsernameAndUserType(Map<String, String> params);

    /*根据手机号,用户类型,用户状态查询该用户是否是否存在*/
    List<UserInfo> selectByMobileAndUserType(Map<String, String> params);

    /*批量删除用户的注册信息,即:将当前用户的有效状态置位无效*/
    void deleteByUserIds(@Param("userIds") List<String> userIds);

    /*多条件查询环卫工人*/
    List<Map<String, String>> selectUsersByMoreCondition(Map<String, Object> map);

    /*有选择性的更新用户信息*/
    void updateByUserIdSelective(UserInfo userInfo);

    /*根据用户id 查询用户信息*/
    UserInfo selectByUserId(@Param("userId") String userId);

    UserInfo selectByuserId(String userId);

    // <!--查询本社区的用户-->
    List<Map<String, String>> selectCommunityUser(Map<String, Object> map);

}