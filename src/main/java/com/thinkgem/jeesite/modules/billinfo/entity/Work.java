package com.thinkgem.jeesite.modules.billinfo.entity;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.billtype.service.TBillTypeService;
import com.thinkgem.jeesite.modules.billtype1.service.TBillType1Service;

public class Work {

	private HttpSession session;

	private List<TBillInfo> billInfos;

	private String accountDate;

	private int title;

	private TBalanceService tBalanceService;

	private TAccountService tAccountService;

	private TBillTypeService tBillTypeService;

	private TBillType1Service tBillType1Service;

	public Work(HttpSession session, List<TBillInfo> billInfos,
			String accountDate, int title, TBalanceService tBalanceService,
			TAccountService tAccountService, TBillTypeService tBillTypeService,
			TBillType1Service tBillType1Service) {
		super();
		this.session = session;
		this.billInfos = billInfos;
		this.accountDate = accountDate;
		this.title = title;
		this.tBalanceService = tBalanceService;
		this.tAccountService = tAccountService;
		this.tBillTypeService = tBillTypeService;
		this.tBillType1Service = tBillType1Service;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public List<TBillInfo> getBillInfos() {
		return billInfos;
	}

	public void setBillInfos(List<TBillInfo> billInfos) {
		this.billInfos = billInfos;
	}

	public String getAccountDate() {
		/*
		 * TCustomer customerinfo = (TCustomer) session
		 * .getAttribute("sessionCustomer");// 获得当前的客户信息 // 日期 当前账期的最后一天 // 当前账期
		 * String updateDate = customerinfo.getCurrentperiod(); Calendar cal =
		 * Calendar.getInstance(); int month = cal.get(Calendar.MONTH) + 1; if
		 * (Integer.parseInt(updateDate.substring(4, 6)) == month) { // 格式化日期
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 * accountDate = sdf.format(cal.getTime()); } else { // 设置年份
		 * cal.set(Calendar.YEAR, Integer.parseInt(updateDate.substring(0, 4)));
		 * // 设置月份 cal.set(Calendar.MONTH,
		 * Integer.parseInt(updateDate.substring(4, 6)) - 1); // 获取某月最大天数 int
		 * lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //
		 * 设置日历中月份的最大天数 cal.set(Calendar.DAY_OF_MONTH, lastDay); // 格式化日期
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 * accountDate = sdf.format(cal.getTime()); }
		 */
		String year = accountDate.substring(0, 4);
		String month = accountDate.substring(4, 6);
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		// 设置月份
		cal.set(Calendar.MONTH, Integer.valueOf(month) - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return year + month + new DecimalFormat("00").format(lastDay);
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public TBalanceService gettBalanceService() {
		return tBalanceService;
	}

	public void settBalanceService(TBalanceService tBalanceService) {
		this.tBalanceService = tBalanceService;
	}

	public TAccountService gettAccountService() {
		return tAccountService;
	}

	public void settAccountService(TAccountService tAccountService) {
		this.tAccountService = tAccountService;
	}

	public TBillTypeService gettBillTypeService() {
		return tBillTypeService;
	}

	public void settBillTypeService(TBillTypeService tBillTypeService) {
		this.tBillTypeService = tBillTypeService;
	}

	public TBillType1Service gettBillType1Service() {
		return tBillType1Service;
	}

	public void settBillType1Service(TBillType1Service tBillType1Service) {
		this.tBillType1Service = tBillType1Service;
	}

}
