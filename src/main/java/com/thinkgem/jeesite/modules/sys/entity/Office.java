/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;
import com.thinkgem.jeesite.modules.newcharge.entity.TChargecompany;

/**
 * 机构Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Office extends TreeEntity<Office> {

	private static final long serialVersionUID = 1L;
	private Office parent;	// 父级编号
	private String parentIds; // 所有父级编号
	private String parentId;		// 父级机构id
	private String pId;		// 父级机构id
	private Area area;		// 归属区域
	private String code; 	// 机构编码
	private String name; 	// 机构名称
	private Integer sort;		// 排序
	private String type; 	// 机构类型（1：公司；2：部门；3：小组）
	private String grade; 	// 机构等级（1：一级；2：二级；3：三级；4：四级）
	private String address; // 联系地址
	private String zipCode; // 邮政编码
	private String master; 	// 负责人
	private String phone; 	// 电话
	private String fax; 	// 传真
	private String email; 	// 邮箱
	private String useable;//是否可用
	private User primaryPerson;//主负责人
	private User deputyPerson;//副负责人
	private List<String> childDeptList;//快速添加子部门
	
	
	

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public Office(){
		super();
//		this.sort = 30;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
		this.pId=parentId;
	}

	public Office(String id){
		super(id);
	}
	
	public List<String> getChildDeptList() {
		return childDeptList;
	}

	public void setChildDeptList(List<String> childDeptList) {
		this.childDeptList = childDeptList;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public User getPrimaryPerson() {
		return primaryPerson;
	}

	public void setPrimaryPerson(User primaryPerson) {
		this.primaryPerson = primaryPerson;
	}

	public User getDeputyPerson() {
		return deputyPerson;
	}

	public void setDeputyPerson(User deputyPerson) {
		this.deputyPerson = deputyPerson;
	}

	public Office getParent() {
		return parent;
	}

	public void setParent(Office parent) {
		this.parent = parent;
		//this.pId = parent.getId();
	}

	@Length(min=1, max=2000)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@NotNull
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Length(min=1, max=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
//
//	public Integer getSort() {
//		return sort;
//	}
//
//	public void setSort(Integer sort) {
//		this.sort = sort;
//	}
	
	@Length(min=1, max=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=1, max=1)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Length(min=0, max=255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min=0, max=100)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Length(min=0, max=100)
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	@Length(min=0, max=200)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=0, max=200)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Length(min=0, max=200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min=0, max=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
	
	@Override
	public String toString() {
		return name;
	}
	/**------以下是合并的库的字段------**/
   private TChargecompany chargecomname;//要插入的这一个实体类yang
   private TChargecompany city;
   private TChargecompany contectperson;
   private TChargecompany mobilenumber;
   private TChargecompany companynumber;
   private TChargecompany companyrunrange;
   private TChargecompany servicestatus;
   private TChargecompany usestatus;
   private TChargecompany legalname;
   private TChargecompany contectphone;
   private TChargecompany appendixtype;
   private TChargecompany cardnumber;
   private TChargecompany runnumber;
   private TChargecompany runappendix;
   private TChargecompany taxrenum;
   private TChargecompany taxappendx;
   private TChargecompany  organcode;
   private TChargecompany  codeappendx;
   private TChargecompany  abutment;
   private TChargecompany  auditbill;
   private TChargecompany  rapauditnum;
   private String province     ;//省
   private String  municipality ;//市
   private String  district;//县
   private String  masteraccount;//唯一标识

   public String getProvince() {
	return province;
}

public void setProvince(String province) {
	this.province = province;
}

public String getMunicipality() {
	return municipality;
}

public void setMunicipality(String municipality) {
	this.municipality = municipality;
}

public String getDistrict() {
	return district;
}

public void setDistrict(String district) {
	this.district = district;
}

public String getMasteraccount() {
	return masteraccount;
}

public void setMasteraccount(String masteraccount) {
	this.masteraccount = masteraccount;
}
private int countCompany;
   private int sonCount;//


public int getSonCount() {
	return sonCount;
}

public void setSonCount(int sonCount) {
	this.sonCount = sonCount;
}

public int getCountCompany() {
	return countCompany;
}

public void setCountCompany(int countCompany) {
	this.countCompany = countCompany;
}

public TChargecompany getChargecomname() {
	return chargecomname;
}

public void setChargecomname(TChargecompany chargecomname) {
	this.chargecomname = chargecomname;
}

public TChargecompany getCity() {
	return city;
}

public void setCity(TChargecompany city) {
	this.city = city;
}

public TChargecompany getContectperson() {
	return contectperson;
}

public void setContectperson(TChargecompany contectperson) {
	this.contectperson = contectperson;
}

public TChargecompany getMobilenumber() {
	return mobilenumber;
}

public void setMobilenumber(TChargecompany mobilenumber) {
	this.mobilenumber = mobilenumber;
}

public TChargecompany getCompanynumber() {
	return companynumber;
}

public void setCompanynumber(TChargecompany companynumber) {
	this.companynumber = companynumber;
}

public TChargecompany getCompanyrunrange() {
	return companyrunrange;
}

public void setCompanyrunrange(TChargecompany companyrunrange) {
	this.companyrunrange = companyrunrange;
}

public TChargecompany getServicestatus() {
	return servicestatus;
}

public void setServicestatus(TChargecompany servicestatus) {
	this.servicestatus = servicestatus;
}

public TChargecompany getUsestatus() {
	return usestatus;
}

public void setUsestatus(TChargecompany usestatus) {
	this.usestatus = usestatus;
}

public TChargecompany getLegalname() {
	return legalname;
}

public void setLegalname(TChargecompany legalname) {
	this.legalname = legalname;
}

public TChargecompany getContectphone() {
	return contectphone;
}

public void setContectphone(TChargecompany contectphone) {
	this.contectphone = contectphone;
}

public TChargecompany getAppendixtype() {
	return appendixtype;
}

public void setAppendixtype(TChargecompany appendixtype) {
	this.appendixtype = appendixtype;
}

public TChargecompany getCardnumber() {
	return cardnumber;
}

public void setCardnumber(TChargecompany cardnumber) {
	this.cardnumber = cardnumber;
}

public TChargecompany getRunnumber() {
	return runnumber;
}

public void setRunnumber(TChargecompany runnumber) {
	this.runnumber = runnumber;
}

public TChargecompany getRunappendix() {
	return runappendix;
}

public void setRunappendix(TChargecompany runappendix) {
	this.runappendix = runappendix;
}

public TChargecompany getTaxrenum() {
	return taxrenum;
}

public void setTaxrenum(TChargecompany taxrenum) {
	this.taxrenum = taxrenum;
}

public TChargecompany getTaxappendx() {
	return taxappendx;
}

public void setTaxappendx(TChargecompany taxappendx) {
	this.taxappendx = taxappendx;
}

public TChargecompany getOrgancode() {
	return organcode;
}

public void setOrgancode(TChargecompany organcode) {
	this.organcode = organcode;
}

public TChargecompany getCodeappendx() {
	return codeappendx;
}

public void setCodeappendx(TChargecompany codeappendx) {
	this.codeappendx = codeappendx;
}

public TChargecompany getAbutment() {
	return abutment;
}

public void setAbutment(TChargecompany abutment) {
	this.abutment = abutment;
}

public TChargecompany getAuditbill() {
	return auditbill;
}

public void setAuditbill(TChargecompany auditbill) {
	this.auditbill = auditbill;
}

public TChargecompany getRapauditnum() {
	return rapauditnum;
}

public void setRapauditnum(TChargecompany rapauditnum) {
	this.rapauditnum = rapauditnum;
}
 
}