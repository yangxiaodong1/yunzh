package com.thinkgem.jeesite.modules.stamp;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.IntNode;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
@Controller
@Transactional(readOnly = true)
public class StampPdf {
	public void createPdf(HttpServletRequest request,
			HttpServletResponse response, List<PdfPTable> listPdfPTable, String title)
			throws IOException {
		// 设置纸张的
		Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
		try {
			// 允许在PDF中写入中文，将字体文件放在classPath中。
			String fontsPath = request.getSession().getServletContext()
					.getRealPath("Fonts");
			BaseFont bfChinese = BaseFont.createFont(fontsPath + "/SIMYOU.TTF",
					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			Font fontChinese = new Font(bfChinese, 16, Font.BOLD,
					BaseColor.BLACK);

			Paragraph chapterTitle = new Paragraph(title, fontChinese);
			chapterTitle.setAlignment(Element.ALIGN_CENTER);
			// 输出流
			response.setContentType("application/pdf");
			PdfWriter.getInstance(doc, response.getOutputStream());
			doc.open();
			int pageNum = 1;
			for (PdfPTable pdfTableInfo : listPdfPTable) {
				// 添加标题
				doc.add(chapterTitle);
				doc.add(Chunk.NEWLINE);
				doc.add(pdfTableInfo);
				if (pageNum<listPdfPTable.size()) {
					 doc.newPage();
				}
				pageNum++;
			}			
			doc.close();
			// 写文件
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
