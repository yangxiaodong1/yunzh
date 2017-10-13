/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.books.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 凭证摘要和科目Entity
 * @author zhangtong
 * @version 2015-12-04
 */
public class Subsidiaryledge extends DataEntity<Subsidiaryledge> {
	
	private static final long serialVersionUID = 1L;
	private String voucherTitleName;		// 凭证字名称
	private String voucherTitleNumber;		// 凭证字号
	private Date accountDate;		// 记账日期
	private String accountPeriod;		// 账期
	private String recieptNumber;		// 单据数
	private String recieptInfo;		// 单据信息
	private String totalAmount;		// 合计金额
	private String userName;		// 制单人
	private Date touchingDate;		// 制单时间
	private String checker;		// 审核人
	private Date checkDate;		// 审核时间
	private String isCheck;		// 审核状态
	private String tVId;		// 记账凭_凭证编号
	private String expRowNumber;		// 摘要行号
	private String exp;		// 摘要
	private String debit;		// 借方金额
	private String credit;		// 贷方金额
	private String accId;		// 科目编号
	private String fdbid;		// 公司客户编号
	private String accuntId;		// 公司编码
	private String accountName;		// 科目名称
	private String accLevel;		// 科目级别
	private String detail;		// 科目是否有子文件
	private Subsidiaryledge parent;		// 上一级科目id
	private String rootId;		// 根节点id
	private String groupId;		// 科目组ID
	private String dc;		// 借贷方向
	
	private String periodEnd;  //账期后
	public String getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}
	public Subsidiaryledge() {
		super();
	}

	public Subsidiaryledge(String id){
		super(id);
	}

	@Length(min=0, max=64, message="凭证字名称长度必须介于 0 和 64 之间")
	public String getVoucherTitleName() {
		return voucherTitleName;
	}

	public void setVoucherTitleName(String voucherTitleName) {
		this.voucherTitleName = voucherTitleName;
	}
	
	@Length(min=0, max=4, message="凭证字号长度必须介于 0 和 4 之间")
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
	
	@Length(min=0, max=6, message="账期长度必须介于 0 和 6 之间")
	public String getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	
	@Length(min=0, max=4, message="单据数长度必须介于 0 和 4 之间")
	public String getRecieptNumber() {
		return recieptNumber;
	}

	public void setRecieptNumber(String recieptNumber) {
		this.recieptNumber = recieptNumber;
	}
	
	@Length(min=0, max=200, message="单据信息长度必须介于 0 和 200 之间")
	public String getRecieptInfo() {
		return recieptInfo;
	}

	public void setRecieptInfo(String recieptInfo) {
		this.recieptInfo = recieptInfo;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Length(min=0, max=100, message="制单人长度必须介于 0 和 100 之间")
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
	
	@Length(min=0, max=100, message="审核人长度必须介于 0 和 100 之间")
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
	
	@Length(min=0, max=20, message="审核状态长度必须介于 0 和 20 之间")
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	@Length(min=0, max=64, message="记账凭_凭证编号长度必须介于 0 和 64 之间")
	public String getTVId() {
		return tVId;
	}

	public void setTVId(String tVId) {
		this.tVId = tVId;
	}
	
	@Length(min=0, max=64, message="摘要行号长度必须介于 0 和 64 之间")
	public String getExpRowNumber() {
		return expRowNumber;
	}

	public void setExpRowNumber(String expRowNumber) {
		this.expRowNumber = expRowNumber;
	}
	
	@Length(min=0, max=64, message="摘要长度必须介于 0 和 64 之间")
	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}
	
	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}
	
	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	@Length(min=1, max=11, message="科目编号长度必须介于 1 和 11 之间")
	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}
	
	@Length(min=0, max=64, message="公司客户编号长度必须介于 0 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	@Length(min=0, max=64, message="公司编码长度必须介于 0 和 64 之间")
	public String getAccuntId() {
		return accuntId;
	}

	public void setAccuntId(String accuntId) {
		this.accuntId = accuntId;
	}
	
	@Length(min=0, max=64, message="科目名称长度必须介于 0 和 64 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Length(min=0, max=64, message="科目级别长度必须介于 0 和 64 之间")
	public String getAccLevel() {
		return accLevel;
	}

	public void setAccLevel(String accLevel) {
		this.accLevel = accLevel;
	}
	
	@Length(min=0, max=64, message="科目是否有子文件长度必须介于 0 和 64 之间")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	@JsonBackReference
	public Subsidiaryledge getParent() {
		return parent;
	}

	public void setParent(Subsidiaryledge parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=64, message="根节点id长度必须介于 0 和 64 之间")
	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
	
	@Length(min=0, max=64, message="科目组ID长度必须介于 0 和 64 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@Length(min=0, max=20, message="借贷方向长度必须介于 0 和 20 之间")
	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}
	
}