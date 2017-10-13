package com.thinkgem.jeesite.modules.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@Transactional(readOnly = true)
public class DownloadBalance {
	
	/**
	 * 标题的样式
	 * @param wb
	 * @return
	 */
	private XSSFCellStyle headerStyle(XSSFWorkbook wb) {
		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb .createCellStyle();// 创建标题样式  
		xssCelltyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);    //设置垂直居中  
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);   //设置水平居中  
		XSSFFont xssFFont = (XSSFFont) wb.createFont(); //创建字体样式  
		xssFFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
		xssFFont.setFontName("Courier New");  //设置字体类型  
		xssFFont.setFontHeightInPoints((short) 14);    //设置字体大小  
		xssCelltyle.setFont(xssFFont);    //为标题样式设置字体样式
		return xssCelltyle;
	}
	/**
	 * 副标题的样式
	 * @param wb
	 * @param xssCelltyle
	 */
	private void subHearderStyle(XSSFWorkbook wb, XSSFCellStyle xssCelltyle) {
		xssCelltyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);    //设置垂直居中  
		XSSFFont xssFFont = (XSSFFont) wb.createFont(); //创建字体样式  
		xssFFont.setFontName("Arial");  //设置字体类型  
		xssFFont.setFontHeightInPoints((short) 10);    //设置字体大小  
		xssCelltyle.setFont(xssFFont);    //为标题样式设置字体样式
	}
	/**
	 *每列的标题
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle columnStyle (XSSFWorkbook wb) {
		/* 
		 * 设定合并单元格区域范围 
		 *  firstRow  0-based 
		 *  lastRow   0-based 
		 *  firstCol  0-based 
		 *  lastCol   0-based 
		 */  
		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb .createCellStyle();// 创建标题样式  
		xssCelltyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);    //设置垂直居中  
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);   //设置水平居中  
		xssCelltyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		xssCelltyle.setFillPattern(xssCelltyle.SOLID_FOREGROUND);
		XSSFFont xssFFont = (XSSFFont) wb.createFont(); //创建字体样式  
		xssFFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
		xssFFont.setFontName("Courier New");  //设置字体类型  
		xssFFont.setFontHeightInPoints((short) 12);    //设置字体大小  
		xssCelltyle.setFont(xssFFont);    //为标题样式设置字体样式
		return xssCelltyle;
	}
	/**
	 * 信息样式	名字
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle infoNameStyle (XSSFWorkbook wb) {
		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb .createCellStyle();// 创建标题样式  
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);   //设置水平居中  
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}
	/**
	 * 信息样式	行次
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle infoNumberStyle (XSSFWorkbook wb) {
		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb .createCellStyle();// 创建标题样式  
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);   //设置水平居中  
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}
	/**
	 * 信息样式	金额
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle infoMoneyStyle (XSSFWorkbook wb) {
		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb .createCellStyle();// 创建标题样式  
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);   //设置水平居中  
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}
	/**
	 * 宽度	现金流量表
	 * @param sheet
	 */
	public void cashFlowAndProfitStyleWidth(XSSFSheet sheet) {
		//下面样式可作为导出左右分栏的表格模板  
		//下面样式可作为导出左右分栏的表格模板  
		sheet.setColumnWidth((short) 0, (short) 8000);// 设置列宽  
		sheet.setColumnWidth((short) 1, (short) 3500);  
		sheet.setColumnWidth((short) 2, (short) 4500);  
		sheet.setColumnWidth((short) 3, (short) 4500);  
	}
	/**
	 * 资产负债表头的样式
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle balanceHeaderStyle (XSSFWorkbook wb, XSSFSheet sheet) {
		/* 
		 * 设定合并单元格区域范围 
		 *  firstRow  0-based 
		 *  lastRow   0-based 
		 *  firstCol  0-based 
		 *  lastCol   0-based 
		 */  
		CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 7);
		sheet.addMergedRegion(cra);
		
		XSSFCellStyle xssCelltyle = headerStyle(wb);
		return xssCelltyle;
	}
	
	
	
	/**
	 * 资产负债表头的样式	副标题
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle balanceSubHeaderStyle2 (XSSFWorkbook wb, XSSFSheet sheet) {
		/* 
		 * 设定合并单元格区域范围 
		 *  firstRow  0-based 
		 *  lastRow   0-based 
		 *  firstCol  0-based 
		 *  lastCol   0-based 
		 */  
		CellRangeAddress cra=new CellRangeAddress(1, 1, 3, 4);
		sheet.addMergedRegion(cra);
		
		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb .createCellStyle();// 创建标题样式  
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);   //设置水平居中  
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}
	
	
	/**
	 * 现金流量表头的样式	副标题
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle cashFlowAnProfitSubHeaderStyle3 (XSSFWorkbook wb, XSSFSheet sheet) {
		/* 
		 * 设定合并单元格区域范围 
		 *  firstRow  0-based 
		 *  lastRow   0-based 
		 *  firstCol  0-based 
		 *  lastCol   0-based 
		 */  
		CellRangeAddress cra=new CellRangeAddress(1, 1, 2, 3);
		sheet.addMergedRegion(cra);
		
		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb .createCellStyle();// 创建标题样式  
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);   //设置右  
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}
		
	/**
	 * 下载操作
	 * @param path
	 * @param response
	 */
	public void download(String path, HttpServletResponse response) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
			deleteFile(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void deleteFile(File file) {   
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	       
	    }
	} 
}
