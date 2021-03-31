package com.d00216118.demo.repository;

import com.d00216118.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 2:11 下午 2021/3/29
 **/
public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsernameAndPassword(String username, String password);

}


