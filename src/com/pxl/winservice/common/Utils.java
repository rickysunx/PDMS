package com.pxl.winservice.common;

import java.io.InputStream;

public class Utils {
	
	public static byte[] getBytesByLen(String str,int len) throws Exception {
		byte [] bytes = new byte[len];
		byte [] strBytes = (str==null)?(new byte[0]):(str.getBytes("GBK"));
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = 20;
		}
		for (int i = 0; i < Math.min(bytes.length, strBytes.length); i++) {
			bytes[i] = strBytes[i];
		}
		return bytes;
	}
	
	public static String [] getFileNameAndExt(String fileName) throws Exception {
		String [] fileNameAndExt = new String[2];
		int dotindex = fileName.lastIndexOf(".");
		if(dotindex==-1) {
			fileNameAndExt[0] = fileName;
			fileNameAndExt[1] = "";
		} else {
			fileNameAndExt[0] = fileName.substring(0, dotindex);
			fileNameAndExt[1] = fileName.substring(dotindex+1);
		}
		return fileNameAndExt;
	}
	
	public static String readStringByLen(InputStream in,int len) throws Exception {
		byte [] buff = new byte[len];
		int size = in.read(buff);
		if(size!=len) throw new Exception("¶ÁÈ¡×Ö·û´®³ö´í");
		String str = new String(buff,"GBK");
		return str.trim();
	}
	
	
}
