package boot.spring.test;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Test {
	@Autowired
	private MockMvc mvc;
    @org.junit.Test
    public void test() throws Exception{
    	String longUrl="https://baidu.com/123/4.html";
    	String shortUrl="http://swho.cn/w3hi";
    	
		mvc.perform(MockMvcRequestBuilders.get("/api/domainChangeAdd").param("longUrl", longUrl))
		.andExpect(MockMvcResultMatchers.status().isOk());
		mvc.perform(MockMvcRequestBuilders.get("/api/domainChangeQuery").param("shortUrl", shortUrl))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
    }
}