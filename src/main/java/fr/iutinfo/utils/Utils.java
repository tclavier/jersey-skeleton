package fr.iutinfo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

	public static String hashMD5(String content) {
		try {
			byte[] bytesOfMessage = content.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] result = md.digest(bytesOfMessage);
			return new String(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
