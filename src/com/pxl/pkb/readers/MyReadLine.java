package com.pxl.pkb.readers;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyReadLine {
	
	protected InputStream in = null;
	public MyReadLine(InputStream in) {
		this.in = new BufferedInputStream(in);
	}
	
	public String readLine(String charset) throws IOException {
		return new String(readLineBytes(),charset);
	}
	
	public byte [] readLineBytes() throws IOException {
		byte [] buff = new byte[520];
		int len=0;
		while(true) {
			if(len>=512) break;
			int c1 = in.read();
			if(c1==-1) return null;
			if(c1=='\r') {
				int c2 = in.read();
				if(c2=='\n') {
					buff[len++]=(byte)c1;
					buff[len++]=(byte)c2;
					break;
				} else {
					buff[len++]=(byte)c1;
					buff[len++]=(byte)c2;
				}
			} else {
				buff[len++]=(byte)c1;
			}
		}
		byte [] returnBytes = new byte[len];
		System.arraycopy(buff, 0, returnBytes, 0, len);
		return returnBytes;
	}
	
	
	
}
