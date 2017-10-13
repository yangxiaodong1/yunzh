package com.thinkgem.jeesite.modules.stamp;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Phrase;

@Controller
@Transactional(readOnly = true)
public class TableBuilder {
	/**
	 * 
	 * @param cellNumber多少列
	 * @param pageWidth页面的宽度
	 * @param cellWidth每列的宽度
	 * @return
	 * @throws DocumentException
	 */
	public PdfPTable createPdf(String fontSPath, int cellNumber, int pageWidth,
			float[] cellWidth) throws DocumentException {
		// create 6 column table
		PdfPTable table = new PdfPTable(cellNumber);

		// set the width of the table to 100% of page
		table.setWidthPercentage(pageWidth);

		// set relative columns width
		table.setWidths(cellWidth);
		// ----------------Table Header "Title"----------------
		return table;
	}

	String left = "left";
	String center = "center";
	String right = "right";

	/**
	 * 副标题
	 * 
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public PdfPTable createSub(String fontSPath, String subT1, String subT2,
			String subT3) throws DocumentException, IOException {
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(90);
		table.addCell(createValueSub(fontSPath, left, subT1));
		table.addCell(createValueSub(fontSPath, center, subT2));
		table.addCell(createValueSub(fontSPath, right, subT3));
		return table;

	}
	public PdfPTable createSub(String fontSPath, String subT1, 
			String subT3) throws DocumentException, IOException {
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(90);
		table.addCell(createValueSub(fontSPath, left, subT1));
		table.addCell(createValueSub(fontSPath, right, subT3));
		return table;

	}

	/**
	 * 底部信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public PdfPTable createRemarks(String fontSPath, String remarks,
			String stampTime) throws DocumentException, IOException {
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(90);
		table.addCell(createValueSub(fontSPath, left, remarks));
		table.addCell(createValueSub(fontSPath, right, stampTime));
		return table;
	}
	
	/**
	 * 底部信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public PdfPTable createRemarks2(String fontSPath, 
			String stampTime) throws DocumentException, IOException {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		//table.addCell(createValueSub(fontSPath, left, remarks));
		table.addCell(createValueSub(fontSPath, right, stampTime));
		return table;
	}
	/**
	 * 每个的样式
	 * 
	 * @param text
	 * @return
	 */
	public PdfPCell createValueCell(int n, String position, String fontSPath,
			String text, int rowspan, int colspan) {
		BaseFont bfChinese = null;
		try {
			bfChinese = BaseFont.createFont(fontSPath + "/SIMYOU.TTF",
					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// font
		Font font = null;
		if (n == 1) {
			font = new Font(bfChinese, 10, Font.BOLD, BaseColor.BLACK);
		} else {
			font = new Font(bfChinese, 10, Font.NORMAL, BaseColor.BLACK);
		}

		// create cell
		PdfPCell cell = new PdfPCell(new Phrase(text, font));

		// set style
		Style.valueCellStyle(position, cell, rowspan, colspan);
		return cell;
	}

	public PdfPCell createValueSub(String fontSPath, String position,
			String text) {
		PdfPCell cell = subFontStyle(fontSPath, text);

		// set style
		Style.valueCellStyleSub(position, cell);
		return cell;
	}

	/**
	 * 副标题信息的字体设置
	 * 
	 * @param text
	 * @return
	 */
	private PdfPCell subFontStyle(String fontSPath, String text) {
		BaseFont bfChinese = null;
		try {
			bfChinese = BaseFont.createFont(fontSPath + "/SIMYOU.TTF",
					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Font fontChinese = new Font(bfChinese, 12, Font.NORMAL, BaseColor.BLACK);
		// create cell
		PdfPCell cell = new PdfPCell(new Phrase(text, fontChinese));
		return cell;
	}
}
