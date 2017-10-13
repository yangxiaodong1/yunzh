/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertemplateexp.web;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.vouchertemplate.entity.TVoucherTemplate;
import com.thinkgem.jeesite.modules.vouchertemplate.service.TVoucherTemplateService;
import com.thinkgem.jeesite.modules.vouchertemplateexp.entity.TVoucherTemplateExp;
import com.thinkgem.jeesite.modules.vouchertemplateexp.service.TVoucherTemplateExpService;

/**
 * 凭证模板摘要Controller
 * 
 * @author 凭证模板摘要
 * @version 2015-10-30
 */
@Controller
@RequestMapping(value = "${adminPath}/vouchertemplateexp/tVoucherTemplateExp")
public class TVoucherTemplateExpController extends BaseController {

	@Autowired
	private TVoucherTemplateService tVoucherTemplateService;

	@Autowired
	private TVoucherTemplateExpService tVoucherTemplateExpService;

	@ModelAttribute
	public TVoucherTemplateExp get(@RequestParam(required = false) String id) {
		TVoucherTemplateExp entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = tVoucherTemplateExpService.get(id);
		}
		if (entity == null) {
			entity = new TVoucherTemplateExp();
		}
		return entity;
	}

	@RequiresPermissions("vouchertemplateexp:tVoucherTemplateExp:view")
	@RequestMapping(value = { "list", "" })
	public String list(TVoucherTemplateExp tVoucherTemplateExp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TVoucherTemplateExp> page = tVoucherTemplateExpService.findPage(new Page<TVoucherTemplateExp>(request, response), tVoucherTemplateExp);
		model.addAttribute("page", page);
		return "modules/vouchertemplateexp/tVoucherTemplateExpList";
	}

	@RequiresPermissions("vouchertemplateexp:tVoucherTemplateExp:view")
	@RequestMapping(value = "form")
	public String form(TVoucherTemplateExp tVoucherTemplateExp, Model model) {
		model.addAttribute("tVoucherTemplateExp", tVoucherTemplateExp);
		return "modules/vouchertemplateexp/tVoucherTemplateExpForm";
	}

	@RequiresPermissions("vouchertemplateexp:tVoucherTemplateExp:edit")
	@RequestMapping(value = "save")
	public String save(TVoucherTemplateExp tVoucherTemplateExp, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tVoucherTemplateExp)) {
			return form(tVoucherTemplateExp, model);
		}
		tVoucherTemplateExpService.save(tVoucherTemplateExp);
		addMessage(redirectAttributes, "保存凭证模板摘要成功");
		return "redirect:" + Global.getAdminPath() + "/vouchertemplateexp/tVoucherTemplateExp/?repage";
	}

	@RequiresPermissions("vouchertemplateexp:tVoucherTemplateExp:edit")
	@RequestMapping(value = "delete")
	public String delete(TVoucherTemplateExp tVoucherTemplateExp, RedirectAttributes redirectAttributes) {
		tVoucherTemplateExpService.delete(tVoucherTemplateExp);
		addMessage(redirectAttributes, "删除凭证模板摘要成功");
		return "redirect:" + Global.getAdminPath() + "/vouchertemplateexp/tVoucherTemplateExp/?repage";
	}

	@RequestMapping(value = "getByTemplateId")
	@ResponseBody
	public Object getByTemplateId(String id) {
		TVoucherTemplate voucherTemplate = tVoucherTemplateService.get(id);
		List<TVoucherTemplateExp> voucherTemplateExps = tVoucherTemplateExpService.getByTempId(id);
		if (CollectionUtils.isNotEmpty(voucherTemplateExps)) {
			for (TVoucherTemplateExp exp : voucherTemplateExps) {
				DecimalFormat df = new DecimalFormat("#.00");
				String debit = "0.00";
				if( null != exp.getDebit()){
					debit = df.format(exp.getDebit());
				}
				String credit = "0.00";
				if( null != exp.getCredit()){
					credit = df.format(exp.getCredit());
				}
				
				if ("0.00".equals(debit) || ".00".equals(debit) ) {
					exp.setDebitStr("");
				} else {
					exp.setDebitStr(debit.replace(".", ""));
					exp.setCreditStr("");
				}

				if ("0.00".equals(credit) || ".00".equals(credit)) {
					exp.setCreditStr("");
				} else {
					exp.setDebitStr("");
					exp.setCreditStr(credit.replace(".", ""));
				}
			}
		}
		Collections.sort(voucherTemplateExps, new Comparator<TVoucherTemplateExp>() {
			@Override
			public int compare(TVoucherTemplateExp o1, TVoucherTemplateExp o2) {
				return Integer.parseInt(o1.getExpRowNumber()) - Integer.parseInt(o2.getExpRowNumber());
			}
		});

		// return voucherTemplates;
		return ImmutableMap.of("voucherTemplate", voucherTemplate, "voucherTemplateExps", voucherTemplateExps);
	}
}