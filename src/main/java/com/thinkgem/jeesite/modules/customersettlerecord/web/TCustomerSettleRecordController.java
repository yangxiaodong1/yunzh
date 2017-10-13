/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customersettlerecord.web;

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
import com.thinkgem.jeesite.modules.customersettlerecord.entity.TCustomerSettleRecord;
import com.thinkgem.jeesite.modules.customersettlerecord.service.TCustomerSettleRecordService;

/**
 * 结账记录表Controller
 * @author zhangtong
 * @version 2016-01-30
 */
@Controller
@RequestMapping(value = "${adminPath}/customersettlerecord/tCustomerSettleRecord")
public class TCustomerSettleRecordController extends BaseController {

	@Autowired
	private TCustomerSettleRecordService tCustomerSettleRecordService;
	
	@ModelAttribute
	public TCustomerSettleRecord get(@RequestParam(required=false) String id) {
		TCustomerSettleRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCustomerSettleRecordService.get(id);
		}
		if (entity == null){
			entity = new TCustomerSettleRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("customersettlerecord:tCustomerSettleRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCustomerSettleRecord tCustomerSettleRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TCustomerSettleRecord> page = tCustomerSettleRecordService.findPage(new Page<TCustomerSettleRecord>(request, response), tCustomerSettleRecord); 
		model.addAttribute("page", page);
		return "modules/customersettlerecord/tCustomerSettleRecordList";
	}

	@RequiresPermissions("customersettlerecord:tCustomerSettleRecord:view")
	@RequestMapping(value = "form")
	public String form(TCustomerSettleRecord tCustomerSettleRecord, Model model) {
		model.addAttribute("tCustomerSettleRecord", tCustomerSettleRecord);
		return "modules/customersettlerecord/tCustomerSettleRecordForm";
	}

	@RequiresPermissions("customersettlerecord:tCustomerSettleRecord:edit")
	@RequestMapping(value = "save")
	public String save(TCustomerSettleRecord tCustomerSettleRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCustomerSettleRecord)){
			return form(tCustomerSettleRecord, model);
		}
		tCustomerSettleRecordService.save(tCustomerSettleRecord);
		addMessage(redirectAttributes, "保存结账记录表成功");
		return "redirect:"+Global.getAdminPath()+"/customersettlerecord/tCustomerSettleRecord/?repage";
	}
	
	@RequiresPermissions("customersettlerecord:tCustomerSettleRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(TCustomerSettleRecord tCustomerSettleRecord, RedirectAttributes redirectAttributes) {
		tCustomerSettleRecordService.delete(tCustomerSettleRecord);
		addMessage(redirectAttributes, "删除结账记录表成功");
		return "redirect:"+Global.getAdminPath()+"/customersettlerecord/tCustomerSettleRecord/?repage";
	}

}