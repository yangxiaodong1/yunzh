package com.thinkgem.jeesite.modules.voucher.web;

import javax.servlet.http.HttpServletRequest;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @author Administrator
 *
 */
public class pdfShow {
	
		public static PdfPTable createzhong(HttpServletRequest request) throws Exception{
			
			PdfPTable tableSub = new PdfPTable(1);
			PdfPCell cell=new PdfPCell();
	 		tableSub.setWidthPercentage(90);
	 		cell.setMinimumHeight(75);
			cell.setBorder(0);
			cell.setBorderWidthBottom(0);
			tableSub.addCell(cell);
			
			return tableSub;
		}
	
	public static PdfPTable createsub(HttpServletRequest request) throws Exception{
		
		// 设置打印字体的格式设置
		String fontsPath = request.getSession().getServletContext().getRealPath("Fonts");
		BaseFont bfChinese=BaseFont.createFont(fontsPath + "/SIMYOU.TTF",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		Font fontChinese = new Font(bfChinese, 10, Font.NORMAL);
		
		PdfPTable tableSub = new PdfPTable(1);
 		tableSub.setWidthPercentage(90);
 		
 		PdfPCell cell=new PdfPCell(new Phrase("核算单位：科技公司",fontChinese)); 
 		//垂直居中
 		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(0);
		cell.setBorderWidthBottom(0);
		cell.setMinimumHeight(20);
		tableSub.addCell(cell);
		
		return tableSub;
	}
	
	
	public static PdfPCell createPdfCell(String exp,Font font){
		PdfPCell cell=new PdfPCell(new Phrase(exp,font)); 
		cell.setMinimumHeight(30);
		return cell;
		
	}
	public static PdfPCell createNullPdfCell(){
		PdfPCell cell=new PdfPCell(new Phrase("")); 
		cell.setMinimumHeight(30);
		return cell;
	}
	public static PdfPCell create12(String exp,Font font){
		PdfPCell cell=new PdfPCell(new Phrase(exp,font)); 
		cell.setMinimumHeight(30);
		return cell;
	}
	public static PdfPCell createThree(String exp,Font font){
		PdfPCell cell=new PdfPCell(new Phrase(exp,font)); 
		cell.setMinimumHeight(30);
		return cell;
	}
	public static PdfPCell createNullPdfCellthree(int cellHeight){
		PdfPCell cell=new PdfPCell(new Phrase("")); 
		cell.setMinimumHeight(cellHeight);
		return cell;
	}
	
	public static PdfPTable createzhongNew(HttpServletRequest request,int tableWidth,int cellHeight) throws Exception{
		
		PdfPTable table = new PdfPTable(1);
		PdfPCell cell=new PdfPCell();
 		table.setWidthPercentage(tableWidth);
 		cell.setMinimumHeight(cellHeight);
		cell.setBorder(0);
		cell.setBorderWidthBottom(0);
		table.addCell(cell);
		
		return table;
	}
	/**
	 * 记账凭证的头部信息
	 * @param request
	 * @param fontChinese	字体的格式
	 * @param tableWidth	表格的宽度
	 * @param cellHeight	单元格的宽度
	 * @return				头部信息的表格
	 * @param company		公司名称
	 * @throws Exception
	 */
	public static PdfPTable createTop(HttpServletRequest request,Font fontChinese,int tableWidth,int cellHeight,String company) throws Exception{
		// 设置打印字体的格式设置
		PdfPTable table = createPdfTable(1,tableWidth);
		//PdfPCell cell=new PdfPCell(new Phrase("核算单位："+company,fontChinese)); 
		PdfPCell cell=createcell("核算单位："+company,fontChinese,cellHeight,"left",true);
		table.addCell(cell);
		return table;
	}
	
	/**
	 * @param request
	 * @param fontChinese	字体格式	
	 * @param row			几列
	 * @param tableWidth	表格的宽度
	 * @param cellWidth		单元格每列的宽度
	 * @param cellHeight	单元的高度
	 * @param company		公司的名称
	 * @return
	 * @throws Exception
	 */
	public static PdfPTable createInfoTable(int row,int tableWidth,float[] cellWidth) throws Exception{
		// 设置打印字体的格式设置
		PdfPTable table = createPdfTable(row,tableWidth);
		table.setTotalWidth(tableWidth);
		table.setTotalWidth(cellWidth);
		return table;
	}
	/**
	 * 记账凭证的底部信息
	 * @param request
	 * @param fontChinese	字体格式
	 * @param tableWidth	表格宽度
	 * @param cellHeight	单元格高度
	 * @param people		记账人
	 * @return
	 * @throws Exception
	 */
	public static PdfPTable creatbottom(HttpServletRequest request,Font fontChinese,int tableWidth,int cellHeight,String people) throws Exception{
		
		PdfPTable table = createPdfTable(1,tableWidth);
 		//PdfPCell cell=new PdfPCell(new Phrase("制单人："+people,fontChinese)); 
 		PdfPCell cell=createcell("制单人："+people,fontChinese,cellHeight,"left",true);
		table.addCell(cell);
		return table;
	}
	
	/**
	 * @param row			创建几行
	 * @param tableWidth	表格的宽度
	 * @return
	 */
	public static PdfPTable createPdfTable(int row,int tableWidth){
		PdfPTable table = new PdfPTable(row);
 		table.setWidthPercentage(tableWidth);
 		return table;
	}
	
	/**
	 * @param people	表格内容
	 * @param fontChinese	字体格式
	 * @param cellHeight	表格高度
	 * @param HorizontalAlignment	水平对齐方式
	 * @return
	 */
	public static PdfPCell createcell(String context,Font fontChinese,int cellHeight,String HorizontalAlignment,boolean bool){
		PdfPCell cell=new PdfPCell(new Phrase(context,fontChinese)); 
 		//垂直居中
 		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//水平对准方式
		if("left".equals(HorizontalAlignment))
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		else if("right".equals(HorizontalAlignment))
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		else if("center".equals(HorizontalAlignment))
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		//表格线
		if(bool){
		cell.setBorder(0);
		cell.setBorderWidthBottom(0);
		}
		cell.setMinimumHeight(cellHeight);
		return cell;
	}
	/**
	 * @param request	获取本地字体文件
	 * @param fontsize 	字体的大小
	 */
	public static Font fontStyle(HttpServletRequest request,int fontsize) throws Exception{
		String fontsPath = request.getSession().getServletContext().getRealPath("Fonts");
		BaseFont bfChinese=BaseFont.createFont(fontsPath + "/SIMYOU.TTF",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		Font fontChinese = new Font(bfChinese, fontsize, Font.NORMAL);
		return fontChinese;
	}
	
}
