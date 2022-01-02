
package com.oldnoop.shortlink.config;

import com.oldnoop.shortlink.storage.MemoryLinkStorage;
import com.oldnoop.shortlink.transfer.SequenceTransfer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
	
	@Bean
	public MemoryLinkStorage linkStorage() {
		MemoryLinkStorage storage = new MemoryLinkStorage();
		storage.setSize(1 << 25);
		storage.setCheckInterval(10);
		return storage;
	}

	@Bean
	public SequenceTransfer sequenceTransfer(MemoryLinkStorage linkStorage) {
		SequenceTransfer transfer = new SequenceTransfer();
		transfer.setLinkMemoryStore(linkStorage);
		transfer.setDomain("5.cn");
		transfer.setLength(8);
		return transfer;
	}
}

