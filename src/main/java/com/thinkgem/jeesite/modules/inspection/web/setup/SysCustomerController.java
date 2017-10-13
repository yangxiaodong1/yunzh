/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.web.setup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.entity.TAppendix;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TAppendixService;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TCompanyYmc;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TServiceCharge;
import com.thinkgem.jeesite.modules.inspection.service.workstatistics.TCompanyYmcService;
import com.thinkgem.jeesite.modules.newcharge.service.TChargecompanyService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 
 * @author xuxialong
 * @version 2015-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/inspection/setup/sysCustomer")
public class SysCustomerController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private TCompanyYmcService tCompanyYmcService;

	@Autowired
	private TAppendixService tAppendixService;
	@Autowired
	private TCustomerService tCustomerService;
	@Autowired
	private TChargecompanyService tChargecompanyService;
	
	@ModelAttribute
	public TCustomer get(@RequestParam(required=false) String id) {
		TCustomer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCustomerService.get(id);
		}
		if (entity == null){
			entity = new TCustomer();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TCustomer tCustomer, HttpServletRequest request, HttpServletResponse response,Model model) {
		
		User user1 = new User();
		Office company=UserUtils.getUser().getCompany();
	 	user1.setCompany(company);
	 	List<User> userList1 = systemService.selectuserzNodes(user1);
	 	List<User> userList = systemService.selectuser(user1);
	 	Office office=new Office();
		office.setpId(UserUtils.getUser().getCompany().getId());
		
		List<Office> sysOfficeList=officeService.findByParentIdszNodes(office);
		model.addAttribute("company",company);
	 	model.addAttribute("sysOfficeList",sysOfficeList);
	 	model.addAttribute("tServeInfoinsert",new TServiceCharge());
	 	model.addAttribute("tServiceCharge",new TServiceCharge());
		model.addAttribute("tCustomer", tCustomer);
		model.addAttribute("userList",userList);
		model.addAttribute("userList1",userList1);
		return "modules/inspection/setup/sysCustomerList";
	}
	
	@RequestMapping(value = "getbyOfficeId")
	@ResponseBody
	public String getbyOfficeId(){
		return officeService.getbyOfficeId(UserUtils.getUser().getCompany().getId());
	}
	
	@RequestMapping(value = "map")
	@ResponseBody
	public List<TCustomer> map(TCustomer tCustomer, HttpServletRequest request, HttpServletResponse response,Model model){
		return tCustomerService.listTCustomerBySupervisors(tCustomer);
	}
	
	@RequestMapping(value = "ListUrgent")
	@ResponseBody
	public List<TCustomer> ListUrgent(TCustomer tCustomer, HttpServletRequest request, HttpServletResponse response,Model model){
		tCustomer.setOfficeId(UserUtils.getUser().getCompany().getId());
		tCustomer.setUrgent("0");
		return tCustomerService.listTCustomerBySupervisors(tCustomer);
	}
	
	@RequestMapping(value = "urgentupdates")
	@ResponseBody
	public int urgentupdates(String ids,String u){
		return tCustomerService.urgentupdates(Arrays.asList(ids.split(",")),u);
	}
	
	@RequestMapping(value = "outOfServiceupdate")
	@ResponseBody
	public int outOfServiceupdate(String ids,String u){
		int i=0;
		TCompanyYmc tCompanyYmc=new TCompanyYmc();
		tCompanyYmc.setCompanyId(UserUtils.getUser().getCompany().getId());
		if("0".equals(u)){
			
			i= tCustomerService.outOfServiceupdates1(Arrays.asList(ids.split(",")));
			tCompanyYmc.setYmonthCount(i);
			tCompanyYmcService.updateSubtracts(tCompanyYmc);
			
		}else {
			i=  tCustomerService.outOfServiceupdate0(ids);
			tCompanyYmcService.updatePlus(tCompanyYmc);
		}
		return i;
	}
	
	
	
	@RequestMapping(value = "findByState")
	@ResponseBody
	public List<TAppendix> findByState(){
		List<TAppendix> listTAppendix=new ArrayList<TAppendix>();
		listTAppendix=tAppendixService.findByState("0");
		return listTAppendix;
	}
	//@RequiresPermissions("customer:tCustomer:view")
	@RequestMapping(value = "form")
	public String form(TCustomer tCustomer, Model model) {
		model.addAttribute("tCustomer", tCustomer);
		return "modules/inspection/setup/sysCustomer";
	}
	/**
	 * 实现给表单赋值
	 * @param tCustomer
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "forms")
	public TCustomer forms(TCustomer tCustomer, Model model) {
		TCustomer  tc=tCustomerService.get(tCustomer);
		return tc;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public void update(TCustomer tCustomer){
		tCustomerService.save(tCustomer);
	}
	
	/**
	 * ajax使用添加
	 * **/
	//@RequiresPermissions("customer:tCustomer:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(TCustomer tCustomer){
		System.out.println(tCustomer.toString());
		String result="1";
		String uuid= tCustomerService.selectId();
		List<TAppendix> listTAppendix=new ArrayList<TAppendix>();
		listTAppendix=tAppendixService.findByState("0");
		if(listTAppendix.size()>0){
			for(TAppendix tappendix:listTAppendix){
				System.out.println(tappendix.getId());
				tappendix.setDuiyingState("1");
				tappendix.setTCusId(uuid);
				tAppendixService.updateCusid(tappendix);
			}
		}
		if(tCustomer.getCustomerName()!=null && tCustomer.getCustomerName()!= "")
		{
			try{
				//添加主键值
				User user = UserUtils.getUser();
				System.out.println(uuid);
				//添加开始代账日期(格式转换)
				String initperiod=tCustomer.getInitperiod().replaceAll("-", "");
				System.out.println(initperiod);
				tCustomer.setInitperiod(initperiod);
				//必填字段
				tCustomer.setId(uuid);
				tCustomer.setUpdateBy(user);
				tCustomer.setCreateBy(user);
				tCustomer.setUpdateDate(new Date());
				tCustomer.setCreateDate(new Date());
				//System.out.println(tCustomer.getId());
				System.out.println(tCustomer.getInitperiod());
				System.out.println(tCustomer.getValueAddedTax());
				tCustomerService.insertcustomer(tCustomer);
				//System.out.println(tCustomer.getId());
			}
			catch( Exception e){
				e.printStackTrace();
				result="0";
			}
			
		}
		System.out.println(result);
		return result;
	}
	
	//@RequiresPermissions("customer:tCustomer:edit")
	@RequestMapping(value = "delete")
	public String delete(TCustomer tCustomer, RedirectAttributes redirectAttributes) {
		tCustomerService.delete(tCustomer);
		addMessage(redirectAttributes, "删除用户成功");	
		return "redirect:"+Global.getAdminPath()+"/customer/tCustomer/?repage";
	}
	@ResponseBody
	@RequestMapping(value = "delectCustomer")
	public void delectCustomer(String id, RedirectAttributes redirectAttributes) {
		tCustomerService.deleteCustomer(id);
	}
	/**
	 * 用户信息为添加的同时有附件信息的添加，则修改用户（外键）和状态，cy暂时没用
	 * **/
	public void updateappendix(String tcusid){
		TAppendix tAppendix=new TAppendix();
		tAppendix.setDuiyingState("1");
		tAppendix.setTCusId(tcusid);
		tAppendixService.updateCusid(tAppendix);
	}
	/**
	 * 显示上传的附件
	 * **/
	public List<TAppendix> list() {
		List<TAppendix> info=tAppendixService.findByState("0");	
		return info;
	}
	/**
	 * 添加附件信息表中信息
	 * **/
	public TAppendix insertTAppendix(String appendixName,String appendixTypeName){
		
		User user=UserUtils.getUser();
		String id=tAppendixService.selectId();
		TAppendix tAppendix=new TAppendix();
		//附件表主键 
		tAppendix.setId(id);
		//附件的名称
		tAppendix.setAppendixName(appendixName);
		//附件的上传类型及原名称 
		tAppendix.setAppendixTypeName(appendixTypeName);
		//上传者的ID（绑定用户信息表中的ID主键）
		tAppendix.setUpPeopleId(user.getId());
		//上传者的姓名
		tAppendix.setUpPeople(user.getName());
		//上传的对应企业的状态
		tAppendix.setDuiyingState("0");
		try{
			tAppendixService.insertNew(tAppendix);
		}catch(Exception e){
			e.printStackTrace();
		}
		return tAppendix;
	}
	
	/**
	 * 文件的上传方法，cy
	 * **/
	@RequestMapping(value = "fileUpload2")
	@ResponseBody
	public TAppendix fileUpload2(HttpServletRequest request,@RequestParam("appendix") String appendix) throws IllegalStateException, IOException{
		
		String fileName="";
		org.springframework.web.multipart.MultipartHttpServletRequest  mRequest = (MultipartHttpServletRequest)(request);  
		//解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		//先判断request中是否包涵multipart类型的数据，
		 if(multipartResolver.isMultipart(request)){
			 
			//再将request中的数据转化成multipart类型的数据
			 MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			 //迭代器
			 Iterator iter = multiRequest.getFileNames();
			 
			 while(iter.hasNext()){
				    MultipartFile file = multiRequest.getFile((String)iter.next());
				    if(file != null){
				     fileName = file.getOriginalFilename();
				     //解决文件冲突
				     //  下面的加的日期是为了防止上传的名字一样
     				//String path2= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileName;
				     String path = "F:/" + fileName;
				     File localFile = new File(path);
				     //写文件到本地
				     file.transferTo(localFile);
				    }
			   }
		 }
		 //添加附件表数据
		 TAppendix tAppendix=insertTAppendix(appendix,fileName);
		 //List<TAppendix> list=list();
		 return tAppendix;
	}
	
	
	/**
	 * 文件的下载方法
	 * @throws IOException 
	 * **/
	@RequestMapping(value = "downFile")
	public String  downFile(String fileName,HttpServletResponse response) throws IOException{
		 
		 response.setCharacterEncoding("utf-8");
	     response.setContentType("multipart/form-data");
	     //添加头文件，协议的扩展
	     response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
	     
	     try {
	    	 	//这个download目录为啥建立在classes下的,---------线程获取当前项目的编译路径+download
	            String path = "F:\\";
	            //Thread.currentThread().getContextClassLoader().getResource("").getPath()+ "download";	            
	            System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
	            
	            //如果考虑跨平台，如：C:\tmp\test.txt  ==  "C:" + File.separator + "tmp" + File.separator, "test.txt"
	            InputStream inputStream = new FileInputStream(new File(path + File.separator + fileName));
	            
	            //定义一个输出流
	            OutputStream os = response.getOutputStream();
	            
	            //创建byte变量，用于对流一个一个字节的读，返回的int就是这个字节的int表达方式（以整数形式返回实际读取的字节数）。
	            byte[] b = new byte[2048];
	            
	            //定义一个长度
	            int length;
	            
	            //判断输入流的长度
	            while ((length = inputStream.read(b)) > 0) {
	            	//将指定 byte 数组中从偏移量 0 开始的 length 个字节写入此输出流
	                os.write(b, 0, length);
	            }
	            
	            // 这里主要输出关闭。
	            os.close();
	            //这里输入流关闭
	            inputStream.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	     //返回值要注意，要不然就出现下面这句错误！
         //java+getOutputStream() has already been called for this response
	     return null;
	}
    
    /**
     * 删除单个文件
     * @param   sPath  被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
	@RequestMapping(value = "deleteFile")
    public void deleteFile(String sPathName) {
    	boolean flag = false;
    	String sPath="F:\\";
    	
    	//跨平台的时候，考虑File.separator，相当于/
        File file = new File(sPath + File.separator + sPathName);
        
        
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        //return flag;
    }
	
	/**
	 * 删除附件ajax使用，配合删除单个文件
	 * **/
	@RequestMapping(value = "deleteById")
	@ResponseBody
	public String deleteById(String id){
		try{
			tAppendixService.deleteInfo(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	    //List<TAppendix> list=list();
	    //return list;
		return "1";
	}
	
	/**
	 * chf
	 * **/
	@ResponseBody
	@RequestMapping(value = "getName")
	public List<TCustomer> getName(TCustomer tCustomer, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException{
 		String key=new String(request.getParameter("key").getBytes("iso-8859-1"), "utf-8");
		tCustomer.setCustomerName(key);
 		String top = request.getParameter("top");
 		List<TCustomer> list=null;
 		tCustomer.setSupervisor(UserUtils.getUser().getId());
 		if(key!=null){
 			 list=tCustomerService.getName(tCustomer);
 		}
 		System.out.println(list);
 		return list;
	}

	
	//@RequiresPermissions("customer:tCustomer:view")
	@RequestMapping(value = "setSession")
	public String setSession(HttpServletRequest request, HttpServletResponse response,Map<String, Object> map) {
		String sessionCustomerId=request.getParameter("sessionCustomerId");
		if("".equals(sessionCustomerId)||null==sessionCustomerId){
			return "redirect:"+Global.getAdminPath()+"/workterrace/chargeToAccount/?repage";
		}
		else{
			TCustomer sessionCustomer=tCustomerService.get(sessionCustomerId);
			map.put("sessionCustomer", sessionCustomer);
			return "redirect:"+Global.getAdminPath()+"/sys/user/voucherIndexx";
			
		}
	}
	
}