/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Calendar;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.workterrace.entity.WagesVo;
/**
 * 客户Entity
 * @author cy
 * @version 2015-11-23
 */
public class TCustomer extends DataEntity<TCustomer> {
	
	private static final long serialVersionUID = 1L;
	private String id;					//客户编号
	private String customerName;		// 公司名称
	private String abbrName;		// 公司简称
	private String industry;		// 行业
	private String legalRepresentative;		// 法人代表
	private String idNumber;		// 身份证号
	private String businessLicenseNumber;		// 营业执照号
	private String organizationCode;		// 组织机构代码
	private Date establishmentDate;		// 成立日期
	private Date startDate;		// 开始代帐日期
	private String supervisor;		//客户主管
	private String auditPerson;		// 审核人
	private String contactPerson;		// 联系人
	private String officePhone="";		// 办公电话
	private String phoneNumber;		// 电话号码
	private String mobilePhone;		// 手机号码
	private String faxNumber;		// 传真号码
	private String qq;		// QQ
	private String eMail;		// 电子邮件
	private String other;		// 其他
	private String detailedAddress;		// 详细地址
	private String valueAddedTax;		// 增值税性质
	private Integer taxRate;		// 税率
	private String stateTaxRegistrationNumber;		// 国税税务登记号
	private String computerCode1;		// 电脑编号1
	private String stateTaxBureau;		// 国税主管税务分局（科）
	private String remarks1;		// 备注1
	private String localTaxRegistrationNumber;		// 地税税务登记号
	private String computerCode2;		// 电脑编号2
	private String localTaxBureau;		// 地税主管税务分局（科）
	private String remarks2;		// 备注2
	private String remarks3;		// 备注信息
	/**
	 * 新加的字段
	 * **/
	private String currentperiod;	//当前记账账期
	private int system;  //会计制度
	private Date certificatesmature;	//证书到期日期
	private String certificatesmatureNew;//证书到期时间字符串
	public String getCertificatesmatureNew() {
		return certificatesmatureNew;
	}

	public void setCertificatesmatureNew(String certificatesmatureNew) {
		this.certificatesmatureNew = certificatesmatureNew;
	}

	private String statetaxregistrationadmin;  //国家税务登记专管员
	private String localtaxregistrationadmin;  //地税税务登记管理员 
	private String initperiod; //起始账期z
	private String latelyperiod; //最近账期
	private String serviceexpirationdate="";	//服务到期时间
	private String carryForwardPercentage; // 结转百分比 add by wub 20150116
	private String byYear=String.valueOf(Calendar.getInstance().get(Calendar.YEAR)); //记账年份条件
	private String byYearName=byYear+"年";		// 记账年份展示
	private String[] montharray=new String[]{"06"," ","none","09"," ","none","05"," ","none"};		// 12月记账状态
	private String byYearMonth=byYear+String.valueOf(Calendar.getInstance().get(Calendar.MONTH)<9 ? "0"+(Calendar.getInstance().get(Calendar.MONTH)+1) : (Calendar.getInstance().get(Calendar.MONTH)+1));		//记账条件年月
	private String doneCount;
	private String doIngCount;
	private String doWillCount;
	private WagesVo wagesVo=new WagesVo();
	private String upContent;
	private String officeId;
	private String supervisorName; //负责人姓名
	private String urgent; //是否加急0:不加急1:加急
	private String outOfService="0"; //是否停止服务0:不停止1:停止
	private String search; //搜索条件
	
