package com.example.domain.service.impl;

import com.example.domain.DomainServiceApplication;
import com.example.domain.service.ShortDomainGenerateRuleStrategy;
import com.example.domain.service.entity.Domain;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * DomainServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DomainServiceApplication.class)
public class DomainServiceImplTest {

    /**
     * 模拟mvc测试对象
     */
    private MockMvc mockMvc;



    @Resource
    private Map<String, Domain> domainMap;

    /**
     * web项目上下文
     */
    @Resource
    private WebApplicationContext webApplicationContext;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Mock
    ShortDomainGenerateRuleStrategy shortDomainGenerateRuleStrategy;

    @After
    public void after() throws Exception {
    }



    /**
     * Method: addDomainInfo(String domain)
     */
    @Test
    public void testAddDomainInfo() throws Exception {
     String domain = "baidu.com";
     when(shortDomainGenerateRuleStrategy.actionRule()).thenReturn(0L);
     Long id = (Long)shortDomainGenerateRuleStrategy.actionRule();
     Domain domain1 = new Domain();
     domain1.setDomain(domain);
     domain1.setId(id);
     String shortDomain = Base64Utils.encodeToString(domain.getBytes(StandardCharsets.UTF_8));
     domain1.setShortDomain(shortDomain);
     domainMap.put(shortDomain,domain1);
     Assert.assertTrue(StringUtils.equals(domain,domain1.getDomain()));
    }

    /**
     * Method: getDomain8ShortDomain(String shortDomain)
     */
    @Test
    public void testGetDomain8ShortDomain() throws Exception {
        String shortDomain = "YmFpZHUuY29t";
        Domain domain = domainMap.get(shortDomain);
        Assert.assertNotNull(domain);
    }


} 
