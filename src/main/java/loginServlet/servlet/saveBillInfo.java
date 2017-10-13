package loginServlet.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

//import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfo;
import com.thinkgem.jeesite.modules.billinfo.service.TBillInfoService;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;

import loginServlet.dao.TBillInfoDao;
import loginServlet.entity.TBillInfo;
//import loginServlet.entity.TBillInfo;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class saveBillInfo
 */
public class saveBillInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private TBillInfoService tBillInfoService;
	@Autowired
	private TCustomerService tCustomerService;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public saveBillInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//this.doGet(request, response);
		String result = "";			//post传递过来的json字符串，也就是需要保持的bill票据信息
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));

		StringBuffer sb = new StringBuffer("");
		String temp;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		//result="{\"upload_period\":\"201601\",\"image_url\":\"12BBBBBBBB2016010001.jpg\",\"abstract_msg\":\"市内交通\",\"total_price\":\"300\",\"customer_id\":\"12\",\"remarks\":\"哈哈\"}";
		result = sb.toString();
		//result="{\"upload_period\":\"201605\"}";
		//result = "{\"userid\":\"dcsm\",\"billno\":\"A15041700395\",\"operid\":\"dcsm\",\"reason\":\"03\",\"rem1\":\"测试\"}";			//示例json串
		//System.out.println("请求json串内容:" + result);

		JSONObject json = JSONObject.fromObject(result);
		TBillInfo tbifo = (TBillInfo) JSONObject.toBean(json,
				TBillInfo.class);
		//System.out.println(tbifo.getUploadPeriod()+"hahhaha");
	    String resultMsg="false";		
		try{
			
				System.out.println("你好");
				//requestreasonService.add(rn);
				//System.out.println("ok");
				TBillInfoDao tbdao=new TBillInfoDao();
				String id=tbdao.find();
				//tbifo.setId(id);
				tbdao.saveBillInfo(tbifo);
				//tBillInfoService.save(tbifo);
				//requestmasterService.update(requestmaste);  //
				//resultMsg=getText("更新","request_master");
				resultMsg="true";
		
		}catch(Exception e){
			
		}
		
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("save_flag", String.valueOf(resultMsg));
		System.out.println(resultMsg);

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String rtn = JSONObject.fromObject(hm).toString();
		// out.print(URLEncoder.encode(rtn, "UTF-8"));
		out.print(rtn);
		//response.getWriter().print(rtn);
	}

}
