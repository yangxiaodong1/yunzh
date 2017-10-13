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
public class DownloadController {

	/**
	 * 标题的样式
	 * 
	 * @param wb
	 * @return
	 */
	private XSSFCellStyle headerStyle(XSSFWorkbook wb) {
		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb.createCellStyle();// 创建标题样式
		xssCelltyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 设置垂直居中
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 设置水平居中
		XSSFFont xssFFont = (XSSFFont) wb.createFont(); // 创建字体样式
		xssFFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
		xssFFont.setFontName("Courier New"); // 设置字体类型
		xssFFont.setFontHeightInPoints((short) 14); // 设置字体大小
		xssCelltyle.setFont(xssFFont); // 为标题样式设置字体样式
		return xssCelltyle;
	}

	/**
	 * 副标题的样式
	 * 
	 * @param wb
	 * @param xssCelltyle
	 */
	private void subHearderStyle(XSSFWorkbook wb, XSSFCellStyle xssCelltyle) {
		xssCelltyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 设置垂直居中
		XSSFFont xssFFont = (XSSFFont) wb.createFont(); // 创建字体样式
		xssFFont.setFontName("Arial"); // 设置字体类型
		xssFFont.setFontHeightInPoints((short) 10); // 设置字体大小
		xssCelltyle.setFont(xssFFont); // 为标题样式设置字体样式
	}

