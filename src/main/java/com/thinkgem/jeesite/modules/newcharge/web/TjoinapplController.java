/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.newcharge.web;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.thinkgem.jeesite.modules.newcharge.entity.Tjoinappl;
import com.thinkgem.jeesite.modules.newcharge.service.TjoinapplService;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 加盟申请Controller
 * @author yang
 * @version 2016-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/newcharge/tjoinappl")
public class TjoinapplController extends BaseController {

	@Autowired
	private TjoinapplService tjoinapplService;
	
	@ModelAttribute
	public Tjoinappl get(@RequestParam(required=false) String id) {
		Tjoinappl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tjoinapplService.get(id);
		}
		if (entity == null){
			entity = new Tjoinappl();
		}
		return entity;
	}
	
	@RequiresPermissions("newcharge:tjoinappl:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tjoinappl tjoinappl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Tjoinappl> page = tjoinapplService.findPage(new Page<Tjoinappl>(request, response), tjoinappl); 
		model.addAttribute("page", page);
		return "modules/newcharge/tjoinapplList";
	}

	@RequiresPermissions("newcharge:tjoinappl:view")
	@RequestMapping(value = "form")
	public String form(Tjoinappl tjoinappl, Model model) {
		model.addAttribute("tjoinappl", tjoinappl);
		return "modules/newcharge/tjoinapplForm";
	}

	@RequiresPermissions("newcharge:tjoinappl:edit")
	@RequestMapping(value = "save")
	public String save(Tjoinappl tjoinappl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tjoinappl)){
			return form(tjoinappl, model);
		}
		tjoinapplService.save(tjoinappl);
		addMessage(redirectAttributes, "保存加盟申请成功");
		return "redirect:"+Global.getAdminPath()+"/newcharge/tjoinappl/?repage";
	}
	
	@RequiresPermissions("newcharge:tjoinappl:edit")
	@RequestMapping(value = "delete")
	public String delete(Tjoinappl tjoinappl, RedirectAttributes redirectAttributes) {
		tjoinapplService.delete(tjoinappl);
		addMessage(redirectAttributes, "删除加盟申请成功");
		return "redirect:"+Global.getAdminPath()+"/newcharge/tjoinappl/?repage";
	}
	
	@RequestMapping(value = {"addym"})//添加申请列表
	public String addym(Tjoinappl tjoinappl, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("tjoinappl", tjoinappl);
		return "modules/adminback/join_add";
	}
	@RequestMapping(value = {"menu"})//申请列表
	public String menu(Tjoinappl tjoinappl, HttpServletRequest request, HttpServletResponse response, Model model) {
	 List<Tjoinappl>	tList=tjoinapplService.findList(tjoinappl);
	 
	 for (Tjoinappl t : tList) {
		 Date gd=t.getCreateDate();
  	  
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String createDa = sdf.format(gd);
			t.setCreateDateString(createDa);
	}
	 model.addAttribute("tList", tList);
		model.addAttribute("tjoinappl", tjoinappl);
		 return "modules/adminback/clientManage_joinAppl";
	}
	
	@RequestMapping(value = {"saveApp"})//添加申请列表
	public String saveApp(Tjoinappl tjoinappl, HttpServletRequest request, HttpServletResponse response, Model model) {
		//System.out.println(tjoinappl.getArea().getId()+"大范甘迪");
		//System.out.println(tjoinappl.getArea().getName()+"sdfgdfg");
		//tjoinappl.setCity(tjoinappl.getArea().getName());
		tjoinappl.setCity(tjoinappl.getCity1()+"/"+tjoinappl.getCity2()+"/"+tjoinappl.getCity3());
		tjoinapplService.save(tjoinappl);
		model.addAttribute("tjoinappl", tjoinappl);
		return "redirect:"+Global.getAdminPath()+"/newcharge/tjoinappl/menu";
	}
	@RequestMapping(value = {"savefeedback"})//保存反馈信息，其实就是修改一条记录
	public String savefeedback(Tjoinappl tjoinappl, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			String te=request.getParameter("text");
			System.out.println(te);
			System.out.println(tjoinappl.getId()+"jiji");
			if("option5".equals(tjoinappl.getMessage())){
				String text=request.getParameter("text");
				tjoinappl.setMessage(text);
			}
			tjoinapplService.save(tjoinappl);
			model.addAttribute("tjoinappl", tjoinappl);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:"+Global.getAdminPath()+"/newcharge/tjoinappl/menu";
	}
	@RequestMapping(value = "forms")//给表单赋值
	@ResponseBody
	public Tjoinappl forms(Tjoinappl tjoinappl, Model model,HttpServletRequest request,HttpServletRequest response) {
		String id=request.getParameter("id");
		
		Tjoinappl tc=tjoinapplService.get(id);
		return tc;
	}
}