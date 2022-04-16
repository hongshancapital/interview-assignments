package interview.assignments;

import interview.assignments.controller.ShortUrlController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhiran.wang
 * @date 2022/4/16 19:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {InterviewAssignmentsApplication.class})
public class TestShortUrlController {

    @Autowired
    private ShortUrlController controller;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void test() throws Exception {
        setUp();
        String shortUrl = mvc.perform(MockMvcRequestBuilders.get("/shortUrl/saveLongUrl?longUrl=http://www.baidu.com")).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("uMBrU3", shortUrl);

        String longUrl = mvc.perform(MockMvcRequestBuilders.get("/shortUrl/queryLongUrl?shortUrl=uMBrU3")).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("http://www.baidu.com", longUrl);

    }
}
