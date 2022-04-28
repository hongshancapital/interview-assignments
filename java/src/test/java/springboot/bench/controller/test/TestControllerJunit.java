package springboot.bench.controller.test;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import springboot.bench.DomainApplication;
import springboot.bench.consts.Constants;
import springboot.bench.utils.RandomStrGenerator;
import springboot.bench.vo.QueryShortDomainRequest;
import springboot.bench.vo.SaveLongDomainRequest;

@SpringBootTest(classes= DomainApplication.class)
@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.MethodOrderer)
public class TestControllerJunit {
    
    @Autowired
    private MockMvc mockMvc;

    @Order ( 1 )
    @Test
    public void test001() throws Exception {
        System.out.println("/ping TEST ......");
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.get("/ping"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                /// .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String pingResultStr = saveResult.getResponse().getContentAsString();
        System.out.println(pingResultStr);
        Assertions.assertTrue(pingResultStr.equals("pong"), "/ping test fail");
        System.out.println("/ping OK ......\n");
    }
    
    // get的输入依赖save的输出，因而把两个接口的测试用例整合在一起
    @Order ( 2 )
    @Test
    public void test002() throws Exception {
        String longDomain = "http://abcd.io";
        SaveLongDomainRequest saveRequest = new SaveLongDomainRequest(longDomain);
        String postJson = JSON.toJSONString(saveRequest);
        
        // 1. 正常数据流程测试，先存再取
        System.out.println("/saveDomain TEST ......");
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/saveDomain").content(postJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                /// .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String saveResultStr = saveResult.getResponse().getContentAsString();
        System.out.println(saveResultStr);
        JSONObject jsonObject = JSONObject.parseObject(saveResultStr);
        int saveRetCode = (Integer) jsonObject.get("retCode");
        Assertions.assertTrue(saveRetCode == Constants.REVOKE_OK, "/save test fail");
        System.out.println("/saveDomain OK ......\n");
        
        String shortDomain = (String) jsonObject.get("retInfo");
        QueryShortDomainRequest queryRequest = new QueryShortDomainRequest(shortDomain);
        String queryJson = JSON.toJSONString(queryRequest);
        
        System.out.println("/getDomamin TEST ......");
        MvcResult queryResult = mockMvc.perform(MockMvcRequestBuilders.post("/getDomain").content(queryJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                /// .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String queryResultStr = queryResult.getResponse().getContentAsString();
        System.out.println(queryResultStr);
        JSONObject queryObject = JSONObject.parseObject(queryResultStr);
        int queryRetCode = (Integer) queryObject.get("retCode");
        Assertions.assertTrue(queryRetCode == Constants.REVOKE_OK, "/getDomain test fail");
        
        String queryLongDomain = queryObject.getString("retInfo");
        Assertions.assertTrue(queryLongDomain.equals(longDomain), "/getDomain long domain not match!");
        System.out.println("/getDomain OK ......");
    }
    
    // 异常流程测试，再次把已经存在的长链保存一次，测试是否返回异常信息：ERR_LONG_DOMAIN_EXISTS，retCode = -1
    @Order ( 3 )
    @Test
    public void test003() throws Exception {
        String longDomain = "http://abcd.io";
        SaveLongDomainRequest saveRequest = new SaveLongDomainRequest(longDomain);
        String postJson = JSON.toJSONString(saveRequest);
        
        System.out.println("/saveDomamin exception TEST ......");
        MvcResult saveResult2 = mockMvc.perform(MockMvcRequestBuilders.post("/saveDomain").content(postJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String saveResultStr2 = saveResult2.getResponse().getContentAsString();
        System.out.println(saveResultStr2);
        JSONObject jsonObject2 = JSONObject.parseObject(saveResultStr2);
        int saveRetCode2 = (Integer) jsonObject2.get("retCode");
        Assertions.assertTrue(saveRetCode2 == Constants.REVOKE_NOK, "/save test fail");
        System.out.println("/saveDomain exception check OK ......\n");
        String saveRetInfo2 = (String) jsonObject2.get("retInfo");
        Assertions.assertTrue(saveRetInfo2.equals(Constants.ERR_LONG_DOMAIN_EXISTS), "/save test fail");
    }
    
    // 异常流程测试，随机生成一个短链，调用getDomain查询，正常retCode = -1，retInfo = ERR_SHORT_DOMAIN_MAPPING_NOT_EXISTS
    @Order ( 4 )
    @Test
    public void test004() throws Exception {
        System.out.println("/getDomamin exception TEST ......");
        String randomShortDomain = RandomStrGenerator.genShortDomain();
        QueryShortDomainRequest queryRequest2 = new QueryShortDomainRequest(randomShortDomain);
        String queryJson2 = JSON.toJSONString(queryRequest2);
        MvcResult queryResult2 = mockMvc.perform(MockMvcRequestBuilders.post("/getDomain").content(queryJson2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String queryResultStr2 = queryResult2.getResponse().getContentAsString();
        System.out.println(queryResultStr2);
        JSONObject queryObject2 = JSONObject.parseObject(queryResultStr2);
        int queryRetCode2 = (Integer) queryObject2.get("retCode");
        Assertions.assertTrue(queryRetCode2 == Constants.REVOKE_NOK, "/getDomain exception test fail");
        
        String queryLongDomain2 = queryObject2.getString("retInfo");
        Assertions.assertTrue(queryLongDomain2.equals(Constants.ERR_SHORT_DOMAIN_MAPPING_NOT_EXISTS), "/getDomain exception test fail!");
        System.out.println("/getDomain exception test OK ......");
    }
    
    // 内存保护测试，超过最大存储键值队数量(MAX_CAPACITY = 100000), 后续再调用saveDomain返回retCode = -1, retInfo = ERR_DOMAIN_POOL_FULL
    @Order ( 5 )
    @Test
    public void test005() throws Exception {
        System.out.println("memOverloadExceptionTest TEST ......");
        // test 2 已经存了一对
        for (int i = 0; i < Constants.MAX_CAPACITY - 1; ++i) {
            String longDomain = RandomStrGenerator.getLongDomain();
            SaveLongDomainRequest saveRequest = new SaveLongDomainRequest(longDomain);
            String postJson = JSON.toJSONString(saveRequest);
            
            MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/saveDomain").content(postJson).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            String saveResultStr = saveResult.getResponse().getContentAsString();
            JSONObject jsonObject = JSONObject.parseObject(saveResultStr);
            int retCode = (Integer) jsonObject.get("retCode");
            Assertions.assertTrue(retCode == Constants.REVOKE_OK, "memOverloadExceptionTest test fail");
        }
        
        // 循环save MAX_CAPACITY, 后续调用则返回retCode = -1, retInfo = ERR_DOMAIN_POOL_FULL
        String longDomain = RandomStrGenerator.getLongDomain();
        SaveLongDomainRequest saveRequest = new SaveLongDomainRequest(longDomain);
        String postJson = JSON.toJSONString(saveRequest);
        
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/saveDomain").content(postJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String saveResultStr = saveResult.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(saveResultStr);
        int retCode = (Integer) jsonObject.get("retCode");
        String retInfo = (String) jsonObject.get("retInfo");
        Assertions.assertTrue(retCode == Constants.REVOKE_NOK, "memOverloadExceptionTest test fail");
        Assertions.assertTrue(retInfo.equals(Constants.ERR_DOMAIN_POOL_FULL), "memOverloadExceptionTest test fail");
        System.out.println("memOverloadExceptionTest test OK ......");
    }
    
    @Order ( 6 )
    @Test
    public void test006() throws Exception {
        System.out.println("randomStrGeneratorTest TEST ......");
        String randomShortDomain = RandomStrGenerator.genShortDomain();
        Assertions.assertTrue(randomShortDomain.length() == RandomStrGenerator.SHORT_DOMAIN_LEN + "http://".length(), "randomStrGeneratorTest test fail");
        System.out.println("/randomStrGeneratorTest test OK ......");
    }

    @Order ( 7 )
    @Test
    public void test007() throws Exception {
        Map<String, Object> options = DomainApplication.generateUndertowOptions();
        Assertions.assertTrue((Integer) options.get("server.undertow.io-threads") == 4, "undertowOptionsTest test fail");
        Assertions.assertTrue((Integer) options.get("server.undertow.worker-threads") == 32, "undertowOptionsTest test fail");
    }

}
