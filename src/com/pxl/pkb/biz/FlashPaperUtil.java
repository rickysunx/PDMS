package com.pxl.pkb.biz;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.pxl.pkb.framework.Params;
import com.pxl.pkb.framework.PxlInputStream;
import com.pxl.winservice.common.Utils;

public class FlashPaperUtil {
	
	//发送待转换的文件
	protected static void sendFile(String filePath,String fileName,DataOutputStream dout) throws Exception {
		PxlInputStream pin = null;
		try {
			pin = new PxlInputStream(filePath);
			dout.writeLong(pin.getFileSize());
			dout.write(Utils.getBytesByLen(fileName, 255));
			int len = -1;
			byte [] buff = new byte[512];
			while(true) {
				len = pin.read(buff);
				if(len<0) break;
				dout.write(buff,0,len);
			}
			dout.flush();
		} finally {
			try {if(pin!=null) pin.close();} catch (Exception e) {}
		}
	}
	
	//接收转换后的文件
	protected static void recvFile(String outFileName,DataInputStream din) throws Exception {
		
		//读取返回标志
		byte [] flagBytes = new byte[1];
		din.read(flagBytes);
		String flag = new String(flagBytes);
		if(!flag.equals("1")) {
			throw new Exception("转换发生错误");
		}
		
		//不再从服务端下传文件，只读取成功标志
		/*
		//读取文件长度
		long fileSize = din.readLong();
		
		//开始接收文件
		long currSize = 0;
		long readSize = 0;
		int len = -1;
		byte [] buff = new byte[512];
		
		FileOutputStream fout = null;
		
		try {
			fout = new FileOutputStream(outFileName);
			while(true) {
				readSize = fileSize - currSize;
				readSize = Math.min(readSize, buff.length);
				if(readSize==0) break;
				len = din.read(buff, 0, (int)readSize);
				if(len<=0) break;
				fout.write(buff, 0, len);
				currSize+=len;
			}
			fout.flush();
		} finally {
			try {if(fout!=null) fout.close();} catch (Exception e) {}
		}
		*/
	}
	
	public static void convertFileToSwf(String date,int id,String fileExt) throws Exception {
		Params p = Params.getInstance();
		
		Socket s = null;
		InputStream in = null;
		OutputStream out = null;
		
		try {
			//通过Socket将请求发送给VMWare中的Windows 2003系统处理，端口固定为9500
			s = new Socket(p.getFlashPaperServer(),p.getFlashPaperServerPort());
			in = s.getInputStream();
			out = s.getOutputStream();
			DataInputStream din = new DataInputStream(in);
			DataOutputStream dout = new DataOutputStream(out);
			
			//写入请求服务名
			dout.write(Utils.getBytesByLen("FlashPaperConverter", 50));
			
			//文件名需包含扩展名，以便FlashPaper进行转换
			String fileName = ""+id+((fileExt==null||fileExt.trim().length()==0)?"":("."+fileExt));
			
			//输出的SWF文件全路径
			String outFileName = p.getFlashPaperPath()+File.separator+id+".swf";
			
			//加密的附件文件全路径
			String filePath = p.getUploadPath() +
				File.separator + "DOC" +
				File.separator + date +
				File.separator + id;
			
			//通过解密流获取文件信息，将文件写入Socket流，发送给Windows 2003系统处理
			sendFile(filePath, fileName, dout);
			
			//获取转换后的SWF文件
			recvFile(outFileName, din);
		} finally {
			try {if(in!=null) in.close();} catch (Exception e) {e.printStackTrace();}
			try {if(out!=null) out.close();} catch (Exception e) {e.printStackTrace();}
			try {if(s!=null) s.close();} catch (Exception e) {e.printStackTrace();}
		}
		
	}
	
}
