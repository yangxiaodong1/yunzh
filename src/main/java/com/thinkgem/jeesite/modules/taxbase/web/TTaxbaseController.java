/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.taxbase.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.taxbase.entity.TTaxbase;
import com.thinkgem.jeesite.modules.taxbase.service.TTaxbaseService;
import com.thinkgem.jeesite.modules.taxbaseformula.entity.VTaxbaseformula;
import com.thinkgem.jeesite.modules.taxbaseformula.service.VTaxbaseformulaService;

/**
 * 税基数表Controller
 * 
 * @author zt
 * @version 2016-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/taxbase/tTaxbase")
public class TTaxbaseController extends BaseController {

	@Autowired
	private TTaxbaseService tTaxbaseService;
	@Autowired
	private TAccountService tAccountService;
	@Autowired
	private VTaxbaseformulaService vTaxbaseformulaService;
	@RequestMapping(value = { "list", "" })
	public String list(TTaxbase tTaxbase, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		//清除缓存
		response.setHeader("Pragma", "No-Cache");
		response.setHeader("Cache-Control", "No-Cache");
		response.setDateHeader("Expires", 0);
		List<TAccount> list=tAccountService.findAllListByfdbidLevel(companyId,companyInfo.getSystem());
		List<VTaxbaseformula> listTaxbase=vTaxbaseformulaService.findTTaxbaseFormulaByfdbIdAndTaxCategory(companyId,"");
		model.addAttribute("account", list);
		model.addAttribute("taxbase", listTaxbase);
		return "modules/taxCategory/taxCategory";

	}

	@RequestMapping(value = "save")
	public String save(TTaxbase tTaxbase,HttpServletRequest request,
			HttpServletResponse response,HttpSession session, Model model) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		tTaxbaseService.deleteTaxbasesByfdbid(companyId,null);	//删除该公司的所有的税金公式
		return "redirect:" + Global.getAdminPath()
				+ "/taxbase/tTaxbase/?repage";
	}
	
	/**
	 * 获取公司客户信息
	 */
	public TCustomer getCompanyInfo(Model model, HttpSession session) {
		Object obj = session.getAttribute("sessionCustomer");
		TCustomer companyInfo = (TCustomer) obj; // 客户公司信息对象
//		String companyName =  companyInfo.getCustomerName();
//		model.addAttribute("companyName", companyName);
		return companyInfo;

	}
	@RequestMapping(value = "addtaxbase")
	@ResponseBody
	public Object addtaxbase(PrintWriter printWriter,HttpSession session,Model model,
			String rate,String state,String parentaccountid,String result,String id) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		/*System.out.println(rate);
		System.out.println(state);
		System.out.println(parentaccountid);
		System.out.println(result);
		System.out.println(id);*/
		tTaxbaseService.deleteTaxbasesByfdbid(companyId,parentaccountid);
		tAccountService.updateByfdbidAndAccountid(rate,state,id);
		
		List<TTaxbase> list=new ArrayList<TTaxbase>(); 
		JSONArray jsonArray = new JSONArray(result);//把json串转换成json数组
		for(int i=0;i<jsonArray.length();i++){
			TTaxbase tTaxbase=new TTaxbase();
			String accountid = jsonArray.getJSONObject(i).getString(
					"accountid"); //科目编码
			String op = jsonArray.getJSONObject(i).getString(
					"op"); //符号
			tTaxbase.setAccountid(accountid);
			tTaxbase.setFdbid(companyId);
			tTaxbase.setFtype("SL");
			tTaxbase.setOp(op);
			tTaxbase.setTaxcategory(parentaccountid);
			list.add(tTaxbase);
		}
		int count=-1;
		if(list!=null && list.size()>0){
			count=tTaxbaseService.savaListTaxbases(list);
		}
		
		/*String jsonString="";
    	JsonMapper json=new JsonMapper(); 	
    	
		jsonString=json.toJsonString(count);
				
        printWriter.write(jsonString);  
        printWriter.flush();  
        printWriter.close(); */
		return count;
	}
}