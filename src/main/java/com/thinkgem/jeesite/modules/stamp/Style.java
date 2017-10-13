package com.thinkgem.jeesite.modules.stamp;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;

@Controller
@Transactional(readOnly = true)
public class Style {
	public static void headerCellStyle(PdfPCell cell) {

		// alignment
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		// padding
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);

		// background color
		cell.setBackgroundColor(new BaseColor(0, 121, 182));

		// border
		cell.setBorder(0);
		cell.setBorderWidthBottom(2f);

	}

	public static void labelCellStyle(PdfPCell cell) {
		// alignment
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		// padding
		cell.setPaddingLeft(3f);
		cell.setPaddingTop(0f);

		// background color
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

		// border
		cell.setBorder(0);
		cell.setBorderWidthBottom(1);
		cell.setBorderColorBottom(BaseColor.GRAY);

		// height
		cell.setMinimumHeight(18f);
	}

	public static void valueCellStyle(String position, PdfPCell cell,
			int rowspan, int colspan) {
		// alignment
		if (position.equals("left")) {
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		}
		if (position.equals("center")) {
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		if (position.equals("right")) {
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		}
		if (rowspan != 0) {
			cell.setRowspan(rowspan);
		}
		if (colspan != 0) {
			cell.setColspan(colspan);
		}
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		// padding
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(5f);

		// border
		cell.setBorder(0);
		cell.setBorderWidthLeft(0.5f);
		cell.setBorderWidthRight(0.5f);
		cell.setBorderWidthTop(0.5f);
		cell.setBorderWidthBottom(0.5f);

		// height
		cell.setMinimumHeight(18f);
	}

	public static void valueCellStyleSub(String position, PdfPCell cell) {
		if (position.equals("left")) {
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		}
		if (position.equals("center")) {
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		if (position.equals("right")) {
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		}
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		// padding
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(10f);
		/*
		 * // background color cell.setBackgroundColor(new BaseColor(0, 121,
		 * 182));
		 */
		// border
		cell.setBorder(0);
		cell.setBorderWidthBottom(0);

		// height
		cell.setMinimumHeight(30f);
	}
}
