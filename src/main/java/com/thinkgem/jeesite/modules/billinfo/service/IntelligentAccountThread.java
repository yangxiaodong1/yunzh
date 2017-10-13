package com.thinkgem.jeesite.modules.billinfo.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.thinkgem.jeesite.common.utils.IntelligentAccountUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.billinfo.entity.IntelligentAccountContentResult;
import com.thinkgem.jeesite.modules.billinfo.entity.IntelligentAccountParam;
import com.thinkgem.jeesite.modules.billinfo.entity.IntelligentAccountResult;
import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfo;
import com.thinkgem.jeesite.modules.billinfo.entity.Work;
import com.thinkgem.jeesite.modules.billtype.service.TBillTypeService;
import com.thinkgem.jeesite.modules.billtype1.service.TBillType1Service;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;

public class IntelligentAccountThread extends Thread {

	private Work work;

	public IntelligentAccountThread(Work work) {
		this.work = work;
	}

	public void run() {
		HttpSession session = work.getSession();
		TCustomer tCustomer = (TCustomer) session.getAttribute("sessionCustomer");
		String customerId = tCustomer.getId();
		session.setAttribute(session.getId() + customerId + "loading", 0);

		List<TBillInfo> billInfos = work.getBillInfos();
		int title = work.getTitle();
		int titleCount = 1;
		TBalanceService tBalanceService = work.gettBalanceService();
		TAccountService tAccountService = work.gettAccountService();
		String accountDate = work.getAccountDate();
		TBillTypeService tBillTypeService = work.gettBillTypeService();
		TBillType1Service tBillType1Service = work.gettBillType1Service();

		String urlInvoice = "http://101.251.234.166:8081/v1/invoice/";
		int currentIndex = 0;
		List<IntelligentAccountResult> resultList = new ArrayList<IntelligentAccountResult>();
		StringBuffer failBillids = new StringBuffer();
		DecimalFormat df = new DecimalFormat("#.00");
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

		for (TBillInfo bill : billInfos) {
			int curTitle = title + titleCount;
			titleCount++;
			/*
			if("2".equals(bill.getId())){
				System.out.println(bill.getAmount());
				String billkindString = tBillType1Service.get(bill.getBillKind()).getBillTypeName();
				System.out.println(tBillType1Service.get(bill.getBillKind()).getBillTypeName());
			}*/
			
			String billkind= tBillType1Service.get(bill.getBillKind()).getBillTypeName();
			String payer = bill.getPayName();
			if(StringUtils.isBlank(payer)){
				payer = tCustomer.getCustomerName();
			}
				
			IntelligentAccountParam intelligentAccount = new IntelligentAccountParam(bill.getId(),billkind, "1", 
					"0",customerId,tCustomer.getCustomerName(), bill.getAbstractMsg()
					, new BigDecimal(bill.getAmount()), new BigDecimal(bill.getTax()), new BigDecimal(bill.getTotalPrice()),payer , bill.getRecieveName(), "", tBillTypeService.get(bill.gettBId())
					.getBillTypeName(),billkind);

			//intelligentAccount.setSummary("健身费");
			
			String requestParam = JSONObject.fromObject(intelligentAccount).toString();
			System.out.println("==================================================");
			System.out.println(requestParam);
			String returnInvoice = IntelligentAccountUtils.loadJSON(urlInvoice, requestParam);
			JSONObject resultJsonObject = JSONObject.fromObject(returnInvoice);
			String errCode = resultJsonObject.getString("errCode");
			
			System.out.println(errCode); System.out.println("==================================================");
			 
			//errCode = "1";
			boolean amortizeFlag = true;

			if ("0".equals(errCode)) {
				String summary = resultJsonObject.getString("summary");
				JSONArray contentJsonArray = resultJsonObject.getJSONArray("content");
				List<IntelligentAccountContentResult> contentList = JSONArray.toList(contentJsonArray, IntelligentAccountContentResult.class);
				IntelligentAccountResult result = new IntelligentAccountResult();
				result.setTitle(curTitle);
				result.setBillInfo(bill);
				try {
					result.setAccountDate(sf.parse(accountDate));
					result.setAccountDateString(accountDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//result.setTotalAmount(contentList.get(0).getDebit().add(contentList.get(0).getCredit()));
				BigDecimal totalAmount = BigDecimal.ZERO;
				result.setSummary(summary);
				for (IntelligentAccountContentResult contentResult : contentList) {
					
					totalAmount = totalAmount.add(contentResult.getDebit());
					
					contentResult.setSummary(summary);
					if (contentResult.getDebit().doubleValue() > 0) {
						contentResult.setDc("1");
					}
					
					TAccount account = tAccountService.getAccountsInfoByName("",customerId,contentResult.getSubject());
					List<TAccount> pareAccounts = tAccountService
							.getParentAccounts(account.getParentId().getId());
					StringBuffer parentNameString = new StringBuffer();
					StringBuffer parentInitNameString = new StringBuffer();
					if (CollectionUtils.isNotEmpty(pareAccounts)) {
						for (int i = pareAccounts.size(); i > 0; i--) {
							parentNameString.append(pareAccounts.get(i - 1)
									.getAccountName() + "-");
							parentInitNameString.append(pareAccounts.get(i - 1).getInitName());
						}
					}
					account.setParentName(parentNameString.toString());

					TBalance balance = tBalanceService.getLastAccountEndBalance(tCustomer.getId(), contentResult.getSubject(), work.getAccountDate().substring(0, 6));
					if (null != balance) {
						BigDecimal end = new BigDecimal(balance.getEndbalance());
						if (contentResult.getDebit().doubleValue() > 0) {
							balance.setEndbalance((end.divide(new BigDecimal("-1"), 2, BigDecimal.ROUND_HALF_UP)).toString());
							// 根据科目id获取科目信息
							//TAccount account = tAccountService.get(contentResult.getSubject());
							if (null != account && 1 == account.getIfAmortize() && amortizeFlag) {
								amortizeFlag = false;
								contentResult.setAmortizeFlag(true);
							}
						}
						contentResult.setBalance(balance);
					}
					// 格式化数值
					contentResult.setShowdebit(df.format(contentResult.getDebit()));
					contentResult.setShowcredit(df.format(contentResult.getCredit()));
					contentResult.setAccount(account);
				}
				result.setTotalAmount(totalAmount);
				result.setIntelligentAccountContentResults(contentList);
				resultList.add(result);
			} else {
				failBillids.append(bill.getId() + ",");
				// ////////////////////////////////////////////////////////////////
				// todo
				/*IntelligentAccountResult result = new IntelligentAccountResult();
				result.setTitle(curTitle);
				result.setBillInfo(bill);
				try {
					result.setAccountDate(sf.parse(accountDate));
					result.setAccountDateString(accountDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				result.setSummary("失败");
				List<IntelligentAccountContentResult> contentList = new ArrayList<IntelligentAccountContentResult>();
				for (int i = 0; i < 2; i++) {
					IntelligentAccountContentResult contentResult = new IntelligentAccountContentResult();
					contentResult.setSeq(i);
					contentResult.setSubject("26460");
					contentResult.setDebit(BigDecimal.valueOf(5550.11 + i));
					contentResult.setCredit(BigDecimal.valueOf(5550.11 + i));
					contentResult.setCredit(BigDecimal.ZERO);
					contentResult.setSummary("失败");
					if (contentResult.getDebit().doubleValue() > 0) {
						contentResult.setDc("1");
					}
					TBalance balance = tBalanceService.getLastAccountEndBalance(tCustomer.getId(), contentResult.getSubject(), work.getAccountDate());
					if (null != balance) {
						BigDecimal end = new BigDecimal(balance.getEndbalance());
						if (contentResult.getDebit().doubleValue() > 0) {
							balance.setEndbalance((end.divide(new BigDecimal("-1"), 2, BigDecimal.ROUND_HALF_UP)).toString());
							// 根据科目id获取科目信息
							TAccount account = tAccountService.get(contentResult.getSubject());
							if (1 == account.getIfAmortize() && amortizeFlag) {
								amortizeFlag = false;
								contentResult.setAmortizeFlag(true);
							}
						}
						contentResult.setBalance(balance);
					}
					// 格式化数值
					contentResult.setShowdebit(df.format(contentResult.getDebit()));
					contentResult.setShowcredit(df.format(contentResult.getCredit()));
					contentResult.setAccount(tAccountService.get(contentResult.getSubject()));
					contentList.add(contentResult);
				}
				result.setTotalAmount(contentList.get(0).getDebit().add(contentList.get(0).getCredit()));
				result.setIntelligentAccountContentResults(contentList);
				resultList.add(result);*/
				// ////////////////////////////////////////////////////////////////////////
			}
			// session存进度
			session.setAttribute(session.getId() + customerId + "loading", ++currentIndex);
		}
		// 存储最后数据
		session.setAttribute(session.getId() + customerId + "result", resultList);
		session.setAttribute(session.getId() + customerId + "failBillids", failBillids.toString());
		session.setAttribute(session.getId() + customerId + "bills", billInfos);
	}

}
