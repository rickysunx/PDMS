package com.pxl.pkb.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import com.pxl.pkb.framework.PxlInputStream;

public class TextExtracter {

	public static String getTextFromFile(String filePath, String fileType)
			throws Exception {
		String text = "";
		
		try {
			InputStream in = new PxlInputStream(filePath);
//			if (fileType.equalsIgnoreCase("doc")) {
//				WordExtractor wex = new WordExtractor(in);
//				text = wex.getText();
//			}
//			if (fileType.equalsIgnoreCase("xls")) {
//				HSSFWorkbook wb = new HSSFWorkbook(in);
//				ExcelExtractor eex = new ExcelExtractor(wb);
//				text = eex.getText();
//			}
//			if (fileType.equalsIgnoreCase("ppt")) {
//				PowerPointExtractor pptex = new PowerPointExtractor(in);
//				text = pptex.getText();
//			}
			if(in==null){
				throw new Exception("系统异常");
			}
			if (fileType.equalsIgnoreCase("txt")) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				String line = null;
				while ((line = reader.readLine()) != null) {
					text += line + "\r\n";
				}
			}
			if (fileType.equalsIgnoreCase("pdf")) {
				// 内存中的PDF Document
				PDDocument document = null;
				try {
					// 从一个流中装载
					document = PDDocument.load(in);
					// PDFTextStripper来提取文本
					PDFTextStripper stripper = null;
					stripper = new PDFTextStripper();
					// 设置是否排序
					stripper.setSortByPosition(false);
					// 设置起始页
					stripper.setStartPage(1);
					// 设置结束页
					stripper.setEndPage(Integer.MAX_VALUE);
					// 调用PDFTextStripper的writeText提取并输出文本
					text = stripper.getText(document);
					System.out.println(text);
				} catch (IOException e) {
					e.printStackTrace();
					if (e.getMessage() != null
							&& e.getMessage().equals(
									"You do not have permission to extract text"))
						text = "加密PDF，无法解析";
				} finally {
					if (document != null) {
						// 关闭PDF Document
						document.close();
					}
				}
			}
			if (fileType.equalsIgnoreCase("docx")||fileType.equalsIgnoreCase("pptx")||
					fileType.equalsIgnoreCase("xlsx")||fileType.equalsIgnoreCase("ppt")||
					fileType.equalsIgnoreCase("doc")||fileType.equalsIgnoreCase("xls")) {
				try {
					POITextExtractor extractor = ExtractorFactory.createExtractor(in);
					text=extractor.getText();
				} catch (Exception ex) {
					text="";
					ex.printStackTrace();
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(text!=null&&text.length()>500*1024){
			text=text.substring(0,500*1024);
		}
		return text;
	}
//	
//	public static void main(String[] args) {
//		try {
//			File file=new File("E:\\");
//			//POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(file));
//			POITextExtractor extractor = ExtractorFactory.createExtractor(new FileInputStream(file));
//			String text=extractor.getText();
//			System.out.println(text);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
}
