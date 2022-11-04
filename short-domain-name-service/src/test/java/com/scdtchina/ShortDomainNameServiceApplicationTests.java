package com.scdtchina;

import com.scdtchina.common.NumberGenerator;
import com.scdtchina.controller.ShortDomainNameController;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class ShortDomainNameServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(new ShortDomainNameController()).build();
	}

	@Test
	void testSdnLdn() throws Exception {

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/sdn?longDomainName=http://www.baidu.com")
				.accept(MediaType.TEXT_PLAIN)).andDo(print()).andReturn();
		MvcResult resultRes = mockMvc.perform(MockMvcRequestBuilders.get("/ldn?shortDomainName="+result.getResponse().getContentAsString())
				.accept(MediaType.TEXT_PLAIN)).andDo(print()).andReturn();
		assertTrue("true","http://www.baidu.com".equals(resultRes.getResponse().getContentAsString()));


		MvcResult resultNUll = mockMvc.perform(MockMvcRequestBuilders.get("/sdn?longDomainName=")
				.accept(MediaType.TEXT_PLAIN)).andDo(print()).andReturn();
		assertTrue("true","无效链接".equals(resultNUll.getResponse().getContentAsString()));

		MvcResult resultLength1 = null;
		for(int i=0;i<10001;i++){
			resultLength1 = mockMvc.perform(MockMvcRequestBuilders.get("/sdn?longDomainName=http://www.baidu.com")
					.accept(MediaType.TEXT_PLAIN)).andDo(print()).andReturn();
		}
		assertTrue("true","系统容量已达上限".equals(resultLength1.getResponse().getContentAsString()));


		MvcResult resultLength = mockMvc.perform(MockMvcRequestBuilders.get("/ldn?shortDomainName=12345")
				.accept(MediaType.TEXT_PLAIN)).andDo(print()).andReturn();
		assertTrue("true","系统容量已达上限".equals(resultLength.getResponse().getContentAsString()));

		MvcResult resultNull = mockMvc.perform(MockMvcRequestBuilders.get("/ldn?shortDomainName=")
				.accept(MediaType.TEXT_PLAIN)).andDo(print()).andReturn();
		assertTrue("true","无效链接".equals(resultNull.getResponse().getContentAsString()));

	}

	@Test
	void testNumberGenerator(){
		String result1 = NumberGenerator.getInstance().encode(123456L,1);
		String result63 = NumberGenerator.getInstance().encode(123456L,63);
		String result10 = NumberGenerator.getInstance().encode(123456L,10);
		String result62 = NumberGenerator.getInstance().encode(123456L,62);
		assertTrue("true",123456L == NumberGenerator.getInstance().decode(result1,1));
		assertTrue("true",123456L == NumberGenerator.getInstance().decode(result63,63));
		assertTrue("true",123456L == NumberGenerator.getInstance().decode(result10,10));
		assertTrue("true",123456L == NumberGenerator.getInstance().decode(result62,62));

	}


}
