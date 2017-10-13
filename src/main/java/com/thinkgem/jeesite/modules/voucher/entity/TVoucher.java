/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucher.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 记账凭证Entity
 * @author 记账凭证
 * @version 2015-10-30
 */
public class TVoucher extends DataEntity<TVoucher> {
	
	private static final long serialVersionUID = 1L;
	private String voucherTitleName;		// voucher_title_name
	private String voucherTitleNumber;		// 记账编号
	private Date accountDate;				// 记凭证日期
	private String accountPeriod;		//账期查询条件 
	private String recieptNumber;		// reciept_number
	private String recieptInfo;		// reciept_info
	private double totalAmount;		// total_amount
	private String userName;		// 记账人
	private Date touchingDate;		// touching_date
	private String checker;		// checker
	private Date checkDate;		// check_date
	private String isCheck;		// is_check
	
	/**
	 * 自己新加入的字段 cy
	 * **/
	private String totalAmountshow;	//显示总和
	private String fdbid;		//外键，客户表外键
	private int count;		//记录的条数
	private String money;	//总金额（人民币大写）
	private String expComment;//批注字段
	private String commenResult;	//批注状态
	
	public String getTotalAmountshow() {
		return totalAmountshow;
	}

	public void setTotalAmountshow(String totalAmountshow) {
		this.totalAmountshow = totalAmountshow;
	}
	
	public String getCommenResult() {
		return commenResult;
	}

	public void setCommenResult(String commenResult) {
		this.commenResult = commenResult;
	}

	private String ifAuto;



	public String getExpComment() {
		return expComment;
	}

	public void setExpComment(String expComment) {
		this.expComment = expComment;
	}


	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	//以上为新加的三个字段
	public TVoucher() {
		super();
	}

	public TVoucher(String id){
		super(id);
	}

	@Length(min=0, max=64, message="voucher_title_name长度必须介于 0 和 64 之间")
	public String getVoucherTitleName() {
		return voucherTitleName;
	}

	public void setVoucherTitleName(String voucherTitleName) {
		this.voucherTitleName = voucherTitleName;
	}
	
	@Length(min=0, max=4, message="voucher_title_number长度必须介于 0 和 4 之间")
	public String getVoucherTitleNumber() {
		return voucherTitleNumber;
	}

	public void setVoucherTitleNumber(String voucherTitleNumber) {
		this.voucherTitleNumber = voucherTitleNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}
	
	@Length(min=0, max=6, message="account_period长度必须介于 0 和 6 之间")
	public String getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	
	@Length(min=0, max=4, message="reciept_number长度必须介于 0 和 4 之间")
	public String getRecieptNumber() {
		return recieptNumber;
	}

	public void setRecieptNumber(String recieptNumber) {
		this.recieptNumber = recieptNumber;
	}
	
	@Length(min=0, max=200, message="reciept_info长度必须介于 0 和 200 之间")
	public String getRecieptInfo() {
		return recieptInfo;
	}

	public void setRecieptInfo(String recieptInfo) {
		this.recieptInfo = recieptInfo;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Length(min=0, max=100, message="user_name长度必须介于 0 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTouchingDate() {
		return touchingDate;
	}

	public void setTouchingDate(Date touchingDate) {
		this.touchingDate = touchingDate;
	}
	
	@Length(min=0, max=100, message="checker长度必须介于 0 和 100 之间")
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@Length(min=0, max=20, message="is_check长度必须介于 0 和 20 之间")
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getIfAuto() {
		return ifAuto;
	}

	public void setIfAuto(String ifAuto) {
		this.ifAuto = ifAuto;
	}
	
	
}