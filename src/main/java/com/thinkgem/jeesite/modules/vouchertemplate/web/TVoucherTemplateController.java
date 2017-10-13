/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertemplate.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherExp;
import com.thinkgem.jeesite.modules.vouchertemplate.entity.TVoucherTemplate;
import com.thinkgem.jeesite.modules.vouchertemplate.service.TVoucherTemplateService;
import com.thinkgem.jeesite.modules.vouchertemplateexp.entity.TVoucherTemplateExp;
import com.thinkgem.jeesite.modules.vouchertemplateexp.service.TVoucherTemplateExpService;

/**
 * 凭证模板Controller
 * 
 * @author 凭证模板
 * @version 2015-10-30
 */
@Controller
@RequestMapping(value = "${adminPath}/vouchertemplate/tVoucherTemplate")
public class TVoucherTemplateController extends BaseController {

	@Autowired
	private TVoucherTemplateService tVoucherTemplateService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private TVoucherTemplateExpService tVoucherTemplateExpService;

	@Autowired
	private TBalanceService tBalanceService;
	
	@ModelAttribute
	public TVoucherTemplate get(@RequestParam(required = false) String id) {
		TVoucherTemplate entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = tVoucherTemplateService.get(id);
		}
		if (entity == null) {
			entity = new TVoucherTemplate();
		}
		return entity;
	}

	@RequiresPermissions("vouchertemplate:tVoucherTemplate:view")
	@RequestMapping(value = { "list", "" })
	public String list(TVoucherTemplate tVoucherTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TVoucherTemplate> page = tVoucherTemplateService.findPage(new Page<TVoucherTemplate>(request, response), tVoucherTemplate);
		model.addAttribute("page", page);
		return "modules/vouchertemplate/tVoucherTemplateList";
	}

	@RequiresPermissions("vouchertemplate:tVoucherTemplate:view")
	@RequestMapping(value = "form")
	public String form(TVoucherTemplate tVoucherTemplate, Model model) {
		model.addAttribute("tVoucherTemplate", tVoucherTemplate);
		return "modules/vouchertemplate/tVoucherTemplateForm";
	}

	@RequiresPermissions("vouchertemplate:tVoucherTemplate:edit")
	@RequestMapping(value = "save")
	public String save(TVoucherTemplate tVoucherTemplate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tVoucherTemplate)) {
			return form(tVoucherTemplate, model);
		}
		tVoucherTemplateService.save(tVoucherTemplate);
		addMessage(redirectAttributes, "保存凭证模板成功");
		return "redirect:" + Global.getAdminPath() + "/vouchertemplate/tVoucherTemplate/?repage";
	}

	@RequiresPermissions("vouchertemplate:tVoucherTemplate:edit")
	@RequestMapping(value = "delete")
	public String delete(TVoucherTemplate tVoucherTemplate, RedirectAttributes redirectAttributes) {
		tVoucherTemplateService.delete(tVoucherTemplate);
		addMessage(redirectAttributes, "删除凭证模板成功");
		return "redirect:" + Global.getAdminPath() + "/vouchertemplate/tVoucherTemplate/?repage";
	}

	@RequestMapping(value = "saveVoucherTemplate")
	@ResponseBody
	public Object saveVoucherTemplate(TVoucherTemplate voucherTemplate, String voucherExpStr, boolean saveCredit, String voucherTemplateId,HttpSession session) {
		boolean flag = true;
		String msg;
		List<TVoucherTemplateExp> voucherTemplateExpList = null;
		voucherExpStr = voucherExpStr.replace("&quot;", "\"");
		try {
			voucherTemplateExpList = objectMapper.readValue(voucherExpStr, new TypeReference<List<TVoucherTemplateExp>>() {
			});
		} catch (IOException e) {
			flag = false;
			msg = "凭证模板详情信息不合法，请核对检查";
			e.printStackTrace();
		}
		if (StringUtils.isBlank(voucherTemplateId)) { // 新建
			
			// 账期 有session获取
			TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息
				
			String fdbid =  customerinfo.getId();
			
			if (null != voucherTemplate && CollectionUtils.isNotEmpty(voucherTemplateExpList)) {
				// String idString = UUID.randomUUID().toString();
			//	String idString = IdGen.uuid();
			//	voucherTemplate.setId(idString);
				voucherTemplate.setTemplateTypeId("1");
				voucherTemplate.setIsNewRecord(true);
				voucherTemplate.setFdbid(fdbid);
				tVoucherTemplateService.save(voucherTemplate);
				String	idString = voucherTemplate.getId() + "";
				int row = 1;
				for (TVoucherTemplateExp voucherTempExp : voucherTemplateExpList) {
					String id = UUID.randomUUID().toString();
					voucherTempExp.setId(id);
					voucherTempExp.setTemplateId(idString);
					voucherTempExp.setExpRowNumber(row + "");
					voucherTempExp.setIsNewRecord(true);
					voucherTempExp.setCreateBy(null);// 当前登陆用户 TOOD
					voucherTempExp.setCreateDate(new Date());
					voucherTempExp.setFdbid(fdbid);
					if (!saveCredit) {
						if (null != voucherTempExp.getDebit() && voucherTempExp.getDebit().compareTo(BigDecimal.ZERO) == 1) {
							voucherTempExp.setDebit(BigDecimal.ZERO);
						} else {
							voucherTempExp.setDebit(null);
						}
						if (null != voucherTempExp.getCredit() && voucherTempExp.getCredit().compareTo(BigDecimal.ZERO) == 1) {
							voucherTempExp.setCredit(BigDecimal.ZERO);
						} else {
							voucherTempExp.setCredit(null);
						}
						/*
						 * voucherTempExp.setDebit(BigDecimal.ZERO);
						 * voucherTempExp.setCredit(BigDecimal.ZERO);
						 */
					}
					tVoucherTemplateExpService.save(voucherTempExp);
					row++;
				}
			} else {
				flag = false;
			}
		} else {// 更新
			/*
			 * List<TVoucherTemplateExp> voucherTemplates =
			 * tVoucherTemplateExpService .getByTempId(voucherTemplateId); if
			 * (CollectionUtils.isNotEmpty(voucherTemplates)) { for
			 * (TVoucherTemplateExp voucherTemplateExp : voucherTemplates) {
			 * tVoucherTemplateExpService.delete(voucherTemplateExp); } }
			 */
			if (!tVoucherTemplateExpService.delByTempId(voucherTemplateId)) {
				return ImmutableBiMap.of("suc", false);
			}
			voucherTemplate.setId(voucherTemplateId);
			voucherTemplate.setTemplateTypeId("1");
			voucherTemplate.setIsNewRecord(true);
			tVoucherTemplateService.update(voucherTemplate);
			int row = 1;
			for (TVoucherTemplateExp voucherTempExp : voucherTemplateExpList) {
				String id = UUID.randomUUID().toString();
				voucherTempExp.setId(id);
				voucherTempExp.setTemplateId(voucherTemplateId);
				voucherTempExp.setExpRowNumber(row + "");
				voucherTempExp.setIsNewRecord(true);
				voucherTempExp.setCreateBy(null);// 当前登陆用户 TOOD
				voucherTempExp.setCreateDate(new Date());
				if (!saveCredit) {
					voucherTempExp.setDebit(BigDecimal.ZERO);
					voucherTempExp.setCredit(BigDecimal.ZERO);
				}
				tVoucherTemplateExpService.save(voucherTempExp);
				row++;
			}
		}
		return ImmutableBiMap.of("suc", flag);
	}

	@RequestMapping(value = "findAllListByType")
	@ResponseBody
	public Object findAllListByType(String templateTypeId,HttpSession session) {
		TCustomer customerObject=(TCustomer)session.getAttribute("sessionCustomer");
		String fdbid=customerObject.getId();
		List<TVoucherTemplate> voucherTemplates = tVoucherTemplateService.getByTempTypeId(templateTypeId,fdbid);
		// 根据模板获取模板详情
		if (CollectionUtils.isNotEmpty(voucherTemplates)) {
			for (TVoucherTemplate voucherTemplate : voucherTemplates) {
				List<TVoucherTemplateExp> voucherTemplateExplList = tVoucherTemplateExpService.getByTempId(voucherTemplate.getId());
				if (CollectionUtils.isNotEmpty(voucherTemplateExplList)) {
					List<TVoucherTemplateExp> debitExps = new ArrayList<TVoucherTemplateExp>();
					List<TVoucherTemplateExp> creditExps = new ArrayList<TVoucherTemplateExp>();
					for (TVoucherTemplateExp voucherTemplateExp : voucherTemplateExplList) {

						if ((null != voucherTemplateExp.getDebit() && null == voucherTemplateExp.getCredit())
								|| (null != voucherTemplateExp.getDebit() && voucherTemplateExp.getDebit().compareTo(BigDecimal.ZERO) == 1)) {
							debitExps.add(voucherTemplateExp);
						} else {
							creditExps.add(voucherTemplateExp);
						}
					}
					voucherTemplate.setDebitExps(debitExps);
					voucherTemplate.setCreditExps(creditExps);
				}
			}
		}
		return voucherTemplates;
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "deleteVoucherTemplate")
	@ResponseBody
	public Object deleteVoucherTemplate(String id) {
		boolean flag = true;
		try {
			// TVoucherTemplate voucherTemplate =
			// tVoucherTemplateService.get(id);
			/*
			 * List<TVoucherTemplateExp> voucherTemplates =
			 * tVoucherTemplateExpService .getByTempId(id); if
			 * (CollectionUtils.isNotEmpty(voucherTemplates)) { for
			 * (TVoucherTemplateExp voucherTemplateExp : voucherTemplates) {
			 * tVoucherTemplateExpService.delete(voucherTemplateExp); } }
			 */
			if (tVoucherTemplateExpService.delByTempId(id)) {
				// tVoucherTemplateService.delete(voucherTemplate);
				flag = tVoucherTemplateService.deleteById(id);
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			return ImmutableBiMap.of("suc", flag);
		}
	}
	/**
	 * by zt  凭证模板
	 * @param tAmortize
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "modelset")
	@ResponseBody
	public Object modelset(String templateTypeId,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes,HttpSession session){
		TCustomer customerObject=(TCustomer)session.getAttribute("sessionCustomer");
		String fdbid=customerObject.getId();
		List<TVoucherTemplate> voucherTemplates = tVoucherTemplateService.findAllListByTypeAndFdbid(templateTypeId, fdbid);
		if (CollectionUtils.isNotEmpty(voucherTemplates)) {
			for (TVoucherTemplate vt : voucherTemplates) {
				List<TVoucherTemplateExp> exps = vt.getExps();
				List<TVoucherTemplateExp> debitExps = Lists.newArrayList();
				List<TVoucherTemplateExp> creditExps = Lists.newArrayList();
				for (TVoucherTemplateExp voucherTemplateExp : exps) {
					if ((null != voucherTemplateExp.getDebit() && null == voucherTemplateExp.getCredit())
							|| (null != voucherTemplateExp.getDebit() && voucherTemplateExp.getDebit().compareTo(BigDecimal.ZERO) == 1)) {
						debitExps.add(voucherTemplateExp);
					} else {
						creditExps.add(voucherTemplateExp);
					}
				}
				vt.setDebitExps(debitExps);
				vt.setCreditExps(creditExps);
			}
		}
		
		return voucherTemplates;
	}
	@RequestMapping(value = "modelsetPage")
	public String modelsetPage(){
		return "modules/vouchertemplate/modelset";
	}
	
	@RequestMapping(value = "tempateToVoucher")
	public ModelAndView tempateToVoucher(String id, HttpSession session) {
		ModelAndView model = new ModelAndView("modules/voucher/modelsetVoucher");
		TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息
		String fdbid=customerinfo.getId();
		String accountPeriod= customerinfo.getCurrentperiod();
		
		TVoucherTemplate TVoucherTemplate = tVoucherTemplateService.findAllListByIdAndFdbid(id,fdbid);
		List<TVoucherTemplateExp> listTVoucherTemplateExp = TVoucherTemplate.getExps();
		
		TVoucher voucher = new TVoucher();
		List<TVoucherExp> listVoucherExps = Lists.newArrayList();
		
		if(CollectionUtils.isNotEmpty(listTVoucherTemplateExp)){
			DecimalFormat df = new DecimalFormat("#.00");
			//return new BigDecimal(df.format(data));
			for(TVoucherTemplateExp voucherTemplateExp : listTVoucherTemplateExp){
				TVoucherExp voucherExp = new TVoucherExp();
				TBalance balance = tBalanceService.getLastAccountEndBalance(customerinfo.getId(), voucherTemplateExp.getAccountId(),accountPeriod);
				//陈明加的
				if(null != balance){
					//陈明加的
					if(!"0".equals(balance.getEndbalance())){
						BigDecimal	end=new BigDecimal(balance.getEndbalance());
						if("-1".equals(voucherTemplateExp.getDc())){
							balance.setEndbalance((end.divide(new BigDecimal("-1"),2,BigDecimal.ROUND_HALF_UP)).toString());
						}
					}					
					voucherExp.setBalance(balance);
				}
				
				voucherExp.setAccountId(voucherTemplateExp.getAccountId());
				voucherExp.setAccountName(voucherTemplateExp.getAccountName());
				BigDecimal creditBigDecimal = voucherTemplateExp.getCredit() == null ? BigDecimal.ZERO: voucherTemplateExp.getCredit();
				voucherExp.setCredit(creditBigDecimal.doubleValue());
				BigDecimal debitBigDecimal = voucherTemplateExp.getDebit() == null ? BigDecimal.ZERO : voucherTemplateExp.getDebit();
				voucherExp.setDebit(debitBigDecimal.doubleValue());
				voucherExp.setCurrentUser(voucherTemplateExp.getCurrentUser());
				voucherExp.setDc(voucherTemplateExp.getDc());
				voucherExp.setExp(voucherTemplateExp.getExp());
				voucherExp.setExpRowNumber(voucherTemplateExp.getExpRowNumber());
				voucherExp.setFdbid(voucherTemplateExp.getFdbid());
				voucherExp.setId(voucherTemplateExp.getId());
				voucherExp.setRemarks(voucherTemplateExp.getRemarks());
				//格式化数值
				voucherExp.setShowdebit(df.format(voucherExp.getDebit()));
				voucherExp.setShowcredit(df.format(voucherExp.getCredit()));
				listVoucherExps.add(voucherExp);
			}
		}
		
		model.addObject("voucher", voucher);
		model.addObject("voucherExp", listVoucherExps);

		model.addObject("title", voucher.getVoucherTitleNumber());

		// 日期 当前账期的最后一天
		// 当前账期
		String updateDate = customerinfo.getCurrentperiod();
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if (Integer.parseInt(updateDate.substring(4, 6)) == month) {
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String lastDayOfMonth = sdf.format(cal.getTime());
			model.addObject("date", lastDayOfMonth);
		} else {
			// 设置年份
			cal.set(Calendar.YEAR, Integer.parseInt(updateDate.substring(0, 4)));
			// 设置月份
			cal.set(Calendar.MONTH, Integer.parseInt(updateDate.substring(4, 6)) - 1);
			// 获取某月最大天数
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			// 设置日历中月份的最大天数
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String lastDayOfMonth = sdf.format(cal.getTime());
			model.addObject("date", lastDayOfMonth);
		}
		model.addObject("billinfosize", 0);
		User currentUser = UserUtils.getUser();
		model.addObject("user", currentUser);
		return model;
	}
}