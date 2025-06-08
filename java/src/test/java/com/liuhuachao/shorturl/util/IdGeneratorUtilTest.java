package com.liuhuachao.shorturl.util; 

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

/**
 * IdGeneratorUtil Tester.
 * @author <liuhuachao>
 * @version 1.0
 * @since <pre>12/20/2021</pre>
 */
public class IdGeneratorUtilTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	/**
	 * Method: nextId()
	 */
	@Test
	public void testNextId() throws Exception {
		Set<Long> set = new HashSet<>();
		for(int i=0;i<20;i++){
			long id = IdGeneratorUtil.nextId();
			set.add(id);
		}
		String ids = StringUtils.joinWith(",",set);
		System.out.println(String.format("ids:%s",ids));

	}

} 
