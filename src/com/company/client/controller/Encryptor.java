package com.company.client.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Encryptor {
	
	Encryptor() {}
	
	String encryptMD5(String data) {
		MessageDigest md5;
		StringBuilder result = new StringBuilder();
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(data.getBytes());
			for(byte b : bytes) {
				result.append(String.format("%02X", b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	
}