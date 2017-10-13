package loginServlet.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import loginServlet.dao.TBillFeeTypeDao;
import loginServlet.entity.TBillFeeType;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class getBillFeeTypes
 */
public class getBillFeeTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public getBillFeeTypes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取费用类型接口  gyg add 2016-02-16
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("获取费用类型接口");
		String message=null;

		TBillFeeTypeDao tcd=new TBillFeeTypeDao();
		List<TBillFeeType> tbftList = tcd.getBillFeeTypes();
		JSONObject jo=new JSONObject();
		jo.put("billfeetypes", tbftList);
		message=jo.toString();
		System.out.println(message);
		response.getWriter().print(message);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
