package ks.sequoia;


import ks.sequoia.aware.CacheServiceAware;
import ks.sequoia.aware.impl.AbstractCacheServiceImpl;
import ks.sequoia.aware.impl.DomainCacheServiceImpl;
import ks.sequoia.bobj.DomainBObj;
import ks.sequoia.controller.DomainController;
import ks.sequoia.eobj.DomainEObj;
import ks.sequoia.eobj.LRU;
import ks.sequoia.utils.IdFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Slf4j
@SpringBootTest(classes = Application.class)
@Transactional(transactionManager = "transactionManager")
@Data
public class DomainTest {


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Resource
    private DomainBObj domainBObj;

    @Resource
    private DomainCacheServiceImpl cacheService;
    @Resource
    DomainController domainController;



    @Before
    public void before() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();   //构造MockMvc
    }
    @Test
    public void testLongDomain() throws Exception {
        // Get发送请求
        String longDomain = "";
        String url = "/ks/queryEObjByLongDomain?longDomain" +longDomain;
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("客户端获的数据:" + result);
    }

    @Test
    public void testShortDomain() throws Exception {
        // Get发送请求
        String shortDomain = "";
        String url = "/ks/queryEObjByShortDomain?shortDomain" +shortDomain;
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("客户端获的数据:" + result);
    }

    @Test
    public void testIdFactory(){
         System.out.println("test"+IdFactory.nextId());
         System.out.println(  IdFactory.getDataId());
         System.out.println(IdFactory.getWorkId());
        System.out.println(IdFactory.tilNextMillis(System.currentTimeMillis()));
    }

    @Test
    public void testLRU(){
        LRU<Long, DomainEObj> lru = new LRU<>(100);
        lru.size();
        DomainEObj domainEObj = new DomainEObj();
        domainEObj.setLongDomain("11");
        domainEObj.setShortDomain("22");
        domainEObj.setCreateTime(new Timestamp(System.currentTimeMillis()));
        domainEObj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        this.getDomainBObj().addEObj(domainEObj);
        domainBObj.getSqlSessionTemplate();
        domainBObj.queryEObjByLongDomain("http://www.360doc.com/content/12/0731/14/1073512_227467421.shtml");
        domainBObj.queryLatest10000Times();
        domainBObj.queryEObjByShortDomain("baidu");
        domainBObj.deleteEObyById(123L);
        lru.put(123L,domainEObj);
        lru.get(123L);
        lru.getHead();
        lru.remove(123L);
        lru.size();
        lru.contain(123L);
        lru.toString();
    }

    @Test
    public void testService(){
        cacheService.queryEObjByLongDomain("http://www.360doc.com/content/12/0731/14/1073512_227467421.shtml");
        cacheService.queryEObjByLongDomain("www.36");
        cacheService.queryEObjByLongDomain(null);

        cacheService.queryEObjByShortDomain("testet");
        cacheService.queryEObjByShortDomain("http://www.360doc.com/content/12");
        cacheService.test("http://www.360doc.com/content/12");


    }/*

    @Test
    public void testController(){
        domainController.queryEObjByLongDomain("http://www.360doc.com/content/12/0731/14/1073512_227467421.shtml");
        domainController.queryEObjByShortDomain("http://www.360doc.com/content/12/0731/14/1073512_227467421.shtml");

    }
*/

}
