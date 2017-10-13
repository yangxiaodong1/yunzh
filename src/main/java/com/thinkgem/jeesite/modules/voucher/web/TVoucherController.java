/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucher.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.google.common.collect.Table;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.amortize.entity.TAmortize;
import com.thinkgem.jeesite.modules.amortize.service.TAmortizeService;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfo;
import com.thinkgem.jeesite.modules.billinfo.service.TBillInfoService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.customersettlerecord.entity.TCustomerSettleRecord;
import com.thinkgem.jeesite.modules.customersettlerecord.service.TCustomerSettleRecordService;
import com.thinkgem.jeesite.modules.settleAccounts.entity.TSettle;
import com.thinkgem.jeesite.modules.stamp.Style;
import com.thinkgem.jeesite.modules.stamp.TableBuilder;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucher.service.TVoucherService;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherExp;
import com.thinkgem.jeesite.modules.voucherexp.service.TVoucherExpService;
import com.thinkgem.jeesite.modules.voucherexp.web.NumberToCN;
import com.thinkgem.jeesite.modules.voucherexp.web.TVoucherExpController;
//import com.thinkgem.jeesite.modules.amortize.entity.TAmortize;
//import com.thinkgem.jeesite.modules.amortize.service.TAmortizeService;

/**
 * 记账凭证Controller
 * 
 * @author 记账凭证
 * @version 2015-10-30
 */
@Controller
@RequestMapping(value = "${adminPath}/voucher/tVoucher")
public class TVoucherController extends BaseController {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private TVoucherService tVoucherService;

	@Autowired
	private TVoucherExpService voucherExpService;

	@Autowired
	private TBillInfoService tBillInfoService;

	@Autowired
	private TAccountService tAccountService;

	@Autowired
	private TAmortizeService tAmortizeService;

	@Autowired
	private TBalanceService tBalanceService;

	@Autowired
	private TCustomerService customerService;

	@Autowired
	private TableBuilder tBuilder;

	@Autowired
	private TVoucherExpController tVoucherExpController;

	@Autowired
	private TCustomerSettleRecordService tCustomerSettleRecordService;