	private String officeCompanyName;	//记账员所在公司名称	add by zt
	
	
	
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getUrgent() {
		return urgent;
	}

	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}

	public String getOutOfService() {
		return outOfService;
	}

	public void setOutOfService(String outOfService) {
		this.outOfService = outOfService;
	}

	public String getLatelyperiod() {
		return latelyperiod;
	}

	public void setLatelyperiod(String latelyperiod) {
		this.latelyperiod = latelyperiod;
	}
	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getUpContent() {
		return upContent;
	}

	public void setUpContent(String upContent) {
		//this.montharray=new String[]{" ","none"," ","none"," ","none"};
		String[] ss=new String[]{"06"," ","none","09"," ","none","05"," ","none"};
		String[] upContentArray=upContent.split(",");
		for (int i = 0 ; i <upContentArray.length ; i++ ) {
			if(upContentArray[i].indexOf("信息公示已完成")>-1){
		    	  ss[0]=upContentArray[i].substring(7, 9);
		    	  ss[1]="none";
		    	  ss[2]=" ";
		    	  continue;
		      }
			if(upContentArray[i].indexOf("残疾人保证金缴纳")>-1){
		    	  ss[3]=upContentArray[i].substring(8, 10);
		    	  ss[4]="none";
		    	  ss[5]=" ";
		    	  continue;
		      }
			if(upContentArray[i].indexOf("企业所得税汇算缴纳")>-1){
		    	  ss[6]=upContentArray[i].substring(9, 11);
		    	  ss[7]="none";
		    	  ss[8]=" ";
		    	  continue;
		      }
		
		}
		this.montharray=ss;
		this.upContent=upContent;
	}
	public WagesVo getWagesVo() {
		return wagesVo;
	}

	public void setWagesVo(WagesVo wagesVo) {
		this.wagesVo = wagesVo;
	}


	public String getDoneCount() {
		return doneCount;
	}

	public void setDoneCount(String doneCount) {
		this.doneCount = doneCount;
	}

	public String getDoIngCount() {
		return doIngCount;
	}

	public void setDoIngCount(String doIngCount) {
		this.doIngCount = doIngCount;
	}

	public String getDoWillCount() {
		return doWillCount;
	}

	public void setDoWillCount(String doWillCount) {
		this.doWillCount = doWillCount;
	}

	public String getByYearMonth() {
		return byYearMonth;
	}

	public void setByYearMonth(String byYearMonth) {
		this.byYearMonth = byYearMonth;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String[] getMontharray() {
		return montharray;
	}

	public void setMontharray(String[] montharray) {
		this.montharray = montharray;
	}
	public String getServiceexpirationdate() {
		return serviceexpirationdate;
	}

	public void setServiceexpirationdate(String serviceexpirationdate) {
		this.serviceexpirationdate = serviceexpirationdate;
	}

	public String getInitperiod() {
		return initperiod;
	}

	public void setInitperiod(String initperiod) {
		this.initperiod = initperiod;
	}

	public String getCurrentperiod() {
		return currentperiod;
	}

	public void setCurrentperiod(String currentperiod) {
		this.currentperiod = currentperiod;
	}
	public TCustomer() {
		super();
	}
	public String getByYearName() {
		return byYearName;
	}

	public void setByYearName(String byYearName) {
		this.byYearName = this.byYear+"年";
	}

	@Length(min=0, max=64, message="年份长度必须介于 0 和 64 之间")
	public String getByYear() {
		return byYear;
	}

	public void setByYear(String byYear) {
		this.byYear = byYear;
		this.byYearName=byYear+"年";
		this.byYearMonth=byYear+String.valueOf(Calendar.getInstance().get(Calendar.MONTH)<9 ? "0"+(Calendar.getInstance().get(Calendar.MONTH)+1) : (Calendar.getInstance().get(Calendar.MONTH)+1));
	}
	public TCustomer(String id){
		super(id);
	}
	
	public int getSystem() {
		return system;
	}

	public void setSystem(int system) {
		this.system = system;
	}

	public Date getCertificatesmature() {
		return certificatesmature;
	}

	public void setCertificatesmature(Date certificatesmature) {
		this.certificatesmature = certificatesmature;
	}

	public String getStatetaxregistrationadmin() {
		return statetaxregistrationadmin;
	}

	public void setStatetaxregistrationadmin(String statetaxregistrationadmin) {
		this.statetaxregistrationadmin = statetaxregistrationadmin;
	}

	public String getLocaltaxregistrationadmin() {
		return localtaxregistrationadmin;
	}

	public void setLocaltaxregistrationadmin(String localtaxregistrationadmin) {
		this.localtaxregistrationadmin = localtaxregistrationadmin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Length(min=0, max=100, message="公司名称长度必须介于 0 和 100 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=50, message="公司简称长度必须介于 0 和 50 之间")
	public String getAbbrName() {
		return abbrName;
	}

	public void setAbbrName(String abbrName) {
		this.abbrName = abbrName;
	}
	
	@Length(min=0, max=50, message="行业长度必须介于 0 和 50 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Length(min=0, max=20, message="法人代表长度必须介于 0 和 20 之间")
	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	
	@Length(min=0, max=15, message="身份证号长度必须介于 0 和 15 之间")
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@Length(min=0, max=20, message="营业执照号长度必须介于 0 和 20 之间")
	public String getBusinessLicenseNumber() {
		return businessLicenseNumber;
	}

	public void setBusinessLicenseNumber(String businessLicenseNumber) {
		this.businessLicenseNumber = businessLicenseNumber;
	}
	
	@Length(min=0, max=20, message="组织机构代码长度必须介于 0 和 20 之间")
	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Length(min=0, max=100, message="客户主管长度必须介于 0 和 100 之间")
	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	
	@Length(min=0, max=100, message="审核人长度必须介于 0 和 100 之间")
	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}
	
	@Length(min=0, max=20, message="联系人长度必须介于 0 和 20 之间")
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	@Length(min=0, max=20, message="办公电话长度必须介于 0 和 20 之间")
	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	
	@Length(min=0, max=20, message="电话号码长度必须介于 0 和 20 之间")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Length(min=0, max=20, message="手机号码长度必须介于 0 和 20 之间")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Length(min=0, max=20, message="传真号码长度必须介于 0 和 20 之间")
	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	
	@Length(min=0, max=20, message="QQ长度必须介于 0 和 20 之间")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@Length(min=0, max=50, message="电子邮件长度必须介于 0 和 50 之间")
	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	
	@Length(min=0, max=100, message="其他长度必须介于 0 和 100 之间")
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	@Length(min=0, max=100, message="详细地址长度必须介于 0 和 100 之间")
	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}
	
	public String getValueAddedTax() {
		return valueAddedTax;
	}

	public void setValueAddedTax(String valueAddedTax) {
		this.valueAddedTax = valueAddedTax;
	}
	
	public Integer getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Integer taxRate) {
		this.taxRate = taxRate;
	}
	
	@Length(min=0, max=20, message="国税税务登记号长度必须介于 0 和 20 之间")
	public String getStateTaxRegistrationNumber() {
		return stateTaxRegistrationNumber;
	}

	public void setStateTaxRegistrationNumber(String stateTaxRegistrationNumber) {
		this.stateTaxRegistrationNumber = stateTaxRegistrationNumber;
	}
	
	@Length(min=0, max=20, message="电脑编号1长度必须介于 0 和 20 之间")
	public String getComputerCode1() {
		return computerCode1;
	}

	public void setComputerCode1(String computerCode1) {
		this.computerCode1 = computerCode1;
	}
	
	@Length(min=0, max=20, message="国税主管税务分局（科）长度必须介于 0 和 20 之间")
	public String getStateTaxBureau() {
		return stateTaxBureau;
	}

	public void setStateTaxBureau(String stateTaxBureau) {
		this.stateTaxBureau = stateTaxBureau;
	}
	
	@Length(min=0, max=200, message="备注1长度必须介于 0 和 200 之间")
	public String getRemarks1() {
		return remarks1;
	}

	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}
	
	@Length(min=0, max=20, message="地税税务登记号长度必须介于 0 和 20 之间")
	public String getLocalTaxRegistrationNumber() {
		return localTaxRegistrationNumber;
	}

	public void setLocalTaxRegistrationNumber(String localTaxRegistrationNumber) {
		this.localTaxRegistrationNumber = localTaxRegistrationNumber;
	}
	
	@Length(min=0, max=20, message="电脑编号2长度必须介于 0 和 20 之间")
	public String getComputerCode2() {
		return computerCode2;
	}

	public void setComputerCode2(String computerCode2) {
		this.computerCode2 = computerCode2;
	}
	
	@Length(min=0, max=20, message="地税主管税务分局（科）长度必须介于 0 和 20 之间")
	public String getLocalTaxBureau() {
		return localTaxBureau;
	}

	public void setLocalTaxBureau(String localTaxBureau) {
		this.localTaxBureau = localTaxBureau;
	}
	
	@Length(min=0, max=200, message="备注2长度必须介于 0 和 200 之间")
	public String getRemarks2() {
		return remarks2;
	}

	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}
	
	@Length(min=0, max=255, message="备注信息长度必须介于 0 和 255 之间")
	public String getRemarks3() {
		return remarks3;
	}

	public void setRemarks3(String remarks3) {
		this.remarks3 = remarks3;
	}

	public String getCarryForwardPercentage() {
		return carryForwardPercentage;
	}

	public void setCarryForwardPercentage(String carryForwardPercentage) {
		this.carryForwardPercentage = carryForwardPercentage;
	}

	public String getOfficeCompanyName() {
		return officeCompanyName;
	}

	public void setOfficeCompanyName(String officeCompanyName) {
		this.officeCompanyName = officeCompanyName;
	}
	
}