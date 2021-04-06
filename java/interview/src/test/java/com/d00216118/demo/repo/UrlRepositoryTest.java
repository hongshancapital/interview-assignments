package com.d00216118.demo.repo;

import com.d00216118.demo.model.InfoUrl;
import com.d00216118.demo.repository.UrlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 1:16 下午 2021/4/2
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
    @DataJpaTest
public class UrlRepositoryTest {
    @Autowired
    private UrlRepository urlRepository;


    @Test
    public void save(){
        InfoUrl infoUrl=new InfoUrl();
        infoUrl.setMd5Url("21321");
        infoUrl.setTinyUrl("fsfsf");

        InfoUrl t=urlRepository.save(infoUrl);
        System.out.println(t);

    }
}
