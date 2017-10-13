/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.quotation.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.quotation.entity.Tquotation;
import com.thinkgem.jeesite.modules.quotation.service.TquotationService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 语录Controller
 * @author yang
 * @version 2016-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/quotation/tquotation")
public class TquotationController extends BaseController {

	@Autowired
	private TquotationService tquotationService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Tquotation get(@RequestParam(required=false) String id) {
		Tquotation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tquotationService.get(id);
		}
		if (entity == null){
			entity = new Tquotation();
		}
		return entity;
	}
	
	@RequiresPermissions("quotation:tquotation:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tquotation tquotation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Tquotation> page = tquotationService.findPage(new Page<Tquotation>(request, response), tquotation); 
		model.addAttribute("page", page);
		return "modules/quotation/tquotationList";
	}

	@RequiresPermissions("quotation:tquotation:view")
	@RequestMapping(value = "form")
	public String form(Tquotation tquotation, Model model) {
		model.addAttribute("tquotation", tquotation);
		return "modules/quotation/tquotationForm";
	}

	@RequiresPermissions("quotation:tquotation:edit")
	@RequestMapping(value = "save")
	public String save(Tquotation tquotation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tquotation)){
			return form(tquotation, model);
		}
		tquotationService.save(tquotation);
		addMessage(redirectAttributes, "保存语录表成功");
		return "redirect:"+Global.getAdminPath()+"/quotation/tquotation/?repage";
	}
	
	@RequiresPermissions("quotation:tquotation:edit")
	@RequestMapping(value = "delete")
	public String delete(Tquotation tquotation, RedirectAttributes redirectAttributes) {
		tquotationService.delete(tquotation);
		addMessage(redirectAttributes, "删除语录表成功");
		return "redirect:"+Global.getAdminPath()+"/quotation/tquotation/?repage";
	}

	//@RequiresPermissions("quotation:tquotation:edit")
	@RequestMapping(value = "saveQuotation")//保存语录
	//@ResponseBody
	public String saveQuotation(Tquotation tquotation, Model model, RedirectAttributes redirectAttributes) {
		
		tquotationService.save(tquotation);
		//return "redirect:"+Global.getAdminPath()+"/quotation/tquotation/QuotationMenue";
		
		return "redirect:"+Global.getAdminPath()+"/quotation/tquotation/QuotationMenue";
	}
	@RequestMapping(value = "QuotationMenue")//语录列表
	
	public String QuotationMenue(Tquotation tquotation, Model model, RedirectAttributes redirectAttributes) {
       List<Tquotation> tqlist=tquotationService.findList(tquotation);
      // User user=new User();
       
       for (Tquotation t : tqlist) {
    	   Date gd=t.getCreateDate();
    	   Date ud=t.getUpdateDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String createDa = sdf.format(gd); 
			String update = sdf.format(ud); 
			t.setCreateDateString(createDa);
			t.setUpdateDateString(update);
			System.out.println(t.getUpdateBy().getId()+"得到id啦哈哈哈传递");
			//User user=new User();
			//user.setId(t.getCreateBy().getId());
			//user=systemService.getUser(t.getCreateBy().getId());
			User user=systemService.getUser(t.getUpdateBy().getId());
			if(user!=null){
				t.setUpdateByStrinig(user.getName());
			}
			
	   }
       //model.addAttribute("user", user);
    	model.addAttribute("tqlist", tqlist);
		model.addAttribute("tquotation", tquotation);
		 return "modules/adminback/contentManage_quotesManage";
	}
    @RequestMapping(value = "quotation_add")//语录添加
	
	public String quotation_add(Tquotation tquotation, Model model, RedirectAttributes redirectAttributes) {
    	
		model.addAttribute("tquotation", tquotation);
		return "modules/adminback/quotation_add";
	}
    //
    @RequestMapping(value = "quotation_delete")//语录删除通过id 
	public String quotation_delete(Tquotation tquotation, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
    	String id=request.getParameter("id");
    	System.out.println("得到的id是"+id);
    	tquotationService.deleteById(id);
		//model.addAttribute("tquotation", tquotation);
		return "redirect:"+Global.getAdminPath()+"/quotation/tquotation/QuotationMenue";
	}
    @RequestMapping(value = "quotation_update")//语录修改状态通过id 
	public String quotation_update(Tquotation tquotation, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
    	String id=request.getParameter("id");
    	String status=request.getParameter("status");
    	tquotation.setId(id);
    	tquotation.setStartstatus(status);
    	tquotationService.save(tquotation);
    	
		return "redirect:"+Global.getAdminPath()+"/quotation/tquotation/QuotationMenue";
	}
    @RequestMapping(value = "forms")//编辑
	public String forms(Tquotation tquotation, Model model) {
    	System.out.println("获取到id了fff"+tquotation.getId());
		model.addAttribute("tquotation", tquotation);
		return "modules/adminback/quotation_edit";
	}
}