	@ModelAttribute
	public TVoucher get(@RequestParam(required = false) String id) {
		TVoucher entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = tVoucherService.get(id);
		}
		if (entity == null) {
			entity = new TVoucher();
		}
		return entity;
	}

	@RequiresPermissions("voucher:tVoucher:view")
	@RequestMapping(value = { "list", "" })
	public String list(TVoucher tVoucher, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<TVoucher> page = tVoucherService.findPage(new Page<TVoucher>(
				request, response), tVoucher);
		model.addAttribute("page", page);
		model.addAttribute("tVoucher", tVoucher);
		return "modules/voucher/tVoucherList";
	}

	@RequiresPermissions("voucher:tVoucher:view")
	@RequestMapping(value = "form")
	public String form(TVoucher tVoucher, Model model) {
		model.addAttribute("tVoucher", tVoucher);
		return "modules/voucher/tVoucherForm";
	}

	@RequiresPermissions("voucher:tVoucher:edit")
	@RequestMapping(value = "save")
	public String save(TVoucher tVoucher, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tVoucher)) {
			return form(tVoucher, model);
		}
		tVoucherService.save(tVoucher);
		addMessage(redirectAttributes, "保存记账凭证成功");
		return "redirect:" + Global.getAdminPath()
				+ "/voucher/tVoucher/?repage";
	}

	@RequiresPermissions("voucher:tVoucher:edit")
	@RequestMapping(value = "delete")
	public String delete(TVoucher tVoucher,
			RedirectAttributes redirectAttributes) {
		tVoucherService.delete(tVoucher);
		addMessage(redirectAttributes, "删除记账凭证成功");
		return "redirect:" + Global.getAdminPath()
				+ "/voucher/tVoucher/?repage";
	}

	@RequestMapping(value = "saveVoucher")
	@ResponseBody
	public Object saveVoucher(TVoucher voucher, String voucherExpStr,
			String billinfoid, TAmortize amortize, String debitAccountId,
			String creditAccountId, HttpServletRequest request,
			HttpSession session) {
		
		boolean flag = true;
		String msg;
		voucherExpStr = voucherExpStr.replace("&quot;", "\"");
		List<TVoucherExp> voucherExpList = null;
		try {
			voucherExpList = objectMapper.readValue(voucherExpStr,
					new TypeReference<List<TVoucherExp>>() {
					});
		} catch (IOException e) {
			flag = false;
			msg = "凭证详情信息不合法，请核对检查";
			e.printStackTrace();
		}
		String voucherIdString = null;
		if (null != voucher && CollectionUtils.isNotEmpty(voucherExpList)) {

			// 账期 有session获取
			TCustomer customerinfo = (TCustomer) session
					.getAttribute("sessionCustomer");// 获得当前的客户信息
			//add by : zt
			int companySystem =customerinfo.getSystem();	//1 小企业  2  新规则
			// 判断凭证字号是否已存在
			int has = tVoucherService.getByBean(voucher);
			if (has > 0) {
				int maxTitle = tVoucherService.getMaxByAccountPeriod(
						voucher.getAccountPeriod(), customerinfo.getId());
				voucher.setVoucherTitleNumber((maxTitle + 1) + "");
			}
			/*
			 * voucherIdString = UUID.randomUUID().toString();
			 * voucher.setId(voucherIdString);
			 */
			voucher.setTouchingDate(new Date());
			voucher.setIsCheck("0");
			voucher.setIsNewRecord(true);
			voucher.setVoucherTitleName("记");
			voucher.setAccountPeriod(customerinfo.getCurrentperiod());
			voucher.setFdbid(customerinfo.getId());

			// 陈明加的
			voucher.setVoucherTitleNumber(String.valueOf(Integer
					.parseInt(voucher.getVoucherTitleNumber())));

			tVoucherService.save(voucher);
			voucherIdString = voucher.getId() + "";
			
			int rowNum = 1;
			String tVoucherExpId = null;
			for (TVoucherExp voucherExp : voucherExpList) {
				String expIdString = UUID.randomUUID().toString();
				voucherExp.setId(expIdString);
				voucherExp.setTVId(voucherIdString);
				voucherExp.setExpRowNumber(rowNum + "");
				voucherExp.setIsNewRecord(true);
				
				voucherExp.setFdbid(customerinfo.getId());
				voucherExpService.save(voucherExp);
				if (voucherExp.isAmortize()) {
					tVoucherExpId = voucherExp.getId();
				}
				/*
				 * TBalance balance =
				 * tBalanceService.infoByAccountId(voucherExp.getAccountId(),
				 * customerinfo.getCurrentperiod(), customerinfo.getId());
				 * if(null == balance){ balance = new TBalance(); }
				 * 
				 * balance.setDebittotalamount((Double.parseDouble(balance.
				 * getDebittotalamount())+voucherExp.getDebit())+"");
				 * balance.setYtddebittotalamount
				 * ((Double.parseDouble(balance.getYtddebittotalamount
				 * ())+voucherExp.getDebit())+"");
				 * balance.setCredittotalamount((
				 * Double.parseDouble(balance.getCredittotalamount
				 * ())+voucherExp.getCredit())+"");
				 * balance.setYtdcredittotalamount
				 * ((Double.parseDouble(balance.getYtdcredittotalamount
				 * ())+voucherExp.getCredit())+"");
				 * tBalanceService.save(balance);
				 */
				tBalanceService.maintainBalance(voucherExp.getAccountId(),
						customerinfo.getCurrentperiod(), customerinfo.getId(),
						voucherExp.getDebit(), voucherExp.getCredit(),
						voucherExp.getExp(), 0, null, null, null, null, 1,
						customerinfo.getCurrentperiod(),companySystem);

				rowNum++;
			}
			// 改变billinfo的状态
			if (org.apache.commons.lang3.StringUtils.isNoneBlank(billinfoid)) {
				String[] billinfo = billinfoid.split(",");
				if (null != billinfo && billinfo.length > 0) {
					for (String id : billinfo) {
						TBillInfo billinfoB = tBillInfoService.get(id);
						billinfoB.setBillStatus("3");// 已做账
						billinfoB.setRelateVoucher(voucherIdString);
						tBillInfoService.update(billinfoB);
					}
				}
			}
			if (StringUtils.isNoneBlank(amortize.getExpName())) {
				amortize.setId(UUID.randomUUID().toString());
				amortize.settVoucherExpId(tVoucherExpId);
				amortize.setAccountId(debitAccountId);
				amortize.setIsNewRecord(true);
				amortize.setDc("1");
				amortize.setAmortizeStatus("1");
				amortize.setIfInit("2");
				// add by wub 20151224
				amortize.setFdbid(customerinfo.getId());
				amortize.setCurrentPeriod(customerinfo.getCurrentperiod());
				tAmortizeService.save(amortize);
				amortize.setId(UUID.randomUUID().toString());
				amortize.setIsNewRecord(true);
				amortize.setAccountId(creditAccountId);
				amortize.setDc("-1");
				tAmortizeService.save(amortize);
			}
		} else {
			flag = false;
		}
		return ImmutableBiMap.of("suc", flag, "id", voucherIdString);
	}

	@RequestMapping(value = "deleteVoucher")
	@ResponseBody
	public Object deleteVoucher(String id) {
		boolean flag = true;
		TVoucher voucher = tVoucherService.get(id);
		if (null == voucher) {
			flag = false;
		}

		tVoucherService.delete(voucher);
		return ImmutableBiMap.of("suc", flag);
	}

	@RequestMapping(value = "enterVoucher")
	public ModelAndView enterVoucher(TVoucher tVoucher, String billInfos,
			String intelligentAccountId, HttpSession session) {
		ModelAndView model = new ModelAndView("modules/voucher/addvoucher");
		// billInfos = "1";
		// 根据billInfo 的id查询发票信息
		int size = 0;
		List<TBillInfo> billinfosList = new ArrayList<TBillInfo>();
		if (StringUtils.isNoneBlank(billInfos)) {
			List<String> idsList = Arrays.asList(billInfos.split(","));
			if (CollectionUtils.isNotEmpty(idsList)) {
				for (String id : idsList) {
					TBillInfo billInfo = tBillInfoService.get(id);
					if (null != billInfo) {
						billInfo.setAmount(billInfo.getAmount()
								.replace(".", ""));
						if (StringUtils.isNotBlank(intelligentAccountId)) {
							billInfo.setDebitAccount(tAccountService.get("1"));
							billInfo.setCreditAccount(tAccountService.get("68"));
						}
						billinfosList.add(billInfo);
					}
				}
				size = billinfosList.size();
				if (CollectionUtils.isNotEmpty(billinfosList)) {
					model.addObject("hasBillInfo", true);
				} else {
					model.addObject("hasBillInfo", false);
				}

			}
		}
		model.addObject("billinfosize", size);
		if (billinfosList.size() < 2) {
			for (int i = billinfosList.size(); i < 2; i++) {
				TBillInfo billInfo = new TBillInfo();
				billinfosList.add(billInfo);
			}
		}
		model.addObject("billinfos", billinfosList);

		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息

		// 获取当前期最大的凭证字号
		int maxTitle = tVoucherService.getMaxByAccountPeriod(
				customerinfo.getCurrentperiod(), customerinfo.getId());
		maxTitle = maxTitle + 1;

		// 陈明加的
		String pattern = "000";
		java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
		model.addObject("title", df.format(maxTitle));

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
			cal.set(Calendar.MONTH,
					Integer.parseInt(updateDate.substring(4, 6)) - 1);
			// 获取某月最大天数
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			// 设置日历中月份的最大天数
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String lastDayOfMonth = sdf.format(cal.getTime());
			model.addObject("date", lastDayOfMonth);
		}
		User currentUser = UserUtils.getUser();
		model.addObject("user", currentUser);
		return model;
	}

	@RequestMapping(value = "getMaxByAccountPeriod")
	@ResponseBody
	public int getMaxByAccountPeriod(String accountPeriod, String company,
			HttpSession session) {
		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息

		return tVoucherService.getMaxByAccountPeriod(
				customerinfo.getCurrentperiod(), customerinfo.getId());

		// return
		// tVoucherService.getMaxByAccountPeriod(customerinfo.getCurrentperiod(),
		// customerinfo.getId());
	}

	/**
	 * 结账
	 * 
	 * @param voucher
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "settle")
	@ResponseBody
	public Object settle(String voucherStr, HttpSession session)
			throws ParseException {
		
		boolean flag = true;
		String msg = "";
		voucherStr = voucherStr.replace("&quot;", "\"");
		voucherStr = voucherStr.substring(1, voucherStr.length() - 1);
		String[] voucherArray = voucherStr.split("]");
		List<TSettle> settles = new ArrayList<TSettle>();
		for (int i = 0; i < voucherArray.length; i++) {
			TSettle settle = new TSettle();
			String voucherExlArrString = voucherArray[i];
			if (i > 0) {
				voucherExlArrString = voucherExlArrString.substring(1,
						voucherExlArrString.length());
			}
			voucherExlArrString = "[" + voucherExlArrString.replace("[", "")
					+ "]";
			List<TVoucherExp> voucherExpList = null;
			try {
				voucherExpList = objectMapper.readValue(voucherExlArrString,
						new TypeReference<List<TVoucherExp>>() {
						});
			} catch (IOException e) {
				flag = false;
				msg = "对象转换出错";
				e.printStackTrace();
			}
			settle.settVoucherExps(voucherExpList);
			settles.add(settle);
		}
		if (flag) {
			User currentUser = UserUtils.getUser();// 当前登陆用户

			TCustomer customerinfo = (TCustomer) session
					.getAttribute("sessionCustomer");// 获得当前的客户信息
			//add by : zt
			int companySystem =customerinfo.getSystem();	//1 小企业  2  新规则
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			// 结账记录操作
			TCustomerSettleRecord tCustomerSettleRecord = new TCustomerSettleRecord(
					customerinfo.getId(), new Date(),
					customerinfo.getCurrentperiod(), currentUser);
			tCustomerSettleRecordService.save(tCustomerSettleRecord);
			String updateDate = customerinfo.getCurrentperiod();
			Calendar cal = Calendar.getInstance();
			// 设置年份
			cal.set(Calendar.YEAR, Integer.parseInt(updateDate.substring(0, 4)));
			// 设置月份
			cal.set(Calendar.MONTH,
					Integer.parseInt(updateDate.substring(4, 6)) - 1);
			// 获取某月最大天数
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			// 设置日历中月份的最大天数
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String lastDayOfMonth = sdf.format(cal.getTime());

			int maxTitle = tVoucherService.getMaxByAccountPeriod(
					customerinfo.getCurrentperiod(), customerinfo.getId());
			for (TSettle settle : settles) {
				TVoucher voucher = new TVoucher();
				// 构造凭证信息
			/*	String voucherIdString = UUID.randomUUID().toString();
				voucher.setId(voucherIdString);*/
				voucher.setVoucherTitleNumber(maxTitle + 1 + "");
				voucher.setAccountDate(cal.getTime());
				voucher.setAccountPeriod(customerinfo.getCurrentperiod());
				voucher.setRecieptNumber("0");

				voucher.setUserName(currentUser.getName());
				voucher.setTouchingDate(new Date());
				voucher.setIsCheck("0");
				voucher.setIsNewRecord(true);
				voucher.setAccountPeriod(customerinfo.getCurrentperiod());
				voucher.setFdbid(customerinfo.getId());
				voucher.setIfAuto("1");
				List<TVoucherExp> tVoucherExps = settle.gettVoucherExps();
				double totalAmount = 0;
				int rowNum = 1;
				String expName = null;
				for (TVoucherExp voucherExp : tVoucherExps) {
					expName = voucherExp.getExp();
					String expIdString = UUID.randomUUID().toString();
					voucherExp.setId(expIdString);
					//voucherExp.setTVId(voucherIdString);
					voucherExp.setExpRowNumber(rowNum + "");
					voucherExp.setIsNewRecord(true);
					voucherExp.setFdbid(customerinfo.getId());

					rowNum++;

					totalAmount = totalAmount + voucherExp.getDebit(); // +voucherExp.getCredit()
				}
				/**
				 * zt add update t_amortize 表 total_old_position +1
				 */
				if (expName != null && expName != "") {
					tAmortizeService.updatetotalOldPositionById(null,
							customerinfo.getId(), expName,
							customerinfo.getCurrentperiod());
				}
				voucher.setTotalAmount(totalAmount);
				//陈明加的
				if(tVoucherExps.size()>0){
					tVoucherService.save(voucher);
				}				
				maxTitle++;
				for (TVoucherExp voucherExp : tVoucherExps) {
					voucherExp.setTVId(voucher.getId());
					voucherExpService.save(voucherExp);
					tBalanceService.maintainBalance(voucherExp.getAccountId(),
							customerinfo.getCurrentperiod(),
							customerinfo.getId(), voucherExp.getDebit(),
							voucherExp.getCredit(), voucherExp.getExp(), 0,
							null, null, null, null, 1,
							customerinfo.getCurrentperiod(),companySystem);
				}
			}

			// 更新维护用户当前账期
			String currentperiod = customerinfo.getCurrentperiod();
			SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyyMM");
			cal.setTime(sdfYYYYMMDD.parse(currentperiod));
			cal.add(Calendar.MONTH, 1);
			String nextperiod = sdfYYYYMMDD.format(cal.getTime());
			customerinfo.setCurrentperiod(nextperiod);

			String latelyperiod = customerinfo.getLatelyperiod();
			//陈明加的
			if(latelyperiod==null|| latelyperiod.equals(currentperiod)){
				customerinfo.setLatelyperiod(nextperiod);
			}

			if (currentperiod.endsWith("12")) {// 跨年处理

			} else {
				/*
				 * // chengming加的 List<TBalance> tblance =
				 * tBalanceService.latelynextperiod(customerinfo.getId(),
				 * currentperiod); // 复制余额表数据 根据客户id
				 * System.out.println(tblance.size()); if (tblance.size() == 0)
				 * { List<TBalance> balances =
				 * tBalanceService.findListByFdbidAndAccountPeriod
				 * (customerinfo.getId(), currentperiod); if
				 * (CollectionUtils.isNotEmpty(balances)) { for (TBalance
				 * tBalance : balances) {
				 * tBalance.setId(UUID.randomUUID().toString());
				 * tBalance.setAccountPeriod(nextperiod);
				 * tBalance.setDebittotalamount(BigDecimal.ZERO.toString());
				 * tBalance.setCredittotalamount(BigDecimal.ZERO.toString());
				 * tBalance.setBeginbalance(tBalance.getEndbalance()); //zt add
				 * 维护损益 tBalance.setAmountfor(BigDecimal.ZERO.toString());
				 * tBalance.setYtdamountfor(tBalance.getYtdamountfor());
				 * tBalance.setIsNewRecord(true);
				 * tBalanceService.save(tBalance); } } }
				 */
				if (null == latelyperiod || currentperiod.equals(latelyperiod)) {// 结账
					List<TBalance> balances = tBalanceService
							.findListByFdbidAndAccountPeriod(
									customerinfo.getId(), currentperiod);
					if (CollectionUtils.isNotEmpty(balances)) {
						for (TBalance tBalance : balances) {
							tBalance.setId(UUID.randomUUID().toString());
							tBalance.setAccountPeriod(nextperiod);
							tBalance.setDebittotalamount(BigDecimal.ZERO
									.toString());
							tBalance.setCredittotalamount(BigDecimal.ZERO
									.toString());
							tBalance.setBeginbalance(tBalance.getEndbalance());
							tBalance.setAmountfor(BigDecimal.ZERO.toString());
							tBalance.setYtdamountfor(tBalance.getYtdamountfor());
							tBalance.setIsNewRecord(true);
							tBalanceService.save(tBalance);
						}
					}
				} else {// 反结账后的结账
						// 当期有 下几期没有的数据维护到下几期
					List<TBalance> balances = tBalanceService
							.findListByFdbidAndAccountPeriod(
									customerinfo.getId(), currentperiod);
					if (CollectionUtils.isNotEmpty(balances)) {
						// 获取用户最大期数
						int year = Integer.valueOf(currentperiod
								.substring(0, 4));
						int currentMonth = Integer.valueOf(currentperiod
								.substring(4, 6));
						int lastMonth = Integer.valueOf(customerinfo
								.getLatelyperiod().substring(4, 6));
						int differMonth = lastMonth - currentMonth;
						String fdbid = customerinfo.getId();
						for (TBalance balance : balances) {
							String accountId = balance.getAccountId();
							// 判断 下几期是否都有有这个科目
							if (differMonth != tBalanceService.lastHas(fdbid,
									currentperiod, accountId)) {
								for (int i = 1; i <= differMonth; i++) {
									// 判断哪一期没有
									String currentPeriodString = year
											+ ""
											+ ((currentMonth + i) < 10 ? 0 + ""
													+ (currentMonth + i)
													: (currentMonth + i));
									System.out.println(tBalanceService
											.currentHas(fdbid,
													currentPeriodString,
													accountId));
									if (tBalanceService.currentHas(fdbid,
											currentPeriodString, accountId) < 1) {
										balance.setId(UUID.randomUUID()
												.toString());
										balance.setAccountPeriod(currentPeriodString);
										balance.setDebittotalamount(BigDecimal.ZERO
												.toString());
										balance.setCredittotalamount(BigDecimal.ZERO
												.toString());
										balance.setBeginbalance(balance
												.getEndbalance());
										balance.setAmountfor(BigDecimal.ZERO
												.toString());
										balance.setIsNewRecord(true);
										tBalanceService.save(balance);
									}
								}
							}
						}
					}
				}
			}
			customerService.save(customerinfo);
			session.setAttribute("sessionCustomer", customerinfo);
		}
		return ImmutableBiMap.of("suc", flag, "msg", msg);
	}

	public List<TVoucher> showListthree(String accountPeriod, String fdbid,
			String id) {
		TVoucher tVoucher = new TVoucher();
		tVoucher.setAccountPeriod(accountPeriod);
		tVoucher.setFdbid(fdbid);
		tVoucher.setId(id);
		List<TVoucher> tvoucherList = tVoucherService.findShowThree(tVoucher);
		return tvoucherList;
	}

	public List<TVoucher> showList(String accountPeriod, String fdbid) {
		TVoucher tVoucher = new TVoucher();
		tVoucher.setAccountPeriod(accountPeriod);
		tVoucher.setFdbid(fdbid);
		List<TVoucher> tvoucherList = tVoucherService.findShowList(tVoucher);
		return tvoucherList;
	}

	@RequestMapping(value = "updateexpComment")
	@ResponseBody
	public Map<String, Object> updateexpComment(HttpServletRequest request)
			throws Exception {
		String result = "1";
		TVoucher tVoucher = new TVoucher();
		String id = request.getParameter("id");
		String expcomment = new String(request.getParameter("expcomment")
				.getBytes("ISO-8859-1"), "utf-8");
		System.out.println(id + "      " + expcomment);
		tVoucher.setId(id);
		tVoucher.setExpComment(expcomment);
		tVoucher.setCommenResult("1");
		try {
			tVoucherService.updatevoucher(tVoucher);
		} catch (Exception e) {
			result = "2";
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		map.put("id", id);
		// String json="{\"result\":\""+result+"\",\"id\":\""+id+"\"}";
		// return result;
		return map;
	}

	@RequestMapping(value = "modifyVoucher")
	public ModelAndView modifuVoucher(String id, String accountPeriod,
			HttpSession session,HttpServletRequest request) {
		
		String fresult="0";
		try{
			String aa= request.getHeader("referer");
			if(aa!=null){
				fresult="1";
			}
						
		}catch(Exception e){
			System.out.println("输入的地址错误!");
		}finally{
			
		}		
		if("1".equals(fresult)){	
			ModelAndView model = new ModelAndView("modules/voucher/modifyVoucher");
			TCustomer customerinfo = (TCustomer) session
					.getAttribute("sessionCustomer");// 获得当前的客户信息
	
			TVoucher voucher = tVoucherService.get(id);
			List<TVoucherExp> voucherExps = voucherExpService.findbytvid(id);
	
			if (CollectionUtils.isNotEmpty(voucherExps)) {
				DecimalFormat df = new DecimalFormat("#.00");
				// return new BigDecimal(df.format(data));
				for (TVoucherExp voucherExp : voucherExps) {
					TBalance balance = tBalanceService.getLastAccountEndBalance(
							customerinfo.getId(), voucherExp.getAccountId(),
							accountPeriod);
					if (null != balance) {
						BigDecimal end = new BigDecimal(balance.getEndbalance());
						if ("-1".equals(voucherExp.getDc())) {
							balance.setEndbalance((end.divide(new BigDecimal("-1"),
									2, BigDecimal.ROUND_HALF_UP)).toString());
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
				cal.set(Calendar.MONTH,
						Integer.parseInt(updateDate.substring(4, 6)) - 1);
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
		}else{
			ModelAndView model = new ModelAndView("");
			return model;
		}
	}

	@RequestMapping(value = "modify")
	@ResponseBody
	public Object modifyVoucher(String voucherID, TVoucher voucher,
			String voucherExpStr, String billinfoid, TAmortize amortize,
			String debitAccountId, String creditAccountId,
			HttpServletRequest request, HttpSession session) {
		
		String fresult="0";
		try{
			String aa= request.getHeader("referer");
			if(aa!=null){
				fresult="1";
			}
						
		}catch(Exception e){
			System.out.println("输入的地址错误!");
		}finally{
			
		}
		
		if("1".equals(fresult)){
			
		
		
				boolean flag = true;
				String msg;
				voucherExpStr = voucherExpStr.replace("&quot;", "\"");
				List<TVoucherExp> voucherExpList = null;
				try {
					voucherExpList = objectMapper.readValue(voucherExpStr,
							new TypeReference<List<TVoucherExp>>() {
							});
				} catch (IOException e) {
					flag = false;
					msg = "凭证详情信息不合法，请核对检查";
					e.printStackTrace();
				}
				String voucherIdString = null;
				if (null != voucher && CollectionUtils.isNotEmpty(voucherExpList)) {
		
					// 删除凭证再新加
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("tvbid", voucherID);
					map.put("result", "");
					voucherExpService.deleteTvourcher(map);
					String resultString = map.get("result").toString();
		
					// 账期 有session获取
					TCustomer customerinfo = (TCustomer) session
							.getAttribute("sessionCustomer");// 获得当前的客户信息
					//add by :zt
					int companySystem =customerinfo.getSystem();	//1 小企业  2  新规则
		//			voucherIdString = UUID.randomUUID().toString();
		//			voucher.setId(voucherIdString);
					voucher.setTouchingDate(new Date());
					voucher.setIsCheck("0");
					voucher.setIsNewRecord(true);
					voucher.setVoucherTitleName("记");
					voucher.setAccountPeriod(customerinfo.getCurrentperiod());
					voucher.setFdbid(customerinfo.getId());
					// 陈明加的
					voucher.setVoucherTitleNumber(String.valueOf(Integer
							.parseInt(voucher.getVoucherTitleNumber())));
					tVoucherService.save(voucher);
					voucherIdString = voucher.getId();
					int rowNum = 1;
					String tVoucherExpId = null;
					for (TVoucherExp voucherExp : voucherExpList) {
						/*String expIdString = UUID.randomUUID().toString();
						voucherExp.setId(expIdString);*/
						voucherExp.setTVId(voucherIdString);
						voucherExp.setExpRowNumber(rowNum + "");
						voucherExp.setIsNewRecord(true);
						/*if (voucherExp.isAmortize()) {
							tVoucherExpId = expIdString;
						}*/
						voucherExp.setFdbid(customerinfo.getId());
						voucherExpService.save(voucherExp);
						tVoucherExpId = voucherExp.getId() + "";
						tBalanceService.maintainBalance(voucherExp.getAccountId(),
								customerinfo.getCurrentperiod(), customerinfo.getId(),
								voucherExp.getDebit(), voucherExp.getCredit(),
								voucherExp.getExp(), 0, null, null, null, null, 1,
								customerinfo.getCurrentperiod(),companySystem);
		
						rowNum++;
					}
					// 改变billinfo的状态
					if (org.apache.commons.lang3.StringUtils.isNoneBlank(billinfoid)) {
						String[] billinfo = billinfoid.split(",");
						if (null != billinfo && billinfo.length > 0) {
							for (String id : billinfo) {
								TBillInfo billinfoB = tBillInfoService.get(id);
								billinfoB.setBillStatus("3");// 已做账
								billinfoB.setRelateVoucher(voucherIdString);
								tBillInfoService.update(billinfoB);
							}
						}
					}
					if (StringUtils.isNoneBlank(amortize.getExpName())) {
						amortize.setId(UUID.randomUUID().toString());
						amortize.settVoucherExpId(tVoucherExpId);
						amortize.setAccountId(debitAccountId);
						amortize.setIsNewRecord(true);
						amortize.setDc("1");
						amortize.setAmortizeStatus("1");
						// add by wub 20151224
						amortize.setFdbid(customerinfo.getId());
						amortize.setCurrentPeriod(customerinfo.getCurrentperiod());
						tAmortizeService.save(amortize);
						amortize.setId(UUID.randomUUID().toString());
						amortize.setIsNewRecord(true);
						amortize.setAccountId(creditAccountId);
						amortize.setDc("-1");
						tAmortizeService.save(amortize);
					}
				} else {
					flag = false;
				}
				return ImmutableBiMap.of("suc", flag, "id", voucherIdString);
		}else{
			return "";
		}
	}

	@RequestMapping(value = "insertVoucher")
	public ModelAndView insertVoucher(String title, String accountPeriod,
			HttpSession session) {
		ModelAndView model = new ModelAndView("modules/voucher/insertvoucher");

		List<TBillInfo> billinfosList = new ArrayList<TBillInfo>();
		for (int i = billinfosList.size(); i < 2; i++) {
			TBillInfo billInfo = new TBillInfo();
			billinfosList.add(billInfo);
		}
		model.addObject("billinfos", billinfosList);

		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息
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
			cal.set(Calendar.MONTH,
					Integer.parseInt(updateDate.substring(4, 6)) - 1);
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
		model.addObject("title", title);
		model.addObject("accountPeriod", accountPeriod);

		return model;
	}

	@RequestMapping(value = "insert")
	@ResponseBody
	public Object insert(TVoucher voucher, String voucherExpStr,
			TAmortize amortize, String debitAccountId, String creditAccountId,
			String accountPeriod, HttpServletRequest request,
			HttpSession session) {
		
		boolean flag = true;
		String msg;
		voucherExpStr = voucherExpStr.replace("&quot;", "\"");
		List<TVoucherExp> voucherExpList = null;
		try {
			voucherExpList = objectMapper.readValue(voucherExpStr,
					new TypeReference<List<TVoucherExp>>() {
					});
		} catch (IOException e) {
			flag = false;
			msg = "凭证详情信息不合法，请核对检查";
			e.printStackTrace();
		}
		String voucherIdString = null;
		if (null != voucher && CollectionUtils.isNotEmpty(voucherExpList)) {
			// 账期 有session获取
			TCustomer customerinfo = (TCustomer) session
					.getAttribute("sessionCustomer");// 获得当前的客户信息
			//add by :zt
			int companySystem =customerinfo.getSystem();	//1 小企业  2  新规则
			// 判断凭证字号后移
			tVoucherService.insertVoucher(customerinfo.getId(), accountPeriod,
					voucher.getVoucherTitleNumber());

			voucherIdString = UUID.randomUUID().toString();
			voucher.setId(voucherIdString);
			voucher.setTouchingDate(new Date());
			voucher.setIsCheck("0");
			voucher.setIsNewRecord(true);
			voucher.setVoucherTitleName("记");
			voucher.setAccountPeriod(customerinfo.getCurrentperiod());
			voucher.setFdbid(customerinfo.getId());
			tVoucherService.save(voucher);
			int rowNum = 1;
			String tVoucherExpId = null;
			for (TVoucherExp voucherExp : voucherExpList) {
				String expIdString = UUID.randomUUID().toString();
				voucherExp.setId(expIdString);
				voucherExp.setTVId(voucherIdString);
				voucherExp.setExpRowNumber(rowNum + "");
				voucherExp.setIsNewRecord(true);
				if (voucherExp.isAmortize()) {
					tVoucherExpId = expIdString;
				}
				voucherExp.setFdbid(customerinfo.getId());
				voucherExpService.save(voucherExp);

				tBalanceService.maintainBalance(voucherExp.getAccountId(),
						customerinfo.getCurrentperiod(), customerinfo.getId(),
						voucherExp.getDebit(), voucherExp.getCredit(),
						voucherExp.getExp(), 0, null, null, null, null, 1,
						customerinfo.getCurrentperiod(),companySystem);
				rowNum++;
			}

			if (StringUtils.isNoneBlank(amortize.getExpName())) {
				amortize.setId(UUID.randomUUID().toString());
				amortize.settVoucherExpId(tVoucherExpId);
				amortize.setAccountId(debitAccountId);
				amortize.setIsNewRecord(true);
				amortize.setDc("1");
				amortize.setAmortizeStatus("1");
				// add by wub 20151224
				amortize.setFdbid(customerinfo.getId());
				amortize.setCurrentPeriod(customerinfo.getCurrentperiod());
				tAmortizeService.save(amortize);
				amortize.setId(UUID.randomUUID().toString());
				amortize.setIsNewRecord(true);
				amortize.setAccountId(creditAccountId);
				amortize.setDc("-1");
				tAmortizeService.save(amortize);
			}
		} else {
			flag = false;
		}
		return ImmutableBiMap.of("suc", flag, "id", voucherIdString);
	}

	public PdfPTable createzhong(HttpServletRequest request) throws Exception {

		PdfPTable tableSub = new PdfPTable(1);
		PdfPCell cell = new PdfPCell();
		tableSub.setWidthPercentage(90);
		cell.setMinimumHeight(60);
		cell.setBorder(0);
		cell.setBorderWidthBottom(0);
		tableSub.addCell(cell);

		return tableSub;
	}

	@RequestMapping(value = "pdfone")
	public void pdfone(HttpServletResponse response,
			HttpServletRequest request, List<PdfPTable> listPdfPTable)
			throws Exception {
		// 定义纸张的大小
		Rectangle rec = new Rectangle(680f, 397f);
		// 创建一个文档对象
		Document document = new Document(rec);
		// 写入文本到文件中
		response.setContentType("application/pdf");
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		int pageNum = 1;
		PdfPTable tablezh = createzhong(request);
		// 动态来改变的
		int count = Integer.parseInt(request.getParameter("count"));
		if (CollectionUtils.isNotEmpty(listPdfPTable)) {
			// int n=1;
			for (PdfPTable pdfTableInfo : listPdfPTable) {

				// 新建一行
				document.add(Chunk.NEWLINE);
				document.add(tablezh);
				// pdf添加表
				document.add(pdfTableInfo);
				if (pageNum % count == 0) {
					document.newPage();
				}
				pageNum++;
			}	
		} else {
			document.add(Chunk.NEWLINE);
		}
		// 文档对象关闭
		document.close();
	}

	// 24*12
	@RequestMapping(value = "createTable24")
	public void createTable24(HttpServletRequest request,
			HttpServletResponse response) throws DocumentException, Exception {
		Font fontChinese = pdfShow.fontStyle(request, 10);
		List<PdfPTable> listtable = new ArrayList<PdfPTable>();
		DecimalFormat df = new DecimalFormat("###,##0.00");
		System.out.println(request.getParameter("accountPeriod"));
		System.out.println(request.getParameter("fdbid"));
		List<TVoucher> tvoucherList = showList(
				request.getParameter("accountPeriod"),
				request.getParameter("fdbid"));
		double totalamount = 0;
		for (TVoucher tvoucher : tvoucherList) {
			if ("0".equals(tvoucher.getDelFlag())) {
				PdfPTable tableSum = pdfShow.createPdfTable(1, 95); // 显示一个整体的表格
				TCustomer customer = customerService.get(tvoucher.getFdbid());
				PdfPTable tableTop = pdfShow.createTop(request, fontChinese,
						90, 30, customer.getCustomerName()); // -------头部信息显示的表格
				PdfPTable bottom = pdfShow.creatbottom(request, fontChinese,
						90, 30, tvoucher.getUserName()); // -------底部信息的显示表格

				// 创建一个4列的表格,宽度,各列的宽度
				PdfPTable table = pdfShow.createInfoTable(4, 90, new float[] {
						1f, 1f, 0.5f, 0.5f });
				table.addCell(pdfShow.createcell("摘  要", fontChinese, 25,
						"center", false));
				table.addCell(pdfShow.createcell("科  目", fontChinese, 25,
						"center", false));
				table.addCell(pdfShow.createcell("借  方", fontChinese, 25,
						"center", false));
				table.addCell(pdfShow.createcell("贷  方", fontChinese, 25,
						"center", false));
				// 动态数据
				List<TVoucherExp> list = voucherExpService.findbytvid(tvoucher
						.getId());
				int n = 5;
				if (list.size() > 0) {
					int sum;
					totalamount = 0;
					for (int i = 0; i < n; i++) {
						sum = list.size() - 1;
						if (i > sum) {
							table.addCell(pdfShow.createNullPdfCellthree(30));
							table.addCell(pdfShow.createNullPdfCellthree(30));
							table.addCell(pdfShow.createNullPdfCellthree(30));
							table.addCell(pdfShow.createNullPdfCellthree(30));
						} else {
							if (tvoucher.getId().equals(list.get(i).getTVId())) {
								totalamount = totalamount
										+ list.get(i).getCredit();
								if (list.get(i).getDebit() == 0) {
									list.get(i).setShowdebit("");
								} else {
									list.get(i).setShowdebit(
											df.format(list.get(i).getDebit()));
								}
								if (list.get(i).getCredit() == 0) {
									list.get(i).setShowcredit("");
								} else {
									list.get(i).setShowcredit(
											df.format(list.get(i).getCredit()));
								}
							}
							table.addCell(pdfShow.createcell(list.get(i)
									.getExp(), fontChinese, 30, "left", false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getAccountName().replace("&nbsp;", " "),
									fontChinese, 30, "left", false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getShowdebit(), fontChinese, 30, "right",
									false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getShowcredit(), fontChinese, 30, "right",
									false));
						}
					}
				}
				BigDecimal numberOfMoney = BigDecimal.valueOf(Double
						.valueOf(String.valueOf(totalamount)));
				String money = NumberToCN.number2CNMontrayUnit(numberOfMoney);
				// 以下为固定的三行
				PdfPCell sumCell = pdfShow.createcell("合 计：" + money,
						fontChinese, 20, "left", false);
				sumCell.setColspan(4);
				table.addCell(sumCell);
				// 头部信息显示
				PdfPCell cellTableTop = new PdfPCell(tableTop);
				cellTableTop.setBorderWidth(0);
				tableSum.addCell(cellTableTop);
				// 内容信息显示
				PdfPCell cellTable = new PdfPCell(table);
				cellTable.setBorderWidth(0);
				tableSum.addCell(cellTable);
				// 底部信息显示
				PdfPCell cellBottomTable = new PdfPCell(bottom);
				cellBottomTable.setBorderWidth(0);
				tableSum.addCell(cellBottomTable);

				listtable.add(tableSum);
			}
		}
		this.pdfone(response, request, listtable);
	}

	@RequestMapping(value = "pdf12")
	public void pdf12(HttpServletResponse response, HttpServletRequest request,
			List<PdfPTable> listPdfPTable) throws Exception {
		// 定义纸张的大小
		Rectangle rec = new Rectangle(595f, 340f);
		Document document = new Document(rec);
		response.setContentType("application/pdf");
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		// 写入文本到文件中
		response.setContentType("application/pdf");
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		int pageNum = 1;
		// 动态来改变的
		int count = Integer.parseInt(request.getParameter("count"));
		if (CollectionUtils.isNotEmpty(listPdfPTable)) {
			// int n=1;
			for (PdfPTable pdfTableInfo : listPdfPTable) {

				// 新建一行
				document.add(Chunk.NEWLINE);
				// pdf添加表
				document.add(pdfTableInfo);
				if (pageNum % count == 0) {
					document.newPage();
				}
				pageNum++;
			}	
		} else {
			document.add(Chunk.NEWLINE);
		}
		// 文档对象关闭
		document.close();
	}

	@RequestMapping(value = "createTable12New")
	public void createTable12New(HttpServletRequest request,
			HttpServletResponse response) throws DocumentException, Exception {
		Font fontChinese = pdfShow.fontStyle(request, 10);
		List<PdfPTable> listtable = new ArrayList<PdfPTable>();
		DecimalFormat df = new DecimalFormat("###,##0.00");
		System.out.println(request.getParameter("accountPeriod"));
		System.out.println(request.getParameter("fdbid"));
		List<TVoucher> tvoucherList = showList(
				request.getParameter("accountPeriod"),
				request.getParameter("fdbid"));
		for (TVoucher tvoucher : tvoucherList) {
			if ("0".equals(tvoucher.getDelFlag())) {
				PdfPTable tableSum = pdfShow.createPdfTable(1, 95); // 显示一个整体的表格
				TCustomer customer = customerService.get(tvoucher.getFdbid());
				PdfPTable tableTop = pdfShow.createTop(request, fontChinese,
						90, 20, customer.getCustomerName()); // -------头部信息显示的表格
				PdfPTable bottom = pdfShow.creatbottom(request, fontChinese,
						90, 20, tvoucher.getUserName()); // -------底部信息的显示表格

				// 创建一个4列的表格,宽度,各列的宽度
				PdfPTable table = pdfShow.createInfoTable(4, 90, new float[] {
						1f, 1f, 0.5f, 0.5f });
				table.addCell(pdfShow.createcell("摘  要", fontChinese, 25,
						"center", false));
				table.addCell(pdfShow.createcell("科  目", fontChinese, 25,
						"center", false));
				table.addCell(pdfShow.createcell("借  方", fontChinese, 25,
						"center", false));
				table.addCell(pdfShow.createcell("贷  方", fontChinese, 25,
						"center", false));
				// 动态数据
				List<TVoucherExp> list = voucherExpService.findbytvid(tvoucher
						.getId());
				double totalamount = 0;
				int n = 5;
				if (list.size() > 0) {
					int sum;
					totalamount = 0;
					for (int i = 0; i < n; i++) {
						sum = list.size() - 1;
						if (i > sum) {
							table.addCell(pdfShow.createNullPdfCellthree(30));
							table.addCell(pdfShow.createNullPdfCellthree(30));
							table.addCell(pdfShow.createNullPdfCellthree(30));
							table.addCell(pdfShow.createNullPdfCellthree(30));
						} else {
							if (tvoucher.getId().equals(list.get(i).getTVId())) {
								totalamount = totalamount
										+ list.get(i).getCredit();
								if (list.get(i).getDebit() == 0) {
									list.get(i).setShowdebit("");
								} else {
									list.get(i).setShowdebit(
											df.format(list.get(i).getDebit()));
								}
								if (list.get(i).getCredit() == 0) {
									list.get(i).setShowcredit("");
								} else {
									list.get(i).setShowcredit(
											df.format(list.get(i).getCredit()));
								}
							}
							table.addCell(pdfShow.createcell(list.get(i)
									.getExp(), fontChinese, 30, "left", false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getAccountName().replace("&nbsp;", " "),
									fontChinese, 30, "left", false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getShowdebit(), fontChinese, 30, "right",
									false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getShowcredit(), fontChinese, 30, "right",
									false));
						}
					}
				}
				BigDecimal numberOfMoney = BigDecimal.valueOf(Double
						.valueOf(String.valueOf(totalamount)));
				String money = NumberToCN.number2CNMontrayUnit(numberOfMoney);
				// 以下为固定的三行
				PdfPCell sumCell = pdfShow.createcell("合 计：" + money,
						fontChinese, 20, "left", false);
				sumCell.setColspan(4);
				table.addCell(sumCell);
				// 头部信息显示
				PdfPCell cellTableTop = new PdfPCell(tableTop);
				cellTableTop.setBorderWidth(0);
				tableSum.addCell(cellTableTop);
				// 内容信息显示
				PdfPCell cellTable = new PdfPCell(table);
				cellTable.setBorderWidth(0);
				tableSum.addCell(cellTable);
				// 底部信息显示
				PdfPCell cellBottomTable = new PdfPCell(bottom);
				cellBottomTable.setBorderWidth(0);
				tableSum.addCell(cellBottomTable);

				listtable.add(tableSum);
			}
		}
		// this.pdfA4(response,request,listtable);
		this.pdf12(response, request, listtable);
	}

	// -----------------------------以下为a4三版和两版的-------------------
	@RequestMapping(value = "pageShowThreeNew")
	public void pageShowThreeNew(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model)
			throws Exception {
		Font fontChinese = pdfShow.fontStyle(request, 10);
		List<PdfPTable> listtable = new ArrayList<PdfPTable>();
		DecimalFormat df = new DecimalFormat("###,##0.00");
		System.out.println(request.getParameter("accountPeriod"));
		System.out.println(request.getParameter("fdbid"));
		List<TVoucher> tvoucherList = showList(
				request.getParameter("accountPeriod"),
				request.getParameter("fdbid"));
		double totalamount = 0;
		for (TVoucher tvoucher : tvoucherList) {

			PdfPTable tableSum = pdfShow.createPdfTable(1, 95); // 显示一个整体的表格
			TCustomer customer = customerService.get(tvoucher.getFdbid());
			PdfPTable tableTop = pdfShow.createTop(request, fontChinese, 90,
					20, customer.getCustomerName()); // -------头部信息显示的表格
			PdfPTable bottom = pdfShow.creatbottom(request, fontChinese, 90,
					20, tvoucher.getUserName()); // -------底部信息的显示表格

			// 创建一个4列的表格,宽度,各列的宽度
			PdfPTable table = pdfShow.createInfoTable(4, 90, new float[] { 1f,
					1f, 0.5f, 0.5f });
			table.addCell(pdfShow.createcell("摘  要", fontChinese, 25, "center",
					false));
			table.addCell(pdfShow.createcell("科  目", fontChinese, 25, "center",
					false));
			table.addCell(pdfShow.createcell("借  方", fontChinese, 25, "center",
					false));
			table.addCell(pdfShow.createcell("贷  方", fontChinese, 25, "center",
					false));
			// 动态数据
			List<TVoucherExp> list = voucherExpService.findbytvid(tvoucher
					.getId());
			int n = 5;
			if (list.size() > 0) {
				int sum;
				totalamount = 0;
				for (int i = 0; i < n; i++) {
					sum = list.size() - 1;
					if (i > sum) {
						table.addCell(pdfShow.createNullPdfCellthree(30));
						table.addCell(pdfShow.createNullPdfCellthree(30));
						table.addCell(pdfShow.createNullPdfCellthree(30));
						table.addCell(pdfShow.createNullPdfCellthree(30));
					} else {
						if (tvoucher.getId().equals(list.get(i).getTVId())) {
							totalamount = totalamount + list.get(i).getCredit();
							if (list.get(i).getDebit() == 0) {
								list.get(i).setShowdebit("");
							} else {
								list.get(i).setShowdebit(
										df.format(list.get(i).getDebit()));
							}
							if (list.get(i).getCredit() == 0) {
								list.get(i).setShowcredit("");
							} else {
								list.get(i).setShowcredit(
										df.format(list.get(i).getCredit()));
							}
						}
						table.addCell(pdfShow.createcell(list.get(i).getExp(),
								fontChinese, 30, "left", false));
						table.addCell(pdfShow.createcell(list.get(i)
								.getAccountName().replace("&nbsp;", " "),
								fontChinese, 30, "left", false));
						table.addCell(pdfShow.createcell(list.get(i)
								.getShowdebit(), fontChinese, 30, "right",
								false));
						table.addCell(pdfShow.createcell(list.get(i)
								.getShowcredit(), fontChinese, 30, "right",
								false));
					}
				}
			}
			BigDecimal numberOfMoney = BigDecimal.valueOf(Double.valueOf(String
					.valueOf(totalamount)));
			String money = NumberToCN.number2CNMontrayUnit(numberOfMoney);
			// 以下为固定的三行
			PdfPCell sumCell = pdfShow.createcell("合 计：" + money, fontChinese,
					20, "left", false);
			sumCell.setColspan(4);
			table.addCell(sumCell);
			// 头部信息显示
			PdfPCell cellTableTop = new PdfPCell(tableTop);
			cellTableTop.setBorderWidth(0);
			tableSum.addCell(cellTableTop);
			// 内容信息显示
			PdfPCell cellTable = new PdfPCell(table);
			cellTable.setBorderWidth(0);
			tableSum.addCell(cellTable);
			// 底部信息显示
			PdfPCell cellBottomTable = new PdfPCell(bottom);
			cellBottomTable.setBorderWidth(0);
			tableSum.addCell(cellBottomTable);

			listtable.add(tableSum);
		}
		this.pdfA4(response, request, listtable);

	}

	@RequestMapping(value = "pdfModelTwo")
	public void pdfModelTwo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Font fontChinese = pdfShow.fontStyle(request, 12);
		List<PdfPTable> listtable = new ArrayList<PdfPTable>();
		DecimalFormat df = new DecimalFormat("###,##0.00");
		System.out.println(request.getParameter("accountPeriod"));
		System.out.println(request.getParameter("fdbid"));
		List<TVoucher> tvoucherList = showList(
				request.getParameter("accountPeriod"),
				request.getParameter("fdbid"));
		double totalamount = 0;
		for (TVoucher tvoucher : tvoucherList) {
			if ("0".equals(tvoucher.getDelFlag())) {
				PdfPTable tableSum = pdfShow.createPdfTable(1, 95); // 显示一个整体的表格
				TCustomer customer = customerService.get(tvoucher.getFdbid());
				PdfPTable tableTop = pdfShow.createTop(request, fontChinese,
						90, 20, customer.getCustomerName()); // -------头部信息显示的表格
				PdfPTable bottom = pdfShow.creatbottom(request, fontChinese,
						90, 20, tvoucher.getUserName()); // -------底部信息的显示表格

				// 创建一个4列的表格,宽度,各列的宽度
				PdfPTable table = pdfShow.createInfoTable(4, 90, new float[] {
						1f, 1f, 0.5f, 0.5f });
				table.addCell(pdfShow.createcell("摘  要", fontChinese, 30,
						"center", false));
				table.addCell(pdfShow.createcell("科  目", fontChinese, 30,
						"center", false));
				table.addCell(pdfShow.createcell("借  方", fontChinese, 30,
						"center", false));
				table.addCell(pdfShow.createcell("贷  方", fontChinese, 30,
						"center", false));
				// 动态数据
				List<TVoucherExp> list = voucherExpService.findbytvid(tvoucher
						.getId());
				int n = 5;
				if (list.size() > 0) {
					int sum;
					totalamount = 0;
					for (int i = 0; i < n; i++) {
						sum = list.size() - 1;
						if (i > sum) {
							table.addCell(pdfShow.createNullPdfCellthree(35));
							table.addCell(pdfShow.createNullPdfCellthree(35));
							table.addCell(pdfShow.createNullPdfCellthree(35));
							table.addCell(pdfShow.createNullPdfCellthree(35));
						} else {
							if (tvoucher.getId().equals(list.get(i).getTVId())) {
								totalamount = totalamount
										+ list.get(i).getCredit();
								if (list.get(i).getDebit() == 0) {
									list.get(i).setShowdebit("");
								} else {
									list.get(i).setShowdebit(
											df.format(list.get(i).getDebit()));
								}
								if (list.get(i).getCredit() == 0) {
									list.get(i).setShowcredit("");
								} else {
									list.get(i).setShowcredit(
											df.format(list.get(i).getCredit()));
								}
							}
							table.addCell(pdfShow.createcell(list.get(i)
									.getExp(), fontChinese, 35, "left", false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getAccountName().replace("&nbsp;", " "),
									fontChinese, 30, "left", false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getShowdebit(), fontChinese, 35, "right",
									false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getShowcredit(), fontChinese, 35, "right",
									false));
						}
					}
				}
				BigDecimal numberOfMoney = BigDecimal.valueOf(Double
						.valueOf(String.valueOf(totalamount)));
				String money = NumberToCN.number2CNMontrayUnit(numberOfMoney);
				// 以下为固定的三行
				PdfPCell sumCell = pdfShow.createcell("合 计：" + money,
						fontChinese, 25, "left", false);
				sumCell.setColspan(4);
				table.addCell(sumCell);
				// 头部信息显示
				PdfPCell cellTableTop = new PdfPCell(tableTop);
				cellTableTop.setBorderWidth(0);
				tableSum.addCell(cellTableTop);
				// 内容信息显示
				PdfPCell cellTable = new PdfPCell(table);
				cellTable.setBorderWidth(0);
				tableSum.addCell(cellTable);
				// 底部信息显示
				PdfPCell cellBottomTable = new PdfPCell(bottom);
				cellBottomTable.setBorderWidth(0);
				tableSum.addCell(cellBottomTable);

				listtable.add(tableSum);
			}
		}
			this.pdfA4(response, request, listtable);
	}

	@RequestMapping(value = "pdfA4")
	public void pdfA4(HttpServletResponse response, HttpServletRequest request,
			List<PdfPTable> listPdfPTable) throws Exception {
		// 定义纸张的大小
		Document document = new Document(PageSize.A4, 20, 20, 20, 20);
		// 动态来改变的
		int count = Integer.parseInt(request.getParameter("count"));
		response.setContentType("application/pdf");
		PdfWriter.getInstance(document, response.getOutputStream());
		PdfPTable tablezh = null;
		if (count == 2)
			tablezh = pdfShow.createzhongNew(request, 90, 70);
		else
			tablezh = pdfShow.createzhongNew(request, 90, 15);
		document.open();
		// 写入文本到文件中
		response.setContentType("application/pdf");
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		int pageNum = 1;
		if (CollectionUtils.isNotEmpty(listPdfPTable)) {
			// int n=1;
			for (PdfPTable pdfTableInfo : listPdfPTable) {

				// 新建一行
				document.add(Chunk.NEWLINE);
				document.add(tablezh);
				// pdf添加表
				document.add(pdfTableInfo);
				if (pageNum % count == 0) {
					document.newPage();
				}
				pageNum++;
			}	
		} else {
			document.add(Chunk.NEWLINE);
		}
		// 文档对象关闭
		document.close();
		
	}

	// 单个凭证打印
	@RequestMapping(value = "pageShowOne")
	public void pageShowOne(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model)
			throws Exception {
		Font fontChinese = pdfShow.fontStyle(request, 12);
		List<PdfPTable> listtable = new ArrayList<PdfPTable>();
		DecimalFormat df = new DecimalFormat("###,##0.00");
		System.out.println(request.getParameter("accountPeriod"));
		System.out.println(request.getParameter("fdbid"));
		List<TVoucher> tvoucherList = showListthree(
				request.getParameter("accountPeriod"),
				request.getParameter("fdbid"), request.getParameter("id"));
		double totalamount = 0;
		for (TVoucher tvoucher : tvoucherList) {
			if ("0".equals(tvoucher.getDelFlag())) {
				PdfPTable tableSum = pdfShow.createPdfTable(1, 95); // 显示一个整体的表格
				TCustomer customer = customerService.get(tvoucher.getFdbid());
				PdfPTable tableTop = pdfShow.createTop(request, fontChinese,
						90, 20, customer.getCustomerName()); // -------头部信息显示的表格
				PdfPTable bottom = pdfShow.creatbottom(request, fontChinese,
						90, 20, tvoucher.getUserName()); // -------底部信息的显示表格

				// 创建一个4列的表格,宽度,各列的宽度
				PdfPTable table = pdfShow.createInfoTable(4, 90, new float[] {
						1f, 1f, 0.5f, 0.5f });
				table.addCell(pdfShow.createcell("摘  要", fontChinese, 30,
						"center", false));
				table.addCell(pdfShow.createcell("科  目", fontChinese, 30,
						"center", false));
				table.addCell(pdfShow.createcell("借  方", fontChinese, 30,
						"center", false));
				table.addCell(pdfShow.createcell("贷  方", fontChinese, 30,
						"center", false));
				// 动态数据
				List<TVoucherExp> list = voucherExpService.findbytvid(tvoucher
						.getId());
				int n = 5;
				if (list.size() > 0) {
					int sum;
					totalamount = 0;
					for (int i = 0; i < n; i++) {
						sum = list.size() - 1;
						if (i > sum) {
							table.addCell(pdfShow.createNullPdfCellthree(35));
							table.addCell(pdfShow.createNullPdfCellthree(35));
							table.addCell(pdfShow.createNullPdfCellthree(35));
							table.addCell(pdfShow.createNullPdfCellthree(35));
						} else {
							if (tvoucher.getId().equals(list.get(i).getTVId())) {
								totalamount = totalamount
										+ list.get(i).getCredit();
								if (list.get(i).getDebit() == 0) {
									list.get(i).setShowdebit("");
								} else {
									list.get(i).setShowdebit(
											df.format(list.get(i).getDebit()));
								}
								if (list.get(i).getCredit() == 0) {
									list.get(i).setShowcredit("");
								} else {
									list.get(i).setShowcredit(
											df.format(list.get(i).getCredit()));
								}
							}
							table.addCell(pdfShow.createcell(list.get(i)
									.getExp(), fontChinese, 35, "left", false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getAccountName().replace("&nbsp;", " "),
									fontChinese, 30, "left", false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getShowdebit(), fontChinese, 35, "right",
									false));
							table.addCell(pdfShow.createcell(list.get(i)
									.getShowcredit(), fontChinese, 35, "right",
									false));
						}
					}
				}
				BigDecimal numberOfMoney = BigDecimal.valueOf(Double
						.valueOf(String.valueOf(totalamount)));
				String money = NumberToCN.number2CNMontrayUnit(numberOfMoney);
				// 以下为固定的三行
				PdfPCell sumCell = pdfShow.createcell("合 计：" + money,
						fontChinese, 25, "left", false);
				sumCell.setColspan(4);
				table.addCell(sumCell);
				// 头部信息显示
				PdfPCell cellTableTop = new PdfPCell(tableTop);
				cellTableTop.setBorderWidth(0);
				tableSum.addCell(cellTableTop);
				// 内容信息显示
				PdfPCell cellTable = new PdfPCell(table);
				cellTable.setBorderWidth(0);
				tableSum.addCell(cellTable);
				// 底部信息显示
				PdfPCell cellBottomTable = new PdfPCell(bottom);
				cellBottomTable.setBorderWidth(0);
				tableSum.addCell(cellBottomTable);

				listtable.add(tableSum);
			}
		}
		this.pdfA4(response, request, listtable);
	}
	/**
	 * 获取公司客户信息
	 */
	public TCustomer getCompanyInfo(HttpSession session) {
		Object obj = session.getAttribute("sessionCustomer");
		TCustomer companyInfo = (TCustomer) obj; // 客户公司信息对象
		return companyInfo;

	}
}