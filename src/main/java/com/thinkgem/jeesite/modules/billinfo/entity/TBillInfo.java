/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.billinfo.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.account.entity.TAccount;

/**
 * 发票信息Entity
 * 
 * @author 发票信息
 * @version 2015-10-21
 */
public class TBillInfo extends DataEntity<TBillInfo> implements Cloneable {

	private static final long serialVersionUID = 1L;
	private String uploadPeriod; // upload_period
	private String adjustPeriod; // adjust_period
	private String tBId; // t_b_id 0代表发票，值为1代表银行票
	private String billKind; // bill_kind
	private String imageUrl; // image_url
	private String payName; // pay_name
	private String recieveName; // recieve_name
	private Date signDate; // sign_date
	private String abstractMsg; // abstract
	private String amount; // amount
	private String tax; // tax
	private String totalPrice; // total_price
	private String invoiceCode; // invoice_code
	private String invoiceNumber; // invoice_number
	private String payBank; // pay_bank
	private String payAccount; // pay_account
	private String payerTaxIdentificationNumber; // payer_tax_identification_number
	private String payAccountName; // pay_account_name
	private String isExpire; // 0未过期 1过期
	private String relateVoucher; // relate_voucher
	private String urgentState; // 0、正常 1、加急
	private String billStatus; // 0、待审验 1、退回（扫描质量差，看不清） 2、已审验 3、已做账 4、作废 5、错误  add by wub 6 跨期 (从) 7 跨期（到）
	private String errorReason; // 0、正常 1、错误
	private String cancelReason; // 0、待审验 1、已审验 2、已做账 3、作废
	private String customerId; // customer_id

	private boolean scrap = true;

	private boolean error = true;

	private String statusName;

	private String tBIdName;

	private String checkPerson;// 审核人

	private Date checkDate;// 审核日期

	private TAccount debitAccount;
	
	private TAccount creditAccount;
	
	//private int ifAmortize; // 0 不是 1 是自动摊销
	
	private boolean hasSaveVoucer = false;
	
	
	//////////////////////////////////yunzh 2 add begin///////////////
	private String billInfo;//发票信息（新增字段）关联到票据类型信息表t_bill_type_info（新增表），这个表里面的数据，根据所属的票据种类：发票，发票类型不同，发票信息值不同，包括：默认、火车票、飞机票、出租车票。
	
	private String imageName;//图片文件名称
	
	private String uuid;//文件在内容库中的唯一ID
	
	private String recieveTaxIdentificationNumber;//收款方纳税人识别号
	
	private String departurePlace;//出发地
	
	private String arrivingPlace;//到达地
	
	private String dispatchingFlag;//派工标志
	
	private int feedbackFlag;//反馈标记 0：默认 1：看不清票据  2：错误票据
	
	//private int errorFlag;//错误标记 0： 1： 2：

	//private String imageSize;//
	//private String billExamine;//
	
	private String dValue;
	//////////////////////////////////////////////////////////////////
	
	
	public TBillInfo() {
		super();
	}

	public TBillInfo(String id) {
		super(id);
	}

	@Length(min = 0, max = 6, message = "upload_period长度必须介于 0 和 6 之间")
	public String getUploadPeriod() {
		return uploadPeriod;
	}

	public void setUploadPeriod(String uploadPeriod) {
		this.uploadPeriod = uploadPeriod;
	}

	@Length(min = 0, max = 6, message = "adjust_period长度必须介于 0 和 6 之间")
	public String getAdjustPeriod() {
		return adjustPeriod;
	}

	public void setAdjustPeriod(String adjustPeriod) {
		this.adjustPeriod = adjustPeriod;
	}

	@Length(min = 0, max = 64, message = "t_b_id长度必须介于 0 和 64 之间")
	public String getTBId() {
		return tBId;
	}

	public void setTBId(String tBId) {
		this.tBId = tBId;
	}

	@Length(min = 0, max = 64, message = "bill_kind长度必须介于 0 和 64 之间")
	public String getBillKind() {
		return billKind;
	}

	public void setBillKind(String billKind) {
		this.billKind = billKind;
	}

	@Length(min = 0, max = 64, message = "image_url长度必须介于 0 和 64 之间")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Length(min = 0, max = 64, message = "pay_name长度必须介于 0 和 64 之间")
	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	@Length(min = 0, max = 64, message = "recieve_name长度必须介于 0 和 64 之间")
	public String getRecieveName() {
		return recieveName;
	}

