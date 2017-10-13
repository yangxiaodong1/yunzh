/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.entity.workstatistics;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 收费审核Entity
 * @author xuxiaoong
 * @version 2015-11-25
 */
public class TServiceCharge extends DataEntity<TServiceCharge> {
	
	private static final long serialVersionUID = 1L;
	private String customerId;		// 用户id
	private String customerName;//公司名称
	private String loginName;//审核人
	private Date signDate;		// 签约日期
	private String serviceDate;		// 服务日期
	private Double loanMoney;		// 代账收费
	private Double accountbookMoney;		// 账本收费
	private String payeeMan;//收款人
	private String modePayment;		// 付款方式 按月收费：0 按季收费：1 按年收费：2 
	private Double shouldMoney;		// 应收费
	private Double realityMoney;		// 实际收费
	private String remark;		// 备注
	private String state;		// 审核状态 未审核：0 已审核：1
	private Boolean states =true;
	private Date beginSignDate;		// 开始 签约日期
	private Date endSignDate;		// 结束 签约日期
	private String serviceDate1;//服务日期开始年
	private String serviceDate2;//服务日期开始月
	private String serviceDate3;//服务日期结束年
	private String serviceDate4;//服务日期结束月
	private String serviceChargeNo;//收据编号
	private String companyId;//记账公司Id
	

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getServiceChargeNo() {
		return serviceChargeNo;
	}

	public void setServiceChargeNo(String serviceChargeNo) {
		this.serviceChargeNo = serviceChargeNo;
	}

	public String getServiceDate1() {
		return serviceDate1;
	}

	public void setServiceDate1(String serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}

	public String getServiceDate2() {
		return serviceDate2;
	}

	public void setServiceDate2(String serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}

	public String getServiceDate3() {
		return serviceDate3;
	}

	public void setServiceDate3(String serviceDate3) {
		this.serviceDate3 = serviceDate3;
	}

	public String getServiceDate4() {
		return serviceDate4;
	}

	public void setServiceDate4(String serviceDate4) {
		this.serviceDate4 = serviceDate4;
	}

	public String getPayeeMan() {
		return payeeMan;
	}

	public void setPayeeMan(String payeeMan) {
		this.payeeMan = payeeMan;
	}

	public Boolean getStates() {
		return states;
	}

	public void setStates(Boolean states) {
		this.states = states;
	}

	public TServiceCharge() {
		super();
	}

	public TServiceCharge(String id){
		super(id);
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	@Length(min=0, max=100, message="服务日期长度必须介于 0 和 100 之间")
	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
		this.serviceDate1=serviceDate.substring(0, 4);
		this.serviceDate2=serviceDate.substring(4, 6);
		this.serviceDate3=serviceDate.substring(7, 11);
		this.serviceDate4=serviceDate.substring(11, 13);
	}
	
	public Double getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(Double loanMoney) {
		this.loanMoney = loanMoney;
	}
	
	public Double getAccountbookMoney() {
		return accountbookMoney;
	}

	public void setAccountbookMoney(Double accountbookMoney) {
		this.accountbookMoney = accountbookMoney;
	}
	
	@Length(min=0, max=64, message="付款方式长度必须介于 0 和 64 之间")
	public String getModePayment() {
		return modePayment;
	}

	public void setModePayment(String modePayment) {
		this.modePayment = modePayment;
	}
	
	public Double getShouldMoney() {
		return shouldMoney;
	}

	public void setShouldMoney(Double shouldMoney) {
		this.shouldMoney = shouldMoney;
	}
	
	public Double getRealityMoney() {
		return realityMoney;
	}

	public void setRealityMoney(Double realityMoney) {
		this.realityMoney = realityMoney;
	}
	
	@Length(min=0, max=300, message="备注长度必须介于 0 和 300 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=40, message="审核状态长度必须介于 0 和 40 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public Date getBeginSignDate() {
		return beginSignDate;
	}

	public void setBeginSignDate(Date beginSignDate) {
		this.beginSignDate = beginSignDate;
	}
	
	public Date getEndSignDate() {
		return endSignDate;
	}

	public void setEndSignDate(Date endSignDate) {
		this.endSignDate = endSignDate;
	}
		
}