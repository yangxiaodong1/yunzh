/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.billinfo.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.amortize.entity.TAmortize;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.billinfo.entity.IntelligentAccountContentResult;
import com.thinkgem.jeesite.modules.billinfo.entity.IntelligentAccountResult;
import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfo;
import com.thinkgem.jeesite.modules.billinfo.entity.Work;
import com.thinkgem.jeesite.modules.billinfo.service.IntelligentAccountThread;
import com.thinkgem.jeesite.modules.billinfo.service.TBillInfoService;
import com.thinkgem.jeesite.modules.billinfofeedback.entity.TBillInfoFeedBack;
import com.thinkgem.jeesite.modules.billinfofeedback.service.TBillInfoFeedBackService;
import com.thinkgem.jeesite.modules.billtype.entity.TBillType;
import com.thinkgem.jeesite.modules.billtype.service.TBillTypeService;
import com.thinkgem.jeesite.modules.billtype1.service.TBillType1Service;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucher.service.TVoucherService;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherExp;
import com.thinkgem.jeesite.modules.voucherexp.service.TVoucherExpService;

/**
 * 发票信息Controller
 * 
 * @author 发票信息
 * @version 2015-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/billinfo/tDealBillInfo")
public class TDealBillInfoController extends BaseController {

	@Autowired
	private TBillInfoService tBillInfoService;

	@Autowired
	private TVoucherService tVoucherService;

	@Autowired
	private TVoucherExpService voucherExpService;

	@Autowired
	private TBalanceService tBalanceService;

	@Autowired
	private TAccountService tAccountService;

	@Autowired
	private TBillInfoFeedBackService tBillInfoFeedBackService;
	
	@Autowired
	private TBillTypeService tBillTypeService;
	
	@Autowired
	private TBillType1Service tBillType1Service;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "uploadBillInfo")
	public ModelAndView uploadVoucher(HttpSession session, TBillInfo tBillInfo) {
		ModelAndView model = new ModelAndView("modules/billinfo/uploadBilliInfo");
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		String currentPeriod = tCustomer.getCurrentperiod();
		String year = currentPeriod.substring(0, 4);
		String month = currentPeriod.substring(4, 6);
		List<String> periodList = new ArrayList<String>();
		for (int i = Integer.parseInt(month); i > 0; i--) {
			String per = year + new DecimalFormat("00").format(i);
			periodList.add(per);
		}
		model.addObject("periodList", periodList);
		model.addObject("image_path", Global.getConfig("billinfo.image.url"));
		return model;
	}

	@RequestMapping(value = "queryUploadBillInfo")
	@ResponseBody
	public Object queryUploadBillInfo(HttpSession session, TBillInfo tBillInfo, boolean cancelFlag) {
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		tBillInfo.setCustomerId(tCustomer.getId());
		tBillInfo.setBillStatus(cancelFlag ? "4" : "");
		TBillInfo billInfo = new TBillInfo();
		billInfo.setUploadPeriod(tBillInfo.getUploadPeriod());
		billInfo.setCustomerId(tCustomer.getId());
		return ImmutableMap.of("list", tBillInfoService.queryUploadBillInfo(tBillInfo), "count", tBillInfoService.countBillInfo(billInfo, null));
	}

	@RequestMapping(value = "dealBilliInfo")
	public ModelAndView dealVoucher(HttpSession session, TBillInfo tBillInfo) {
		ModelAndView model = new ModelAndView("modules/billinfo/dealBilliInfo");
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		String currentPeriod = tCustomer.getCurrentperiod();
		String year = currentPeriod.substring(0, 4);
		String month = currentPeriod.substring(4, 6);
		List<String> periodList = new ArrayList<String>();
		for (int i = Integer.parseInt(month); i > 0; i--) {
			String per = year + new DecimalFormat("00").format(i);
			periodList.add(per);
		}
		model.addObject("periodList", periodList);
		model.addObject("typeList", tBillTypeService.findList(null));
		model.addObject("image_path", Global.getConfig("billinfo.image.url"));
		return model;
	}
	
	@RequestMapping(value = "feedBack")
	@ResponseBody
	public Object feedBack(TBillInfoFeedBack tBillInfoFeedBack){
		tBillInfoFeedBack.setIsNewRecord(true);
		tBillInfoFeedBackService.save(tBillInfoFeedBack);
		return true;
	}
	

	@RequestMapping(value = "queryDealBillInfo")
	@ResponseBody
	public Object queryDealBillInfo(HttpSession session, TBillInfo tBillInfo, boolean cancelFlag) {
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		tBillInfo.setCustomerId(tCustomer.getId());
		// 问题票据
		List<TBillInfo> errorBillInfos = tBillInfoService.queryDealBillInfo(tBillInfo, 0, true);

		// 查询普通票据 除 出租车 飞机 火车 特殊处理
		List<TBillInfo> billInfos = tBillInfoService.queryDealBillInfo(tBillInfo, 1, false);
		Map resultMap = new HashMap();
		resultMap.put("errorBillInfos", errorBillInfos);
		resultMap.put("billInfos", billInfos);

		// 出租类
		tBillInfo.setBillInfo("4");
		List<TBillInfo> taxiBillInfos = tBillInfoService.queryDealBillInfo(tBillInfo, 0, false);
		if (CollectionUtils.isNotEmpty(taxiBillInfos) && taxiBillInfos.size() > 0) {
			// 组装出租类默认信息
			TBillInfo taxiBillInfo = new TBillInfo();
			BigDecimal total = BigDecimal.ZERO;
			StringBuffer sb = new StringBuffer();
			for (TBillInfo billInfo : taxiBillInfos) {
				if(null != billInfo && null != billInfo.getTotalPrice()){
					total = total.add(new BigDecimal(billInfo.getTotalPrice()));
				}
				sb.append(billInfo.getId() + ",");
			}
			taxiBillInfo.setId(sb.toString().substring(0, sb.toString().length() - 1));
			taxiBillInfo.setImageUrl("/images/taxi.png");
			taxiBillInfo.setAmount(total.toString());
			resultMap.put("taxiBillInfo", taxiBillInfo);
			resultMap.put("taxiMap", taxiBillInfos);
		}

		// 飞机类
		tBillInfo.setBillInfo("3");
		List<TBillInfo> planBillInfos = tBillInfoService.queryDealBillInfo(tBillInfo, 0, false);
		if (CollectionUtils.isNotEmpty(planBillInfos) && planBillInfos.size() > 0) {
			// 组装出租类默认信息
			TBillInfo taxiBillInfo = new TBillInfo();
			BigDecimal total = BigDecimal.ZERO;
			StringBuffer sb = new StringBuffer();
			for (TBillInfo billInfo : planBillInfos) {
				if(null != billInfo && null != billInfo.getTotalPrice()){
					total = total.add(new BigDecimal(billInfo.getTotalPrice()));
				}
				sb.append(billInfo.getId() + ",");
			}
			taxiBillInfo.setId(sb.toString().substring(0, sb.toString().length() - 1));
			taxiBillInfo.setImageUrl("/images/plane.png");
			taxiBillInfo.setAmount(total.toString());
			resultMap.put("planeBillInfo", taxiBillInfo);
			resultMap.put("planeMap", planBillInfos);
		}

		// 火车类
		tBillInfo.setBillInfo("2");
		List<TBillInfo> trainBillInfos = tBillInfoService.queryDealBillInfo(tBillInfo, 0, false);
		if (CollectionUtils.isNotEmpty(trainBillInfos) && trainBillInfos.size() > 0) {
			// 组装出租类默认信息
			TBillInfo taxiBillInfo = new TBillInfo();
			BigDecimal total = BigDecimal.ZERO;
			StringBuffer sb = new StringBuffer();
			for (TBillInfo billInfo : trainBillInfos) {
				if(null != billInfo && null != billInfo.getTotalPrice()){
					total = total.add(new BigDecimal(billInfo.getTotalPrice()));
				}
				sb.append(billInfo.getId() + ",");
			}
			taxiBillInfo.setId(sb.toString().substring(0, sb.toString().length() - 1));
			taxiBillInfo.setImageUrl("/images/train.png");
			taxiBillInfo.setAmount(total.toString());
			resultMap.put("trainBillInfo", taxiBillInfo);
			resultMap.put("trainMap", trainBillInfos);
		}

		return resultMap;
	}

	/**
	 * 关联票据
	 * 
	 * @param id
	 * @param accountPeriod
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "linkedNotes")
	public ModelAndView linkedNotes(String id, String accountPeriod, HttpSession session) {
		ModelAndView model = new ModelAndView("modules/billinfo/linkednote");
		TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息

		TVoucher voucher = tVoucherService.get(id);
		List<TVoucherExp> voucherExps = voucherExpService.findbytvid(id);
		if (CollectionUtils.isNotEmpty(voucherExps)) {
			DecimalFormat df = new DecimalFormat("#.00");
			for (TVoucherExp voucherExp : voucherExps) {
				TBalance balance = tBalanceService.getLastAccountEndBalance(customerinfo.getId(), voucherExp.getAccountId(), accountPeriod);
				if (null != balance) {
					BigDecimal end = new BigDecimal(balance.getEndbalance());
					if ("-1".equals(voucherExp.getDc())) {
						balance.setEndbalance((end.divide(new BigDecimal("-1"), 2, BigDecimal.ROUND_HALF_UP)).toString());
					}
					voucherExp.setBalance(balance);
				}
				// 格式化数值
				voucherExp.setShowdebit(df.format(voucherExp.getDebit()));
				voucherExp.setShowcredit(df.format(voucherExp.getCredit()));
			}
		}
		if (CollectionUtils.isEmpty(voucherExps) || voucherExps.size() < 4) {
			for (int i = voucherExps.size(); i < 4; i++) {
				TVoucherExp voucherExp = new TVoucherExp();
				voucherExps.add(voucherExp);
			}
		}
		model.addObject("voucher", voucher);
		model.addObject("voucherExp", voucherExps);
		model.addObject("title", voucher.getVoucherTitleNumber());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(voucher.getAccountDate());
		model.addObject("date", lastDayOfMonth);
		model.addObject("billinfosize", voucher.getRecieptNumber());
		model.addObject("user", voucher.getUserName());
		return model;
	}

	/**
	 * 智能结账进度处理
	 * 
	 * @param session
	 * @param tBillInfo
	 * @return
	 */
	@RequestMapping(value = "intelligentAccount")
	@ResponseBody
	public Object intelligentAccount(HttpSession session, TBillInfo tBillInfo, String uploadPeriod) {
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		String customerId = tCustomer.getId();
		tBillInfo.setUploadPeriod(uploadPeriod);
		tBillInfo.setCustomerId(customerId);
		tBillInfo.setBillStatus(" != 3"); // todo 只查未做账的
		List<TBillInfo> billInfos = tBillInfoService.queryDealBillInfo(tBillInfo, 0, false);
		if (CollectionUtils.isNotEmpty(billInfos)) {
			int maxTitle = tVoucherService.getMaxByAccountPeriod(uploadPeriod, customerId);
			Thread thread = new IntelligentAccountThread(new Work(session, billInfos, uploadPeriod, maxTitle, tBalanceService, tAccountService,tBillTypeService,tBillType1Service));
			thread.start();
		}
		return true;
	}

	/**
	 * 跳转到处理进度页面
	 * 
	 * @param session
	 * @param tBillInfo
	 * @param uploadPeriod
	 * @return
	 */
	@RequestMapping(value = "loading")
	public ModelAndView loading(HttpSession session, TBillInfo tBillInfo, String uploadPeriod) {
		ModelAndView model = new ModelAndView("modules/billinfo/loading");
		if (null == tBillInfo) {
			tBillInfo = new TBillInfo();
		}
		tBillInfo.setUploadPeriod(uploadPeriod);
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		String customerId = tCustomer.getId();
		tBillInfo.setCustomerId(customerId);
		tBillInfo.setBillStatus(" != 3"); // todo 只查未做账的
		// TODO优化
		List<TBillInfo> billInfos = tBillInfoService.queryDealBillInfo(tBillInfo, 0, false);
		model.addObject("total", billInfos.size());
		model.addObject("uploadPeriod", uploadPeriod);
		return model;
	}

	/**
	 * 获取处理进度
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "getIntelligentAccountSchedule")
	@ResponseBody
	public Object getIntelligentAccountSchedule(HttpSession session) {
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		int loading = (Integer) session.getAttribute(session.getId() + tCustomer.getId() + "loading");
		return loading;
	}

	/**
	 * 智能结账结果页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "intelligentAccountResult")
	public ModelAndView intelligentAccountResult(HttpSession session,String uploadPeriod) {
		ModelAndView model = new ModelAndView("modules/billinfo/intelligenaccountresult");
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		String customerId = tCustomer.getId();

		model.addObject("result", session.getAttribute(session.getId() + customerId + "result"));
		model.addObject("failBillids", session.getAttribute(session.getId() + customerId + "failBillids"));

		User currentUser = UserUtils.getUser();
		model.addObject("user", currentUser);
		model.addObject("uploadPeriod", uploadPeriod);
		return model;
	}

	@RequestMapping(value = "intelligentAccountBill")
	@ResponseBody
	public Object intelligentAccountBill(HttpSession session, String id) {
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		List<TBillInfo> billInfos = (List<TBillInfo>) session.getAttribute(session.getId() + tCustomer.getId() + "bills");
		if (StringUtils.isNotBlank(id)) {
			List<IntelligentAccountResult> resultList = (List<IntelligentAccountResult>) session.getAttribute(session.getId() + tCustomer.getId() + "result");
			if (CollectionUtils.isNotEmpty(resultList) && StringUtils.isNotBlank(id)) {
				for (int i = 0; i < resultList.size(); i++) {
					TBillInfo bill = resultList.get(i).getBillInfo();
					if (id.equals(bill.getId())) {
						session.setAttribute(session.getId() + tCustomer.getId() + "result", resultList);
						break;
					}
				}
			}
		}
		return billInfos;
		// return (List<TBillInfo>) session.getAttribute(session.getId()+
		// tCustomer.getId() + "bills");
	}

	/**
	 * 修改票据信息
	 * 
	 * @param id
	 * @param field
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "uploadBill")
	@ResponseBody
	public Object uploadBill(String id, String field, String value) {
		return ImmutableMap.of("suc",tBillInfoService.updateBill(id, field, value) > 0,"bean",tBillInfoService.get(id));
	}

	@RequestMapping(value = "deletaIntelligentAccountResult")
	@ResponseBody
	public Object deletaIntelligentAccountResult(HttpSession session, String id) {
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		String customerId = tCustomer.getId();
		List<IntelligentAccountResult> resultList = (List<IntelligentAccountResult>) session.getAttribute(session.getId() + customerId + "result");
		if (CollectionUtils.isNotEmpty(resultList) && StringUtils.isNotBlank(id)) {
			for (int i = 0; i < resultList.size(); i++) {
				TBillInfo bill = resultList.get(i).getBillInfo();
				if (id.equals(bill.getId())) {
					resultList.remove(i);
					session.setAttribute(session.getId() + customerId + "result", resultList);
					break;
				}
			}
		}
		return true;
	}

	@RequestMapping(value = "saveIntelligent")
	public ModelAndView saveIntelligent(HttpSession session) {
		ModelAndView model = new ModelAndView("modules/billinfo/intelligenaccountresulterror");
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		String customerId = tCustomer.getId();
		List<IntelligentAccountResult> resultList = (List<IntelligentAccountResult>) session.getAttribute(session.getId() + customerId + "result");
		if (CollectionUtils.isNotEmpty(resultList)) {
			tVoucherService.saveIntelligenAccount(resultList, tCustomer);
		}
		String failBillids = (String) session.getAttribute(session.getId() + tCustomer.getId() + "failBillids");
		List<String> billList = Arrays.asList(failBillids.split(","));
		List<TBillInfo> errorBillInfos = new ArrayList<TBillInfo>();
		for (String id : billList) {
			errorBillInfos.add(tBillInfoService.get(id));
		}
		model.addObject("failBillids", errorBillInfos);
		model.addObject("image_path", Global.getConfig("billinfo.image.url"));
		return model;
	}

	@RequestMapping(value = "queryIntelligenError")
	@ResponseBody
	public Object queryIntelligenError(HttpSession session, TBillInfo tBillInfo, boolean cancelFlag) {
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");

		String failBillids = (String) session.getAttribute(session.getId() + tCustomer.getId() + "failBillids");
		List<String> billList = Arrays.asList(failBillids.split(","));
		List<TBillInfo> errorBillInfos = new ArrayList<TBillInfo>();
		for (String id : billList) {
			errorBillInfos.add(tBillInfoService.get(id));
		}

		return ImmutableMap.of("errorBillInfos", errorBillInfos);
	}

	/**
	 * 智能结账结果页 编辑
	 * 
	 * @param id
	 * @param accountPeriod
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "modifyVoucher")
	public ModelAndView modifuVoucher(String id, HttpSession session) {
		ModelAndView model = new ModelAndView("modules/billinfo/intelligenModifyVoucher");
		TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息
		IntelligentAccountResult editAccountResult = new IntelligentAccountResult();
		List<IntelligentAccountResult> resultList = (List<IntelligentAccountResult>) session.getAttribute(session.getId() + customerinfo.getId() + "result");
		if (CollectionUtils.isNotEmpty(resultList) && StringUtils.isNotBlank(id)) {
			for (int i = 0; i < resultList.size(); i++) {
				TBillInfo bill = resultList.get(i).getBillInfo();
				if (id.equals(bill.getId())) {
					editAccountResult = resultList.get(i);
					break;
				}
			}
		}
		model.addObject("info", editAccountResult);
		model.addObject("billinfosize", 1);
		User currentUser = UserUtils.getUser();
		model.addObject("user", currentUser);
		return model;
	}

	@RequestMapping(value = "modify")
	@ResponseBody
	public Object modify(String id, TVoucher voucher, String voucherExpStr, String billinfoid, TAmortize amortize, String debitAccountId, String creditAccountId,
			HttpSession session) {
		boolean flag = true;
		String msg;
		voucherExpStr = voucherExpStr.replace("&quot;", "\"");
		List<TVoucherExp> voucherExpList = null;
		try {
			voucherExpList = objectMapper.readValue(voucherExpStr, new TypeReference<List<TVoucherExp>>() {
			});
		} catch (IOException e) {
			flag = false;
			msg = "凭证详情信息不合法，请核对检查";
			e.printStackTrace();
		}

		TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息
		IntelligentAccountResult editAccountResult = new IntelligentAccountResult();
		List<IntelligentAccountResult> resultList = (List<IntelligentAccountResult>) session.getAttribute(session.getId() + customerinfo.getId() + "result");

		int index = 0;
		if (CollectionUtils.isNotEmpty(resultList) && StringUtils.isNotBlank(id)) {
			for (int i = 0; i < resultList.size(); i++) {
				TBillInfo bill = resultList.get(i).getBillInfo();
				if (id.equals(bill.getId())) {
					editAccountResult = resultList.get(i);
					index = i;
					break;
				}
			}
		}

		editAccountResult.setAccountDate(voucher.getAccountDate());
		editAccountResult.setTotalAmount(BigDecimal.valueOf(voucher.getTotalAmount()));
		editAccountResult.setTitle(Integer.valueOf(voucher.getVoucherTitleNumber()));

		List<IntelligentAccountContentResult> intelligentAccountContentResults = new ArrayList<IntelligentAccountContentResult>();
		int rowNum = 0;
		DecimalFormat df = new DecimalFormat("#.00");

		for (TVoucherExp voucherExp : voucherExpList) {
			IntelligentAccountContentResult intelligentAccountContentResult = new IntelligentAccountContentResult();
			intelligentAccountContentResult.setSeq(rowNum);
			intelligentAccountContentResult.setSummary(voucherExp.getExp());
			intelligentAccountContentResult.setSubject(voucherExp.getAccountId());
			intelligentAccountContentResult.setDebit(BigDecimal.valueOf(voucherExp.getDebit()));
			intelligentAccountContentResult.setCredit(BigDecimal.valueOf(voucherExp.getCredit()));
			if (intelligentAccountContentResult.getDebit().doubleValue() > 0) {
				intelligentAccountContentResult.setDc("1");
			}

			Date date = null;
			try {
				date = new SimpleDateFormat("yyyyMM").parse(editAccountResult.getAccountDateString());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			TBalance balance = tBalanceService.getLastAccountEndBalance(customerinfo.getId(), intelligentAccountContentResult.getSubject(), new SimpleDateFormat("yyyyMM").format(date));
			if (null != balance) {
				BigDecimal end = new BigDecimal(balance.getEndbalance());
				if (intelligentAccountContentResult.getDebit().doubleValue() > 0) {
					balance.setEndbalance((end.divide(new BigDecimal("-1"), 2, BigDecimal.ROUND_HALF_UP)).toString());
				}
				intelligentAccountContentResult.setBalance(balance);
			}
			// 格式化数值
			intelligentAccountContentResult.setShowdebit(df.format(intelligentAccountContentResult.getDebit()));
			intelligentAccountContentResult.setShowcredit(df.format(intelligentAccountContentResult.getCredit()));
			intelligentAccountContentResult.setAccount(tAccountService.get(intelligentAccountContentResult.getSubject()));
			rowNum++;
			intelligentAccountContentResults.add(intelligentAccountContentResult);
		}
		
		if(null != amortize){
			editAccountResult.setAmortize(amortize);
			editAccountResult.setDebitAccountId(debitAccountId);
			editAccountResult.setCreditAccountId(creditAccountId);
		}
		
		editAccountResult.setIntelligentAccountContentResults(intelligentAccountContentResults);
		resultList.set(index, editAccountResult);
		session.setAttribute(session.getId() + customerinfo.getId() + "result", resultList);

		return ImmutableMap.of("suc", flag);
	}

	
	
}