	public void setRecieveName(String recieveName) {
		this.recieveName = recieveName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	@Length(min = 0, max = 64, message = "abstract长度必须介于 0 和 64 之间")
	public String getAbstractMsg() {
		return abstractMsg;
	}

	public void setAbstractMsg(String abstractMsg) {
		this.abstractMsg = abstractMsg;
	}

	public String getAmount() {
		return StringUtils.isNotBlank(amount) ? amount : "0";
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTax() {
		return StringUtils.isNotBlank(tax) ? tax : "0";
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTotalPrice() {
		return StringUtils.isNotBlank(totalPrice) ? totalPrice : "0";
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Length(min = 0, max = 64, message = "invoice_code长度必须介于 0 和 64 之间")
	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	@Length(min = 0, max = 64, message = "invoice_number长度必须介于 0 和 64 之间")
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@Length(min = 0, max = 64, message = "pay_bank长度必须介于 0 和 64 之间")
	public String getPayBank() {
		return payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	@Length(min = 0, max = 64, message = "pay_account长度必须介于 0 和 64 之间")
	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	@Length(min = 0, max = 64, message = "payer_tax_identification_number长度必须介于 0 和 64 之间")
	public String getPayerTaxIdentificationNumber() {
		return payerTaxIdentificationNumber;
	}

	public void setPayerTaxIdentificationNumber(
			String payerTaxIdentificationNumber) {
		this.payerTaxIdentificationNumber = payerTaxIdentificationNumber;
	}

	@Length(min = 0, max = 64, message = "pay_account_name长度必须介于 0 和 64 之间")
	public String getPayAccountName() {
		return payAccountName;
	}

	public void setPayAccountName(String payAccountName) {
		this.payAccountName = payAccountName;
	}

	@Length(min = 0, max = 2, message = "0未过期            1过期长度必须介于 0 和 2 之间")
	public String getIsExpire() {
		return isExpire;
	}

	public void setIsExpire(String isExpire) {
		this.isExpire = isExpire;
	}

	@Length(min = 0, max = 64, message = "relate_voucher长度必须介于 0 和 64 之间")
	public String getRelateVoucher() {
		return relateVoucher;
	}

	public void setRelateVoucher(String relateVoucher) {
		this.relateVoucher = relateVoucher;
	}

	@Length(min = 0, max = 64, message = "0、正常            1、加急长度必须介于 0 和 64 之间")
	public String getUrgentState() {
		return urgentState;
	}

	public void setUrgentState(String urgentState) {
		this.urgentState = urgentState;
	}

	@Length(min = 0, max = 64, message = "0、待审验            1、退回（扫描质量差，看不清）            2、已审验            3、已做账            4、作废            5、错误长度必须介于 0 和 64 之间")
	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	@Length(min = 0, max = 64, message = "0、正常            1、错误长度必须介于 0 和 64 之间")
	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	@Length(min = 0, max = 64, message = "0、待审验            1、已审验            2、已做账            3、作废长度必须介于 0 和 64 之间")
	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	@Length(min = 0, max = 100, message = "customer_id长度必须介于 0 和 100 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public boolean isScrap() {
		return scrap;
	}

	public void setScrap(boolean scrap) {
		this.scrap = scrap;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String gettBIdName() {
		return tBIdName;
	}

	public void settBIdName(String tBIdName) {
		this.tBIdName = tBIdName;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String gettBId() {
		return tBId;
	}

	public void settBId(String tBId) {
		this.tBId = tBId;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public TAccount getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(TAccount debitAccount) {
		this.debitAccount = debitAccount;
	}

	public TAccount getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(TAccount creditAccount) {
		this.creditAccount = creditAccount;
	}

	public boolean isHasSaveVoucer() {
		return hasSaveVoucer;
	}

	public void setHasSaveVoucer(boolean hasSaveVoucer) {
		this.hasSaveVoucer = hasSaveVoucer;
	}

	public String getBillInfo() {
		return billInfo;
	}

	public void setBillInfo(String billInfo) {
		this.billInfo = billInfo;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRecieveTaxIdentificationNumber() {
		return recieveTaxIdentificationNumber;
	}

	public void setRecieveTaxIdentificationNumber(
			String recieveTaxIdentificationNumber) {
		this.recieveTaxIdentificationNumber = recieveTaxIdentificationNumber;
	}

	public String getDeparturePlace() {
		return departurePlace;
	}

	public void setDeparturePlace(String departurePlace) {
		this.departurePlace = departurePlace;
	}

	public String getArrivingPlace() {
		return arrivingPlace;
	}

	public void setArrivingPlace(String arrivingPlace) {
		this.arrivingPlace = arrivingPlace;
	}

	public String getDispatchingFlag() {
		return dispatchingFlag;
	}

	public void setDispatchingFlag(String dispatchingFlag) {
		this.dispatchingFlag = dispatchingFlag;
	}

	public int getFeedbackFlag() {
		return feedbackFlag;
	}

	public void setFeedbackFlag(int feedbackFlag) {
		this.feedbackFlag = feedbackFlag;
	}

	public String getdValue() {
		return dValue;
	}

	public void setdValue(String dValue) {
		this.dValue = dValue;
	}
}