	/**
	 * 每列的标题
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle columnStyle(XSSFWorkbook wb) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb.createCellStyle();// 创建标题样式
		xssCelltyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 设置垂直居中
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 设置水平居中
		xssCelltyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
				.getIndex());
		xssCelltyle.setFillPattern(xssCelltyle.SOLID_FOREGROUND);

		xssCelltyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		xssCelltyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
		xssCelltyle.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框
		xssCelltyle.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框

		XSSFFont xssFFont = (XSSFFont) wb.createFont(); // 创建字体样式
		xssFFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
		xssFFont.setFontName("Courier New"); // 设置字体类型
		xssFFont.setFontHeightInPoints((short) 12); // 设置字体大小
		xssCelltyle.setFont(xssFFont); // 为标题样式设置字体样式
		return xssCelltyle;
	}

	/**
	 * 信息样式 名字
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle infoStyle(XSSFWorkbook wb,String position) {
		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb.createCellStyle();// 创建标题样式
		if(position.equals("left")){
			xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 设置水平居中
		}
		if(position.equals("center")){
			xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 设置水平居中
		}
		if(position.equals("right")){
			xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 设置水平居中
		}
		
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}

	/**
	 * 资产负债表头的样式
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle balanceHeaderStyle(XSSFWorkbook wb, XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 7);
		sheet.addMergedRegion(cra);

		XSSFCellStyle xssCelltyle = headerStyle(wb);
		return xssCelltyle;
	}

	/**
	 * 现金流量表头的样式
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle cashFlowHeaderStyle(XSSFWorkbook wb, XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 3);
		sheet.addMergedRegion(cra);

		XSSFCellStyle xssCelltyle = headerStyle(wb);
		return xssCelltyle;
	}

	/**
	 * 资产负债表头的样式 副标题
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle balanceSubHeaderStyle1(XSSFWorkbook wb, XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(1, 1, 0, 2);
		sheet.addMergedRegion(cra);

		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb.createCellStyle();// 创建标题样式
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 设置zuo
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}

	/**
	 * 现金流量表头的样式 副标题
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle cashFlowAndProfitSubHeaderStyle1(XSSFWorkbook wb,
			XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		/*
		 * CellRangeAddress cra=new CellRangeAddress(1, 1, 0, 2);
		 * sheet.addMergedRegion(cra);
		 */

		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb.createCellStyle();// 创建标题样式
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 设置zuo
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}

	/**
	 * 资产负债表头的样式 副标题
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle balanceSubHeaderStyle2(XSSFWorkbook wb, XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(1, 1, 3, 4);
		sheet.addMergedRegion(cra);

		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb.createCellStyle();// 创建标题样式
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 设置水平居中
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}

	/**
	 * 现金流量表头的样式 副标题
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle cashFlowAndProfitSubHeaderStyle2(XSSFWorkbook wb,
			XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		/*
		 * CellRangeAddress cra=new CellRangeAddress(1, 1, 3, 4);
		 * sheet.addMergedRegion(cra);
		 */

		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb.createCellStyle();// 创建标题样式
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 设置水平居中
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}

	/**
	 * 资产负债表头的样式 副标题
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle balanceSubHeaderStyle3(XSSFWorkbook wb, XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(1, 1, 5, 7);
		sheet.addMergedRegion(cra);

		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb.createCellStyle();// 创建标题样式
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 设置右
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}

	/**
	 * 现金流量表头的样式 副标题
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle cashFlowAnProfitSubHeaderStyle3(XSSFWorkbook wb,
			XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(1, 1, 2, 3);
		sheet.addMergedRegion(cra);

		XSSFCellStyle xssCelltyle = (XSSFCellStyle) wb.createCellStyle();// 创建标题样式
		xssCelltyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 设置右
		subHearderStyle(wb, xssCelltyle);
		return xssCelltyle;
	}

	/**
	 * 每列的标题 科目余额表 科目编码
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle columnSubjectBalanceStyle1(XSSFWorkbook wb,
			XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(0, 1, 0, 0);
		sheet.addMergedRegion(cra);
		return columnStyle(wb);
	}

	/**
	 * 每列的标题 科目余额表 科目名称
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle columnSubjectBalanceStyle2(XSSFWorkbook wb,
			XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(0, 1, 1, 1);
		sheet.addMergedRegion(cra);
		return columnStyle(wb);
	}

	/**
	 * 每列的标题 科目余额表 期初余额
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle columnSubjectBalanceStyle3(XSSFWorkbook wb,
			XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(0, 0, 2, 3);
		sheet.addMergedRegion(cra);
		return columnStyle(wb);
	}

	/**
	 * 每列的标题 科目余额表 本期发生额
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle columnSubjectBalanceStyle4(XSSFWorkbook wb,
			XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(0, 0, 4, 5);
		sheet.addMergedRegion(cra);
		return columnStyle(wb);
	}

	/**
	 * 每列的标题 科目余额表 本年累计金额
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle columnSubjectBalanceStyle5(XSSFWorkbook wb,
			XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(0, 0, 6, 7);
		sheet.addMergedRegion(cra);
		return columnStyle(wb);
	}

	/**
	 * 每列的标题 科目余额表 期末余额
	 * 
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle columnSubjectBalanceStyle6(XSSFWorkbook wb,
			XSSFSheet sheet) {
		/*
		 * 设定合并单元格区域范围 firstRow 0-based lastRow 0-based firstCol 0-based lastCol
		 * 0-based
		 */
		CellRangeAddress cra = new CellRangeAddress(0, 0, 8, 9);
		sheet.addMergedRegion(cra);
		return columnStyle(wb);
	}

	/**
	 * 宽度 资产负债表
	 * 
	 * @param sheet
	 */
	public void balanceStyleWidth(XSSFSheet sheet) {
		// 下面样式可作为导出左右分栏的表格模板
		// 下面样式可作为导出左右分栏的表格模板
		sheet.setColumnWidth((short) 0, (short) 5000);// 设置列宽
		sheet.setColumnWidth((short) 1, (short) 1800);
		sheet.setColumnWidth((short) 2, (short) 3600);
		sheet.setColumnWidth((short) 3, (short) 3600);
		sheet.setColumnWidth((short) 4, (short) 5000);
		sheet.setColumnWidth((short) 5, (short) 1800);// 空列设置小一些
		sheet.setColumnWidth((short) 6, (short) 3600);// 设置列宽
		sheet.setColumnWidth((short) 7, (short) 3600);
	}

	/**
	 * 宽度 现金流量表
	 * 
	 * @param sheet
	 */
	public void cashFlowAndProfitStyleWidth(XSSFSheet sheet) {
		// 下面样式可作为导出左右分栏的表格模板
		// 下面样式可作为导出左右分栏的表格模板
		sheet.setColumnWidth((short) 0, (short) 8000);// 设置列宽
		sheet.setColumnWidth((short) 1, (short) 3500);
		sheet.setColumnWidth((short) 2, (short) 4500);
		sheet.setColumnWidth((short) 3, (short) 4500);
	}
	
	/**
	 * 宽度 现金流量表
	 * 
	 * @param sheet
	 */
	public void subsidiaryStyleWidth(XSSFSheet sheet) {
		// 下面样式可作为导出左右分栏的表格模板
		// 下面样式可作为导出左右分栏的表格模板
		sheet.setColumnWidth((short) 0, (short) 3500);// 设置列宽
		sheet.setColumnWidth((short) 1, (short) 3000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 4500);
		sheet.setColumnWidth((short) 4, (short) 4500);
		sheet.setColumnWidth((short) 5, (short) 2000);
		sheet.setColumnWidth((short) 6, (short) 4500);
	}

	/**
	 * 宽度 科目余额表
	 * 
	 * @param sheet
	 */
	public void subjectBalanceStyleWidth(XSSFSheet sheet) {
		// 下面样式可作为导出左右分栏的表格模板
		// 下面样式可作为导出左右分栏的表格模板
		// 下面样式可作为导出左右分栏的表格模板
		sheet.setColumnWidth((short) 0, (short) 5000);// 设置列宽
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 4000);
		sheet.setColumnWidth((short) 3, (short) 4000);
		sheet.setColumnWidth((short) 4, (short) 4000);
		sheet.setColumnWidth((short) 5, (short) 4000);// 空列设置小一些
		sheet.setColumnWidth((short) 6, (short) 4000);// 设置列宽
		sheet.setColumnWidth((short) 7, (short) 4000);
		sheet.setColumnWidth((short) 8, (short) 4000);// 空列设置小一些
		sheet.setColumnWidth((short) 9, (short) 4000);// 空列设置小一些
	}

	/**
	 * 宽度	总账表
	 * 
	 * @param sheet
	 */
	public void generalLedgerStyleWidth(XSSFSheet sheet) {
		// 下面样式可作为导出左右分栏的表格模板
		sheet.setColumnWidth((short) 0, (short) 5000);// 设置列宽
		sheet.setColumnWidth((short) 1, (short) 8000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5000);
		sheet.setColumnWidth((short) 4, (short) 2500);
		sheet.setColumnWidth((short) 5, (short) 5000);// 空列设置小一些
	}

	/**
	 * 下载操作
	 * 
	 * @param path
	 * @param response
	 */
	private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*"; 
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
					+toUtf8String( new String(filename.getBytes())));
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
	public static String toUtf8String(String s){
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<s.length();i++){
		char c = s.charAt(i);
		if (c >= 0 && c <= 255){sb.append(c);}
		else{
		byte[] b;
		try { b = Character.toString(c).getBytes("utf-8");}
		catch (Exception ex) {
		System.out.println(ex);
		b = new byte[0];
		}
		for (int j = 0; j < b.length; j++) {
		int k = b[j];
		if (k < 0) k += 256;
		sb.append("%" + Integer.toHexString(k).toUpperCase());
		}
		}
		}
		return sb.toString();
		}
	public void deleteFile(File file) {   
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	       
	    }
	} 
}
