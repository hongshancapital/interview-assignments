package com.d00216118.demo.repo;

import com.d00216118.demo.model.User;
import com.d00216118.demo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author Yu Chen
 * @Email D00216118@student.dkit.ie
 * @Date 2:19 下午 2021/3/29
 **/
//@SpringBootTest
@RunWith(SpringRunner.class)
    @DataJpaTest
public class UserRepTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void getUser() {
        User user = new User(new Long("3"), "test3", "test3");
        testEntityManager.persist(user);
        testEntityManager.flush();
        User actual = userRepository.findByUsernameAndPassword("test3", "test3");

//        User actual =userRepository.findById(new Long(5)).orElse(new User());
        System.out.println(actual);
        assertThat(actual).isEqualTo(user);
    }
}
