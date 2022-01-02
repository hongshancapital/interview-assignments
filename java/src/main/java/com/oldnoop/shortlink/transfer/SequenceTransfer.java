package com.oldnoop.shortlink.transfer;

import com.oldnoop.shortlink.storage.MemoryLinkStorage;
import com.oldnoop.shortlink.util.Radix62Transfer;

public class SequenceTransfer implements LinkTransfer {

	private long counter = 0;

	private MemoryLinkStorage linkMemoryStore = new MemoryLinkStorage();
	
	private String domain = "5.cn";
	private int length = 8;
	
	private static final String SCHEME_HTTP = "http://";
	private static final String SCHEME_HTTPS = "https://";
	
	@Override
	public synchronized String transfer(String longLink) {
		String scheme = "";
		if(longLink.startsWith(SCHEME_HTTP)) {
			scheme = SCHEME_HTTP;
		}else if(longLink.startsWith(SCHEME_HTTPS)){
			scheme = SCHEME_HTTPS;
		}
		String str = linkMemoryStore.getShortLink(longLink);
		if (str != null) {
			return str;
		}
		String code = Radix62Transfer.to(++counter);
		if(code.length() > length) {
			throw new RuntimeException("短链接超出最大长度" + length + "位限制");
		}
		String shortLink = scheme + domain + "/" + code;
		linkMemoryStore.save(shortLink, longLink);
		return shortLink;
	}

	public String origin(String shortLink) {
		return linkMemoryStore.getLongLink(shortLink);
	}

	public void setLinkMemoryStore(MemoryLinkStorage linkMemoryStore) {
		this.linkMemoryStore = linkMemoryStore;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
}
