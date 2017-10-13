/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.amortize.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.amortize.entity.TAmortize;
import com.thinkgem.jeesite.modules.amortize.service.TAmortizeService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;


/**
 * 摊销设置Controller
 * @author yang
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/amortize/tAmortize")
public class TAmortizeController extends BaseController {

	@Autowired
	private TAmortizeService tAmortizeService;
	
	@ModelAttribute
	public TAmortize get(@RequestParam(required=false) String id) {
		TAmortize entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tAmortizeService.get(id);
		}
		if (entity == null){
			entity = new TAmortize();
		}
		return entity;
	}
	
	//@RequiresPermissions("amortize:tAmortize:view")
	@RequestMapping(value = {"list", ""})
	public String list(TAmortize tAmortize, HttpServletRequest request, HttpServletResponse response, Model model,HttpSession session) {
		Page<TAmortize> page = tAmortizeService.findPage(new Page<TAmortize>(request, response), tAmortize); 
		
		TCustomer customerObject=(TCustomer)session.getAttribute("sessionCustomer");
		String id=customerObject.getId();
		System.out.println(id+"dsds");
		tAmortize.setFdbid(id);
		List<TAmortize> ta=tAmortizeService.selectbyamortize_account_id(tAmortize);//分组查询,现在根据登陆用户的id查询
		model.addAttribute("page", page);
		model.addAttribute("ta", ta);
		System.out.println("杨晓东");
				return "modules/amortize/tAmortizes";
	}
	@RequestMapping(value = "amortize")
	public String amortize(TAmortize tAmortize, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes){
		//1解决编码问题
		response.setContentType("text/html");
		  response.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
		  //2获取传递过来的id值和状态值
		String id=request.getParameter("id");
		String amortize_status=request.getParameter("amortize_status");
		//3给对象tAmortize的id,amortize_status属性赋值
		//tAmortize.setId(id);//1.9号之前
		//tAmortize.setAmortizeAccountId(id);
		tAmortize.settVoucherExpId(id);
		tAmortize.setAmortizeStatus(amortize_status);
		//4，改变数据库中这条数据的状态
		tAmortizeService.update_status(tAmortize);
		//5,将结果再返回到页面
		return "redirect:"+Global.getAdminPath()+"/amortize/tAmortize/list";
	}
	
	//@RequiresPermissions("amortize:tAmortize:view")
	@RequestMapping(value = "form")
	public String form(TAmortize tAmortize, Model model) {
		model.addAttribute("tAmortize", tAmortize);
		return "modules/amortize/tAmortizeForm";
	}

	//@RequiresPermissions("amortize:tAmortize:edit")
	@RequestMapping(value = "save")
	public String save(TAmortize tAmortize, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tAmortize)){
			return form(tAmortize, model);
		}
		tAmortizeService.save(tAmortize);
		addMessage(redirectAttributes, "保存摊销设置成功");
		return "redirect:"+Global.getAdminPath()+"/amortize/tAmortize/?repage";
	}
	
	//@RequiresPermissions("amortize:tAmortize:edit")
	@RequestMapping(value = "delete")
	public String delete(TAmortize tAmortize, RedirectAttributes redirectAttributes) {
		tAmortizeService.delete(tAmortize);
		addMessage(redirectAttributes, "删除摊销设置成功");
		return "redirect:"+Global.getAdminPath()+"/amortize/tAmortize/?repage";
	}

}