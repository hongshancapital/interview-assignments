package com.oldnoop.shortlink.transfer;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.oldnoop.shortlink.storage.MemoryLinkStorage;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SequenceTransferTest {

	@Autowired
	private SequenceTransfer transfer;
	
	@MockBean
	private MemoryLinkStorage storage;
	
	@Test
	public void testExist() {
		String longLink = "http://github.com/scdt-china/interview-assignments";
		String shortLink = "http://5.cn/c";
		Mockito.when(storage.getShortLink(longLink)).thenReturn(shortLink);
		Assert.assertEquals(shortLink, transfer.transfer(longLink));
		Mockito.verify(storage, Mockito.times(0)).save(Mockito.anyString(), Mockito.anyString());
	}
	
	@Test
	public void testNonExist() {
		String longLink = "https://github.com/scdt-china/interview-assignments";
		Mockito.when(storage.getShortLink(longLink)).thenReturn(null);
		transfer.transfer(longLink);
		Mockito.verify(storage, Mockito.times(1)).save(Mockito.anyString(), Mockito.anyString());
	}
	
	@Test(expected = RuntimeException.class)
	public void testLengthLimit() throws Exception {
		String longLink = "https://github.com/scdt-china/interview-assignments";
		SequenceTransfer transfer = new SequenceTransfer();
		Field counterField = SequenceTransfer.class.getDeclaredField("counter");
		counterField.setAccessible(true);
		counterField.set(transfer, Long.MAX_VALUE - 100);
		transfer.transfer(longLink);
	}

}
