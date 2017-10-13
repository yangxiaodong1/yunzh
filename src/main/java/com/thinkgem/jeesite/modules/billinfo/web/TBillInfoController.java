/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.billinfo.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfo;
import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfoToWeb;
import com.thinkgem.jeesite.modules.billinfo.service.TBillInfoService;
import com.thinkgem.jeesite.modules.billtype.entity.TBillType;
import com.thinkgem.jeesite.modules.billtype.service.TBillTypeService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.intelligenttemplate.entity.TIntelligentTemplate;
import com.thinkgem.jeesite.modules.intelligenttemplate.service.TIntelligentTemplateService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;

/**
 * 发票信息Controller
 * 
 * @author 发票信息
 * @version 2015-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/billinfo/tBillInfo")
public class TBillInfoController extends BaseController {


	@Autowired
	private TBillInfoService tBillInfoService;

	@Autowired
	private TBillTypeService billTypeService;

	@Autowired
	private TIntelligentTemplateService tIntelligentTemplateService;

	@Autowired
	private TAccountService tAccountService;

	@ModelAttribute
	public TBillInfo get(@RequestParam(required = false) String id) {
		TBillInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = tBillInfoService.get(id);
		}
		if (entity == null) {
			entity = new TBillInfo();
		}
		return entity;
	}

	@RequiresPermissions("billinfo:tBillInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(TBillInfo tBillInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TBillInfo> page = tBillInfoService.findPage(new Page<TBillInfo>(request, response), tBillInfo);
		model.addAttribute("page", page);
		return "modules/billinfo/tBillInfoList";
	}

	@RequiresPermissions("billinfo:tBillInfo:view")
	@RequestMapping(value = "form")
	public String form(TBillInfo tBillInfo, Model model) {
		model.addAttribute("tBillInfo", tBillInfo);
		return "modules/billinfo/tBillInfoForm";
	}

	@RequiresPermissions("billinfo:tBillInfo:edit")
	@RequestMapping(value = "save")
	public String save(TBillInfo tBillInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tBillInfo)) {
			return form(tBillInfo, model);
		}
		tBillInfoService.save(tBillInfo);
		addMessage(redirectAttributes, "保存发票信息成功");
		return "redirect:" + Global.getAdminPath() + "/billinfo/tBillInfo/?repage";
	}

	@RequiresPermissions("billinfo:tBillInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(TBillInfo tBillInfo, RedirectAttributes redirectAttributes) {
		tBillInfoService.delete(tBillInfo);
		addMessage(redirectAttributes, "删除发票信息成功");
		return "redirect:" + Global.getAdminPath() + "/billinfo/tBillInfo/?repage";
	}

	@RequestMapping(value = "updateBillInfoError")
	@ResponseBody
	public Object updateBillInfoError(String id, String errorReason) {
		TBillInfo billInfo = tBillInfoService.get(id);
		if (null != billInfo) {
			billInfo.setErrorReason(StringUtils.isNotBlank(errorReason) ? errorReason : "");
			billInfo.setBillStatus("5");
			int re = tBillInfoService.update(billInfo);
			if (re > 0) {
				return ImmutableMap.of("suc", true);
			} else {
				return ImmutableMap.of("suc", false, "msg", "标错失败");
			}
		} else {
			return ImmutableMap.of("suc", false, "msg", "发票信息不存在");
		}
	}

	@RequestMapping(value = "updateBillInfoScrap")
	@ResponseBody
	public Object updateBillInfoScrap(String id, String scrapReason) {
		TBillInfo billInfo = tBillInfoService.get(id);
		if (null != billInfo) {
			billInfo.setCancelReason(StringUtils.isNotBlank(scrapReason) ? scrapReason : "");
			billInfo.setBillStatus("4");
			int re = tBillInfoService.update(billInfo);
			if (re > 0) {
				return ImmutableMap.of("suc", true);
			} else {
				return ImmutableMap.of("suc", false, "msg", "废弃失败");
			}
		} else {
			return ImmutableMap.of("suc", false, "msg", "发票信息不存在");
		}
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "billInfolist")
	public String billInfolist(TBillInfo tBillInfo, String showType, String orderBy, Model model, HttpSession session) {
		User currentUser = UserUtils.getUser();
		model.addAttribute("user", currentUser);
		// 日期 当前账期的最后一天
		// 当前账期
		TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息
		// String updateDate = "201511";
		String updateDate = customerinfo.getCurrentperiod();
		Calendar cal = Calendar.getInstance();
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
		model.addAttribute("date", lastDayOfMonth);
		model.addAttribute("year",updateDate.substring(0, 4));
		model.addAttribute("month", updateDate.substring(4, 6));
		return "/modules/billinfo/list";
	}

	@RequestMapping(value = "billInfosAjax")
	@ResponseBody
	public Object billInfosAjax(HttpServletRequest request, HttpServletResponse response, TBillInfo tBillInfo, String showType, String orderBy, HttpSession session) {
		if (StringUtils.isBlank(showType)) {
			showType = "1";
		}

		// 公司名称
		// 上传账期session获取 
		TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息
		String uploadPeriod = customerinfo.getCurrentperiod();
		String customerId = customerinfo.getId();
		
		tBillInfo.setUploadPeriod(uploadPeriod);
		tBillInfo.setCustomerId(customerId);
		
		List<TBillInfo> billInfos = tBillInfoService.findListWithOrderBy(tBillInfo, orderBy);
		if ("2".equals(showType)) {
			List<String> typeId = new ArrayList<String>();
			Map<String, List<TBillInfo>> maps = new HashMap<String, List<TBillInfo>>();
			Map<String, Double> mapsAmountCount = new HashMap<String, Double>();
			Map<String, Double> mapsTaxCount = new HashMap<String, Double>();
			Map<String, Double> mapsTotalPriceCount = new HashMap<String, Double>();

			for (TBillInfo billInfo : billInfos) {
				String type = billInfo.getTBId();
				// 对发票状态的判断
				if ("0".equals(billInfo.getBillStatus())) {
					billInfo.setStatusName("待审验");
				}
				if ("1".equals(billInfo.getBillStatus())) {
					billInfo.setStatusName("退回");
				}
				if ("2".equals(billInfo.getBillStatus())) {
					billInfo.setStatusName("已审验");
				}
				if ("3".equals(billInfo.getBillStatus())) {
					billInfo.setStatusName("已做账 ");
				}
				if ("4".equals(billInfo.getBillStatus())) {
					billInfo.setScrap(false);
					billInfo.setStatusName("作废");
				}
				if ("5".equals(billInfo.getBillStatus())) {
					billInfo.setError(false);
					billInfo.setStatusName("错误");
				}

				// 同一类型发票信息
				List<TBillInfo> mapsBill = maps.get(type);
				if (null == mapsBill) {
					mapsBill = new ArrayList<TBillInfo>();
				}
				mapsBill.add(billInfo);
				maps.put(type, mapsBill);

				// 同一类型金额
				Double amountCountInteger = mapsAmountCount.get(type);
				if (null == amountCountInteger) {
					amountCountInteger = new Double(0);
				}
				if (StringUtils.isNoneBlank(billInfo.getAmount())) {
					amountCountInteger = amountCountInteger + Double.parseDouble(billInfo.getAmount());
				}
				mapsAmountCount.put(type, amountCountInteger);

				// 同一类型税额
				Double taxCountInteger = mapsTaxCount.get(type);
				if (null == taxCountInteger) {
					taxCountInteger = new Double(0);
				}
				if (StringUtils.isNoneBlank(billInfo.getTax())) {
					taxCountInteger = taxCountInteger + Double.parseDouble(billInfo.getTax());
				}
				mapsTaxCount.put(type, taxCountInteger);

				// 同一类型价格合计
				Double totalPriceCountCountInteger = mapsTotalPriceCount.get(type);
				if (null == totalPriceCountCountInteger) {
					totalPriceCountCountInteger = new Double(0);
				}
				if (StringUtils.isNoneBlank(billInfo.getTotalPrice())) {
					totalPriceCountCountInteger = totalPriceCountCountInteger + Double.parseDouble(billInfo.getTotalPrice());
				}
				mapsTotalPriceCount.put(type, totalPriceCountCountInteger);

				// 发票类型
				if (!typeId.contains(type)) {
					typeId.add(type);
				}
			}
			List<TBillInfoToWeb> tBillInfoToWebs = new ArrayList<TBillInfoToWeb>();
			// 查询发票分类 并保存数据结构 给前台
			if (CollectionUtils.isNotEmpty(typeId)) {
				int index = 0;
				for (String id : typeId) {
					TBillInfoToWeb billInfoToWeb = new TBillInfoToWeb();
					TBillType billType = billTypeService.get(id);
					billInfoToWeb.setBillType(billType);
					billInfoToWeb.setBillInfoList(maps.get(id));
					billInfoToWeb.setAmountCount(mapsAmountCount.get(id).toString());
					billInfoToWeb.setTaxCount(mapsTaxCount.get(id).toString());
					billInfoToWeb.setTotalPriceCount(mapsTotalPriceCount.get(id).toString());
					billInfoToWeb.setIndex(index);
					index++;
					tBillInfoToWebs.add(billInfoToWeb);
				}
			}
			return ImmutableMap.of("showType", showType, "result", tBillInfoToWebs);
		}
		if ("1".equals(showType)) {
			return ImmutableMap.of("showType", showType, "result", billInfos);
		}
		return null;
	}

	@RequestMapping(value = "getBillInfo")
	@ResponseBody
	public TBillInfo getBillInfo(@RequestParam(required = false) String id) {
		TBillInfo billInfo = null;
		if (StringUtils.isNotBlank(id)) {
			billInfo = tBillInfoService.get(id);
		}
		if (billInfo == null) {
			billInfo = new TBillInfo();
		} else {
			if ("0".equals(billInfo.getBillStatus())) {
				billInfo.setStatusName("待审验");
			}
			if ("1".equals(billInfo.getBillStatus())) {
				billInfo.setStatusName("退回");
			}
			if ("2".equals(billInfo.getBillStatus())) {
				billInfo.setStatusName("已审验");
			}
			if ("3".equals(billInfo.getBillStatus())) {
				billInfo.setStatusName("已做账 ");
			}
			if ("4".equals(billInfo.getBillStatus())) {
				billInfo.setScrap(false);
				billInfo.setStatusName("作废");
			}
			if ("5".equals(billInfo.getBillStatus())) {
				billInfo.setError(false);
				billInfo.setStatusName("错误");
			}
			TBillType billType = billTypeService.get(billInfo.getTBId());
			billInfo.settBIdName(billType.getBillTypeName());
		}

		return billInfo;
	}

	@RequestMapping(value = "billInfoPeriod")
	@ResponseBody
	public boolean billInfoPeriod(String id, String endTime) {
		TBillInfo billInfo = tBillInfoService.get(id);
		try {
			TBillInfo copyBillInfo = (TBillInfo) billInfo.clone();
			billInfo.setBillStatus("6");
			copyBillInfo.setBillStatus("7");
			copyBillInfo.setId(null);
			endTime = endTime.replace("-", "");
			copyBillInfo.setUploadPeriod(endTime);
			copyBillInfo.setIsNewRecord(true);
			tBillInfoService.update(billInfo);
			tBillInfoService.save(copyBillInfo);
			return true;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@RequestMapping(value = "findIntelligentTemplate")
	@ResponseBody
	public Object findIntelligentTemplate(TIntelligentTemplate tIntelligentTemplate) {
		// tIntelligentTemplate.setCompany("1");
		// tIntelligentTemplate.setBillType("1");
		TBillType billType = null;
		if (null != tIntelligentTemplate && StringUtils.isNotBlank(tIntelligentTemplate.getBillType())) {
			billType = billTypeService.get(tIntelligentTemplate.getBillType());
		}

		List<TIntelligentTemplate> intelligentTemplates = tIntelligentTemplateService.findList(tIntelligentTemplate);

		Collections.sort(intelligentTemplates, new Comparator<TIntelligentTemplate>() {
			@Override
			public int compare(TIntelligentTemplate b1, TIntelligentTemplate b2) {
				return b2.getCount() - b1.getCount();
			}
		});

		// return intelligentTemplates;
		return ImmutableMap.of("billType", billType, "intelligentTemplates", intelligentTemplates);
	}

	@RequestMapping(value = "getBillInfoWithIntelligent")
	@ResponseBody
	public Object getBillInfoWithIntelligent(String id, String intelligentid) {
		TBillInfo billInfo = tBillInfoService.get(id);
		if (null != billInfo) {
			TIntelligentTemplate intelligentTemplate = tIntelligentTemplateService.get(intelligentid);
			billInfo.setAmount(billInfo.getAmount().replace(".", ""));
			if (StringUtils.isNotBlank(intelligentid)) {
				billInfo.setDebitAccount(tAccountService.get(intelligentTemplate.getDebitAccount()));
				billInfo.setCreditAccount(tAccountService.get(intelligentTemplate.getCreditAccount()));
			}
		}
		return billInfo;
	}
	
}