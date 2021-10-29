package com.scdt.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	
	public static String md5(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] digest = md.digest();
			return toHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toHexString(byte[] bytes) {
		StringBuilder buf = new StringBuilder(50);
		int t;
		for (int i = 0; i < bytes.length; i++) {
			t = bytes[i];
			if (t < 0) {
				t += 256;
			}
			if (t < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(t));
		}
		return buf.toString();
	}
}
