/**
tquotation * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.newcharge.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Area;

/**
 * 记账公司Entity
 * @author yang
 * @version 2016-01-15
 */
public class TChargecompany extends DataEntity<TChargecompany> {
	
	private static final long serialVersionUID = 1L;
	private String officeid;		// 公司Id
	private String chargecomname;		// 记账公司名字
	private String city;		// 所在城市
	private String contectperson;		// 联系人
	private String mobilenumber;		// 手机号码
	private String companynumber;		// 公司人数
	private String companyrunrange;		// 企业经营范围
	private String servicestatus;		// 0代表停止状态，1代表服务状态
	private String usestatus;		// 0代表试用客户状态，1代表正式客户状态
	private String legalname;		// 法人代表
	private String contectphone;		// 联系电话
	private String appendixtype;		// 身份证的附件类型
	private String cardnumber;		// 身份证号
	private String runnumber;		// 营业执照号码
	private String runappendix;		// 营业执照号码
	private String taxrenum;		// 税务登记号码
	private String taxappendx;		// 税务登记号码附件
	private String organcode;		// 组织机构代码
	private String codeappendx;		// 组织机构附件
	private String abutment;		// 对接人
	private String auditbill;		// 是否审核发票
	private String rapauditnum;		// 加急审核数量
	
	private String city1;//省份
	private String city2;//市
	private String city3;//区域县
	
	private String ctiy4;
	private Area area;	
	private String areaid;//区域的id
	private int countCompany;
	
	
	
	

	public int getCountCompany() {
		return countCompany;
	}

	public void setCountCompany(int countCompany) {
		this.countCompany = countCompany;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getCtiy4() {
		return ctiy4;
	}

	public void setCtiy4(String ctiy4) {
		this.ctiy4 = ctiy4;
	}

	private int sonCount;//
	
	public String getCity1() {
		return city1;
	}

	public void setCity1(String city1) {
		this.city1 = city1;
	}

	public String getCity2() {
		return city2;
	}

	public void setCity2(String city2) {
		this.city2 = city2;
	}

	public String getCity3() {
		return city3;
	}

	public void setCity3(String city3) {
		this.city3 = city3;
	}

	public int getSonCount() {
		return sonCount;
	}

	public void setSonCount(int sonCount) {
		this.sonCount = sonCount;
	}

	public TChargecompany() {
		super();
	}

	public TChargecompany(String id){
		super(id);
	}

	@Length(min=0, max=64, message="公司Id长度必须介于 0 和 64 之间")
	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
	
	@Length(min=0, max=64, message="记账公司名字长度必须介于 0 和 64 之间")
	public String getChargecomname() {
		return chargecomname;
	}

	public void setChargecomname(String chargecomname) {
		this.chargecomname = chargecomname;
	}
	
	@Length(min=0, max=200, message="所在城市长度必须介于 0 和 200 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=64, message="联系人长度必须介于 0 和 64 之间")
	public String getContectperson() {
		return contectperson;
	}

	public void setContectperson(String contectperson) {
		this.contectperson = contectperson;
	}
	
	@Length(min=0, max=64, message="手机号码长度必须介于 0 和 64 之间")
	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	
	@Length(min=0, max=200, message="公司人数长度必须介于 0 和 200 之间")
	public String getCompanynumber() {
		return companynumber;
	}

	public void setCompanynumber(String companynumber) {
		this.companynumber = companynumber;
	}
	
	@Length(min=0, max=64, message="企业经营范围长度必须介于 0 和 64 之间")
	public String getCompanyrunrange() {
		return companyrunrange;
	}

	public void setCompanyrunrange(String companyrunrange) {
		this.companyrunrange = companyrunrange;
	}
	
	@Length(min=0, max=1, message="0代表停止状态，1代表服务状态长度必须介于 0 和 1 之间")
	public String getServicestatus() {
		return servicestatus;
	}

	public void setServicestatus(String servicestatus) {
		this.servicestatus = servicestatus;
	}
	
	@Length(min=0, max=1, message="0代表试用客户状态，1代表正式客户状态长度必须介于 0 和 1 之间")
	public String getUsestatus() {
		return usestatus;
	}

	public void setUsestatus(String usestatus) {
		this.usestatus = usestatus;
	}
	
	@Length(min=0, max=64, message="法人代表长度必须介于 0 和 64 之间")
	public String getLegalname() {
		return legalname;
	}

	public void setLegalname(String legalname) {
		this.legalname = legalname;
	}
	
	@Length(min=0, max=64, message="联系电话长度必须介于 0 和 64 之间")
	public String getContectphone() {
		return contectphone;
	}

	public void setContectphone(String contectphone) {
		this.contectphone = contectphone;
	}
	
	@Length(min=0, max=100, message="身份证的附件类型长度必须介于 0 和 100 之间")
	public String getAppendixtype() {
		return appendixtype;
	}

	public void setAppendixtype(String appendixtype) {
		this.appendixtype = appendixtype;
	}
	
	@Length(min=0, max=100, message="身份证号长度必须介于 0 和 100 之间")
	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	
	@Length(min=0, max=100, message="营业执照号码长度必须介于 0 和 100 之间")
	public String getRunnumber() {
		return runnumber;
	}

	public void setRunnumber(String runnumber) {
		this.runnumber = runnumber;
	}
	
	@Length(min=0, max=100, message="营业执照号码长度必须介于 0 和 100 之间")
	public String getRunappendix() {
		return runappendix;
	}

	public void setRunappendix(String runappendix) {
		this.runappendix = runappendix;
	}
	
	@Length(min=0, max=100, message="税务登记号码长度必须介于 0 和 100 之间")
	public String getTaxrenum() {
		return taxrenum;
	}

	public void setTaxrenum(String taxrenum) {
		this.taxrenum = taxrenum;
	}
	
	@Length(min=0, max=100, message="税务登记号码附件长度必须介于 0 和 100 之间")
	public String getTaxappendx() {
		return taxappendx;
	}

	public void setTaxappendx(String taxappendx) {
		this.taxappendx = taxappendx;
	}
	
	@Length(min=0, max=100, message="组织机构代码长度必须介于 0 和 100 之间")
	public String getOrgancode() {
		return organcode;
	}

	public void setOrgancode(String organcode) {
		this.organcode = organcode;
	}
	
	@Length(min=0, max=100, message="组织机构附件长度必须介于 0 和 100 之间")
	public String getCodeappendx() {
		return codeappendx;
	}

	public void setCodeappendx(String codeappendx) {
		this.codeappendx = codeappendx;
	}
	
	@Length(min=0, max=64, message="对接人长度必须介于 0 和 64 之间")
	public String getAbutment() {
		return abutment;
	}

	public void setAbutment(String abutment) {
		this.abutment = abutment;
	}
	
	@Length(min=0, max=1, message="是否审核发票长度必须介于 0 和 1 之间")
	public String getAuditbill() {
		return auditbill;
	}

	public void setAuditbill(String auditbill) {
		this.auditbill = auditbill;
	}
	
	@Length(min=0, max=64, message="加急审核数量长度必须介于 0 和 64 之间")
	public String getRapauditnum() {
		return rapauditnum;
	}

	public void setRapauditnum(String rapauditnum) {
		this.rapauditnum = rapauditnum;
	}
	
}