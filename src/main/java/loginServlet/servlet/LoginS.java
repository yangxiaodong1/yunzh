package loginServlet.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.thinkgem.jeesite.modules.sys.service.SystemService;


import net.sf.json.JSONObject;

import loginServlet.dao.TBillInfoDao;
import loginServlet.dao.TCustomerDao;
import loginServlet.dao.UserDao;
import loginServlet.entity.CustomerInfo;
import loginServlet.entity.SysUser;
import loginServlet.entity.TBillInfo;
import loginServlet.entity.TCustomer;



/**
 * Servlet implementation class LoginS
 */
public class LoginS extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Autowired
	private SystemService  systemService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String username=request.getParameter("username");
		String pwd=request.getParameter("password");
		System.out.println(pwd+"密码");
		UserDao ud=new UserDao();
		String dbPwd = ud.selectGetPwdByName(username);//获取到密码
		boolean flag = systemService.validatePassword(pwd,dbPwd);
		String message=null;
		if (flag) {	//登陆成功
			SysUser su=ud.findByNamePwd(username, dbPwd);//获取用户名密码所以对于的客户
			//String message=null;
			if(su!=null){
				TCustomerDao tcd=new TCustomerDao();
				List<TCustomer> tcList=tcd.findById(su.getId());//根据用户的id找到所对应的所有客户
				//根据id和票据状态找到对应的票据
				List<TBillInfo> tbList=null;
				for (TCustomer t : tcList) {
					TBillInfoDao tbid=new TBillInfoDao();
				 tbList=tbid.findByComIdStatus(t.getId(), "1");
					for (TBillInfo tb : tbList) {
						tb.setCompanyName(t.getName());
					}
				}
				/*CustomerInfo ci=new CustomerInfo();
				ci.setUserName(su.getUserName());
				ci.setTelephone(su.getPhone());
				ci.setUserPicUrl(su.getPhotoUrl());
				ci.setTCustomer(tcList);
				ci.setSiList(tbList);*/
				
			//JSONArray jso=JSONArray.fromObject(ci);
				JSONObject jo=new JSONObject();
			 //jo.put("ci", jso);
				jo.put("telephone", su.getPhone());
				jo.put("userName", su.getUserName());//后来加的当前登陆用户名
				jo.put("userPicUrl", su.getPhotoUrl());
				jo.put("email", su.getEmail());
				jo.put("comInfoList", tcList);
				//jo.put("siList",tbList);//去掉票据信息
				message=jo.toString();
				
		     // JsonMapper jm=new JsonMapper();
			 //message=jm.toJson(ci);
				//System.out.println(message);
			}
					
		}else{
			message="0";
		}
		
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
