package com.scdt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class ApplicationTests {


    @Autowired
    private MockMvc mockMvc;

//	@Test
//	void testLongToShort() throws Exception {
//		AtomicLong atomicLong = new AtomicLong(0);
//		UrlMapService.setCounter(atomicLong);
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/longToShort?url=http://www.amazonk.com")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcRequestBuilders.status().isOk())
//				.andExpect(content().string(containsString("\"code\":200")));
//	}
//
//	@Test
//	void testLongToShortBranch() throws Exception {
//		double pow = Math.pow(62, 8);
//		AtomicLong atomicLong = new AtomicLong(new Double(pow - 1).longValue());
//		UrlMapService.setCounter(atomicLong);
//		Assert.assertNotNull(urlMapService.longToShort("http://www.amazon.com"));
//		mockMvc.perform(post("/longToShort?url=http://www.amazon.com")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(content().string(containsString("\"code\":500")));
//	}
//
//	@Test
//	void testLongToShortFail() throws Exception {
//		mockMvc.perform(post("/longToShort?url=www.amazons.com")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(content().string(containsString("\"code\":500")));
//	}
//
//	@Test
//	void testShortToLongFail() throws Exception {
//		mockMvc.perform(get("/shortToLong/00000000N")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(content().string(containsString("\"code\":500")));
//	}
//
//	@Test
//	void testShortToLongSuccess() throws Exception {
//		AtomicLong atomicLong = new AtomicLong(1);
//		UrlMapService.setCounter(atomicLong);
//
//		mockMvc.perform(post("/longToShort?url=http://www.amazons.com")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(content().string(containsString("\"code\":200")));
//
//		mockMvc.perform(get("/shortToLong/0000W")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(content().string(containsString("\"code\":200")));
//	}
//
//	@Test
//	void stressTesting() throws Exception {
//		StopWatch myWatch = new StopWatch("myWatch");
//		for (int i = 0; i < 10000; i++) {
//			String url = "/longToShort?url=http://www." + i + ".com";
//			myWatch.start("task" + (myWatch.getTaskCount()+1));
//			mockMvc.perform(post(url)
//					.accept(MediaType.APPLICATION_JSON))
//					.andExpect(status().isOk())
//					.andExpect(content().string(containsString("\"code\":200")));
//			myWatch.stop();
//		}
//		System.out.println(myWatch.prettyPrint());
//	}

}
