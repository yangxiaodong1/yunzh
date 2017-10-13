/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.web;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;

import com.thinkgem.jeesite.modules.chineseCharToEn.ChineseCharToEnService;
import com.thinkgem.jeesite.modules.customer.entity.*;
import com.thinkgem.jeesite.modules.customer.service.TAppendixService;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TCompanyYmc;
import com.thinkgem.jeesite.modules.inspection.service.workstatistics.TCompanyYmcService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.weChat.entity.TWechatusers;
import com.thinkgem.jeesite.modules.weChat.service.TWechatusersService;

/**
 * 客户Controller
 * @author cy
 * @version 2015-11-23
 */
@SessionAttributes({"sessionCustomer"})
@Controller
@RequestMapping(value = "${adminPath}/customer/tCustomer")
public class TCustomerController extends BaseController {

	@Autowired
	private TCustomerService tCustomerService;
	@Autowired
	private TAppendixService tAppendixService;
	@Autowired
	private ChineseCharToEnService chineseCharToEnService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TWechatusersService tWechatusersService;
	@Autowired
	private TCompanyYmcService tCompanyYmcService;
	
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
	
	//@RequiresPermissions("customer:tCustomer:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCustomer tCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TCustomer> page = tCustomerService.findPage(new Page<TCustomer>(request, response), tCustomer); 
		model.addAttribute("page", page);
		return "modules/customer/tCustomerList";
	}
	
	//@RequiresPermissions("customer:tCustomer:view")
	@RequestMapping(value = "form")
	public String form(TCustomer tCustomer, Model model) {
		model.addAttribute("tCustomer", tCustomer);
		return "modules/customer/tCustomerForm";
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
		//System.out.println(tCustomer.getId()+"杨晓东杨晓东杨晓东gggg");
		TCustomer  tc=tCustomerService.get(tCustomer);
		System.out.println(tc.getCertificatesmature()+"证书到期时间");
		Date date=tc.getCertificatesmature();
		if(date!=null){
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
			 String dateString=formatter.format(date);
			 tc.setCertificatesmatureNew(dateString);
		}
		 
		return tc;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public String update(TCustomer tCustomer,HttpServletRequest request, HttpServletResponse response) throws ParseException{
		System.out.println(tCustomer.getId()+"vvvvv");
		String result="1";
    
    String idsString=request.getParameter("array");//得到附件的id
				if(idsString!=""&&idsString!=null){
					TAppendix tappendix=new TAppendix();
					String[] strArray=null;
					strArray=idsString.split(",");
					for (String s : strArray) {
						tappendix=	tAppendixService.get(s);
						tappendix.setTCusId(tCustomer.getId());
						tAppendixService.updateCusid(tappendix);
					}
				}
				
				String certificatesmatureNew=tCustomer.getCertificatesmatureNew();
				if(!"".equals(certificatesmatureNew)&&certificatesmatureNew!=null){
					Date date=null;  
				    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");  
				    date=formatter.parse(certificatesmatureNew);
				    tCustomer.setCertificatesmature(date);
				}
				
				//System.out.println(certificatesmatureNew+"证书到期哈哈哈");
		tCustomerService.save(tCustomer);
		String enName = chineseCharToEnService.getFirstSpell(tCustomer.getCustomerName());
		TWechatusers tWechatusers = new TWechatusers();
		tWechatusers.setFdbid(tCustomer.getId());
		tWechatusers.setUserName(enName);
		tWechatusersService.updateuesrname(tWechatusers);
		return result;
	}
	
	/**
	 * ajax使用添加
	 * **/
	//@RequiresPermissions("customer:tCustomer:edit")
	@RequestMapping(value = "save")
	@ResponseBody

	public Object save(TCustomer tCustomer,HttpServletRequest request, HttpServletResponse response){

		System.out.println(tCustomer.getId());
		String result="1";

		insertCompanyYmc();
		if(tCustomer.getCustomerName()!=null && tCustomer.getCustomerName()!= "")
		{
			try{
				//添加主键值
				User user = UserUtils.getUser();

				//添加开始代账日期(格式转换)
				String initperiod=tCustomer.getInitperiod().replaceAll("-", "");
				System.out.println(initperiod);
				tCustomer.setInitperiod(initperiod);
				//必填字段

				//tCustomer.setId(uuid);
                //--------
				String certificatesmatureNew=tCustomer.getCertificatesmatureNew();
				if(!"".equals(certificatesmatureNew)){
					SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");  
					Date date=formatter.parse(certificatesmatureNew);
				    tCustomer.setCertificatesmature(date);
				}
			    tCustomer.setUrgent("0");
			    tCustomer.setOutOfService("0");
				//--------
				tCustomer.setUpdateBy(user);
				tCustomer.setCreateBy(user);
				tCustomer.setUpdateDate(new Date());
				tCustomer.setCreateDate(new Date());
				//System.out.println(tCustomer.getId());
				System.out.println(tCustomer.getInitperiod());
				System.out.println(tCustomer.getValueAddedTax());
				tCustomerService.insertcustomer(tCustomer);
				List<TAppendix> listTAppendix=new ArrayList<TAppendix>();
				/*
				listTAppendix=tAppendixService.findByState("0");
				if(listTAppendix.size()>0){
					for(TAppendix tappendix:listTAppendix){
						System.out.println(tappendix.getId());
						tappendix.setDuiyingState("1");
						tappendix.setTCusId(tCustomer.getId());
						tAppendixService.updateCusid(tappendix);
					}
				}
				*/
				//---------yang
				//System.out.println(tCustomer.getId()+"得到用户iddddd");
				String idsString=request.getParameter("array");//得到附件的id
				if(idsString!=""&&idsString!=null){
					TAppendix tappendix=new TAppendix();
					String[] strArray=null;
					strArray=idsString.split(",");
					for (String s : strArray) {
						tappendix=	tAppendixService.get(s);
						tappendix.setTCusId(tCustomer.getId());
						tAppendixService.updateCusid(tappendix);
					}
				}
				
				//---------yang
				
				//System.out.println(tCustomer.getId());
				//添加微信账号 by： zt add
				String enName = chineseCharToEnService.getFirstSpell(tCustomer.getCustomerName());
				
				//处理账户唯一性 by : zt
				int userNum = tWechatusersService.selectLikeUserName(enName);
				enName = enName + String.valueOf(userNum);
				
				String pwd = systemService.entryptPassword("666666");
				TWechatusers tWechatusers = new TWechatusers();
				tWechatusers.setOfficeCompanyName(UserUtils.getUser().getCompany().getName());
				tWechatusers.setFdbid(tCustomer.getId());
				tWechatusers.setPlainTextPassword("666666");
				tWechatusers.setUserName(enName);
				tWechatusers.setPassword(pwd);
				tWechatusersService.saveUserInfo(tWechatusers);
				
			}
			catch( Exception e){
				e.printStackTrace();
				result="0";
			}
			
		}
		return ImmutableMap.of("result", result, "tCustomer", tCustomer);
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
		try {
			tCustomerService.deleteCustomer(id);
			TCompanyYmc tCompanyYmc=new TCompanyYmc();
			tCompanyYmc.setCompanyId(UserUtils.getUser().getCompany().getId());
			tCompanyYmcService.updateSubtract(tCompanyYmc);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
	/*@RequestMapping(value = "fileUpload2")
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
				     String path = "d:/" + fileName;
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
	*/
	@RequestMapping(value = "fileUpload2")
	@ResponseBody
	public TAppendix fileUpload2(HttpServletRequest request,@RequestParam("appendix") String appendix) throws IllegalStateException, IOException{
		
		String fileName="";
		org.springframework.web.multipart.MultipartHttpServletRequest  mRequest = (MultipartHttpServletRequest)(request);  
		//解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		TAppendix t=new TAppendix();
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
				     Map<String,Object> fileMap=FileUtils.upload(file,Global.getappendix(),mRequest);//对文件进行上传
				     if(!fileMap.isEmpty()){
				    	 
				    	 t.setAppendixTypeName(fileMap.get("newName").toString());
				    	 t.setAppendixName(appendix);
				    	 tAppendixService.save(t);
				    	 //id=t.getId().toString();
						}
				    }
			   }
		 }
		 //添加附件表数据
		 //TAppendix tAppendix=insertTAppendix(appendix,fileName);
		 //List<TAppendix> list=list();
		 if(t.getCreateBy()!=null){
			 String nameString= systemService.getUser(t.getCreateBy().getId()).getName() ;
			
				t.setUpPeople(nameString);
		 }
		 return t;
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
	           // String path = "d:\\";
	    	 String path=Global.getappendix();
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
	
	@RequestMapping(value = "insertCompanyYmc")
	public void insertCompanyYmc(){
		User user=UserUtils.getUser();
		String ym=getYearMonth();
		TCompanyYmc tCompanyYmc=new TCompanyYmc();
		tCompanyYmc.setCompanyId(user.getCompany().getId());
		tCompanyYmc.setYmonth(ym);		//当前月份
		TCompanyYmc tCompanyYmcback=tCompanyYmcService.insertTerm(tCompanyYmc);
		TCompanyYmc tCompanyYmcback1=new TCompanyYmc();
		if(tCompanyYmcback!=null)
			tCompanyYmcback1=tCompanyYmcback;
		//最近的一期
		TCompanyYmc tCompanyYmcbackzj=tCompanyYmcService.selectzjymc(tCompanyYmc);
		String dqymc=ym.substring(0,4);
		String  dqm=ym.substring(4,6);
		
		if(tCompanyYmcbackzj!=null){
		String year=tCompanyYmcbackzj.getYmonth().substring(0, 4);
		String yearls=tCompanyYmcbackzj.getYmonth().substring(0, 4);
		String month=tCompanyYmcbackzj.getYmonth().substring(4, 6);
		int maxmath=13;
		int yearcha=Integer.parseInt(dqymc)-Integer.parseInt(year);
		if(yearcha>0){
			if(tCompanyYmcback==null)
				tCompanyYmcback1.setYmonthCount(tCompanyYmcbackzj.getYmonthCount()+0);
			else
				tCompanyYmcback1.setYmonthCount(tCompanyYmcbackzj.getYmonthCount()+tCompanyYmcback1.getYmonthCount());
			for(int y=0;y<=yearcha;y++){
				year=(Integer.parseInt(yearls)+y)+"";
				if(y>0){
					month="0";
					if(Integer.parseInt(year)==Integer.parseInt(dqymc)){
						maxmath=Integer.parseInt(dqm);
					}
				}
				
				for(int i=Integer.parseInt(month);i<maxmath-1;i++){
					if(i+1>=10){
						month=(i+1) + "";
			        }else{
			        	month="0"+((i+1)+"");
			        }
					tCompanyYmc.setYmonth(year+month);
					tCompanyYmc.setYmonthCount(tCompanyYmcbackzj.getYmonthCount());
					tCompanyYmcService.insert(tCompanyYmc);
				}
			}
		}
		if(yearcha==0){
			if(Integer.parseInt(dqm)-Integer.parseInt(month)!=1){
				if(tCompanyYmcback==null)
					tCompanyYmcback1.setYmonthCount(tCompanyYmcbackzj.getYmonthCount()+0);
				else
					tCompanyYmcback1.setYmonthCount(tCompanyYmcbackzj.getYmonthCount()+tCompanyYmcback1.getYmonthCount());
				for(int i=Integer.parseInt(month);i<Integer.parseInt(dqm)-1;i++){
					if(Integer.parseInt(dqm)==Integer.parseInt(month) && Integer.parseInt(year)==Integer.parseInt(dqymc)){
						tCompanyYmcback.setYmonthCount(tCompanyYmcbackzj.getYmonthCount()+1);
						tCompanyYmcService.updateymonthcount(tCompanyYmcback);
					}
					if(i+1>=10){
						month=(i+1) + "";
			        }else{
			        	month="0"+((i+1)+"");
			        }
					tCompanyYmc.setYmonth(year+month);
					tCompanyYmc.setYmonthCount(tCompanyYmcbackzj.getYmonthCount());
					tCompanyYmcService.insert(tCompanyYmc);
				}
			}
		}
		}
		if(tCompanyYmcback!=null){
			if(tCompanyYmcback1.getYmonthCount()==null){
				if(tCompanyYmcback1.getYmonthCount()==0)
					tCompanyYmcback1.setYmonthCount(tCompanyYmcbackzj.getYmonthCount()+tCompanyYmcback1.getYmonthCount());
				System.out.println(tCompanyYmcback1.getYmonthCount());
				tCompanyYmcback1.setYmonthCount(1);
				tCompanyYmcService.save(tCompanyYmcback1);
			}else{
				if(tCompanyYmcback1.getYmonthCount()==0)
					tCompanyYmcback1.setYmonthCount(tCompanyYmcbackzj.getYmonthCount()+tCompanyYmcback1.getYmonthCount());
				tCompanyYmcback.setYmonthCount(tCompanyYmcback1.getYmonthCount()+1);
				tCompanyYmcback.setYmonth(ym);
				tCompanyYmcService.updateymonthcount(tCompanyYmcback);
			}
		}else{
			if(tCompanyYmcbackzj!=null)
				tCompanyYmcback1.setYmonthCount(tCompanyYmcbackzj.getYmonthCount()+1);
			else
				tCompanyYmcback1.setYmonthCount(1);
			tCompanyYmcback1.setYmonth(ym);	
			tCompanyYmcback1.setCompanyId(user.getCompany().getId());
			tCompanyYmcService.save(tCompanyYmcback1);
		}
		
	}
	
	public String getYearMonth(){
		String showMonth="";
    	Calendar now = Calendar.getInstance();
    	int year=now.get(Calendar.YEAR);
    	System.out.println("年: " + year);  
    	int month=(now.get(Calendar.MONTH) + 1);
        System.out.println("月: " + month + "");
        
        if(month>=10){
        	showMonth=month + "";
        }else{
        	showMonth="0"+(month+"");
        }
        System.out.println(showMonth);
        return (year+"")+showMonth;
	}
	
	
	
	
	
	
}