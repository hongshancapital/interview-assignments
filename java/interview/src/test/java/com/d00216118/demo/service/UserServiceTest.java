package com.d00216118.demo.service;

import com.d00216118.demo.model.User;
import com.d00216118.demo.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 7:40 下午 2021/3/30
 **/
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    private UserServiceImpl userServiceImpl;

    @Before
    public void init() {
        this.userServiceImpl = new UserServiceImpl(userRepository);
    }

    @Test
    public void getUserTest() {
        User user1 = new User(new Long("3"), "test3", "test3");
        Optional<User> userOptional = Optional.ofNullable(user1);
        when(userRepository.findById(any())).thenReturn(userOptional);
        User user2 = userServiceImpl.getUser(new Long(3));
        assertThat(userOptional.orElse(null)).isEqualTo(user2);
        verify(userRepository,times(1)).findById(new Long(3));
    }

    @Test
    public  void getUserByUP(){
        User user1 = new User(new Long("3"), "test3", "test3");
        when(userRepository.findByUsernameAndPassword("tests3","test3")).thenReturn(user1);
        User user2= userServiceImpl.getUser("tests3","test3");
        assertThat(user2).isNotNull();
        verify(userRepository, times(1)).findByUsernameAndPassword("tests3","test3");
    }
}
