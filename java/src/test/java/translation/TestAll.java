package translation;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import translation.model.ThePreTreeWrapBridge;

/**
 * 测试用例
 *
 * @author: hello
 * @since: 2023/2/22
 */
@SpringBootTest(classes = {SpringBootForTranslation.class})
@RunWith(SpringRunner.class)
//@FixMethodOrder(MethodSorters.DEFAULT)
@Slf4j
public class TestAll {
    MockMvc mockMvc;
    @Autowired
    ThePreTreeWrapBridge treeWrap;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setMock() {
        log.info("最先初始容器");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 测试采用Trie这种数据结构存储的可行性
     * 查和add 理论上为O(1)
     * 比HashMap要快更省内存
     */
    @Test
    @Order(0)
    public void testTheTree() throws Exception {
        log.info("先测试存储结构的抗压能力");
        long l0 = System.currentTimeMillis();
        treeWrap.test(null);//默认10W的当量,UUID去替换各种URL
        log.info("在测试清理能力");
        treeWrap.clearClear();
        long l1 = System.currentTimeMillis();
        log.info("抗压之后:" + l1 + ";总消耗时间为:{}ms;", l1 - l0);
    }

    @Test
    @Order(1)
    public void testUrl() throws Exception {
        log.info("测试接口");
        log.info("首先是长链接变为短链接的");
        String longUrl = "你好我是长链接_HelloWorld";//和上面共享1个实例
        String shortUrlResult = mockMvc.perform(MockMvcRequestBuilders.get("/url/longToShort?url=" + longUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        log.info("返回的短链接为:" + shortUrlResult);
        log.info("然后是根据短链接获得长链接");
        String longUrlResult = mockMvc.perform(MockMvcRequestBuilders.get("/url/shortToLong/" + shortUrlResult))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        log.info("最后返回的结果为:{};2者对比为:{};", longUrlResult, longUrl.equals(longUrlResult));
    }


}