package com.cicc.domain.jacoco.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import com.cicc.domain.services.TinyURL;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TinyURLTest {
	
	public static String DEFAULT_LONG_URL = "https://www.jd.com/?cu=true&utm_source=baidu-pinzhuan&utm_medium=cpc&utm_campaign=t_288551095_baidupinzhuan&utm_term=0f3d30c8dba7459bb52f2eb5eba8ac7d_0_5c302666523f422fbbf1d2417a88e467";
    @Autowired
    private TinyURL tinyURL;
    
	@Test
	public void testCreateAndGetTinyUrl() {
        
		//Test tiny URL(Write)
		String tinyUrlSeq = tinyURL.createTinyUrl(DEFAULT_LONG_URL,6);
		//Expect that tinyUrlSeq is not Null and length is 6 chars
		Assert.assertNotNull(tinyUrlSeq);
		Assert.assertEquals(6, tinyUrlSeq.length());
		//IF len >8 ,expect source chars is returned.
	    tinyUrlSeq = tinyURL.createTinyUrl(DEFAULT_LONG_URL,10);
	    Assert.assertEquals(DEFAULT_LONG_URL, tinyUrlSeq);
	    
		//Test long URL(Read)
		String longUrlSeq = tinyURL.getLongUrl(tinyUrlSeq);
		//Expect that longUrl  is equivalent to default long url
		Assert.assertEquals(DEFAULT_LONG_URL,longUrlSeq );
	}

}
