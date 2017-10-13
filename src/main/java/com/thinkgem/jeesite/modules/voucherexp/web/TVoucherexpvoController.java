/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucherexp.web;

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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherexpvo;
import com.thinkgem.jeesite.modules.voucherexp.service.TVoucherexpvoService;

/**
 * 显示凭证Controller
 * @author cy
 * @version 2016-02-19
 */
@Controller
@RequestMapping(value = "${adminPath}/voucherexp/tVoucherexpvo")
public class TVoucherexpvoController extends BaseController {

	@Autowired
	private TVoucherexpvoService tVoucherexpvoService;
	
	@ModelAttribute
	public TVoucherexpvo get(@RequestParam(required=false) String id) {
		TVoucherexpvo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tVoucherexpvoService.get(id);
		}
		if (entity == null){
			entity = new TVoucherexpvo();
		}
		return entity;
	}
	
	@RequiresPermissions("voucherexp:tVoucherexpvo:view")
	@RequestMapping(value = {"list", ""})
	public String list(TVoucherexpvo tVoucherexpvo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TVoucherexpvo> page = tVoucherexpvoService.findPage(new Page<TVoucherexpvo>(request, response), tVoucherexpvo); 
		model.addAttribute("page", page);
		return "modules/voucherexp/tVoucherexpvoList";
	}

	@RequiresPermissions("voucherexp:tVoucherexpvo:view")
	@RequestMapping(value = "form")
	public String form(TVoucherexpvo tVoucherexpvo, Model model) {
		model.addAttribute("tVoucherexpvo", tVoucherexpvo);
		return "modules/voucherexp/tVoucherexpvoForm";
	}

	@RequiresPermissions("voucherexp:tVoucherexpvo:edit")
	@RequestMapping(value = "save")
	public String save(TVoucherexpvo tVoucherexpvo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tVoucherexpvo)){
			return form(tVoucherexpvo, model);
		}
		tVoucherexpvoService.save(tVoucherexpvo);
		addMessage(redirectAttributes, "保存显示凭证成功");
		return "redirect:"+Global.getAdminPath()+"/voucherexp/tVoucherexpvo/?repage";
	}
	
	@RequiresPermissions("voucherexp:tVoucherexpvo:edit")
	@RequestMapping(value = "delete")
	public String delete(TVoucherexpvo tVoucherexpvo, RedirectAttributes redirectAttributes) {
		tVoucherexpvoService.delete(tVoucherexpvo);
		addMessage(redirectAttributes, "删除显示凭证成功");
		return "redirect:"+Global.getAdminPath()+"/voucherexp/tVoucherexpvo/?repage";
	}

}