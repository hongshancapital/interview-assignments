package com.oldnoop.shortlink.storage;

import org.junit.Assert;
import org.junit.Test;

public class MemoryLinkStorageTest {

	@Test
	public void test() throws Exception {
		{
			MemoryLinkStorage storage = new MemoryLinkStorage(300);
			storage.setCheckInterval(1);
			storage.init();

			String longLink = "https://github.com/scdt-china/interview-assignments";
			String shortLink = "https://5.cn/c";

			int count = 0;
			for (int i = 0; i < 100; i++) {
				try {
					storage.save(shortLink + (i + 1), longLink + (i + 1));
					Thread.sleep(1000);
				} catch (Exception e) {
					count = i;
					break;
				}
			}
			Assert.assertTrue(count > 0);
			for (int i = 0; i < count; i++) {
				Assert.assertEquals(longLink + (i + 1), storage.getLongLink(shortLink + (i + 1)));
			}
			Assert.assertNull(storage.getLongLink(shortLink + (count + 1)));
			for (int i = 0; i < count; i++) {
				storage.remove(shortLink + (i + 1));
			}
			Thread.sleep(1100);
			storage.save(shortLink + (count + 1), longLink + (count + 1));
			Assert.assertEquals(longLink + (count + 1), storage.getLongLink(shortLink + (count + 1)));
		}
	}

}
