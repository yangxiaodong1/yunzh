package com.thinkgem.jeesite.modules.newcharge.entity;

import java.util.Date;

/**
 * @author server1
 *
 */
public class TCountCompanynew {
    private Date date;
	private String dateStringDate;
	private String company;//新增代账公司数
	
	private String acount;//新增会计人员account
	
	private String cucount;//新增customer人数
	private Date createdate1;
	private Date  createdate2;
	private Date  createdate3;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDateStringDate() {
		return dateStringDate;
	}
	public void setDateStringDate(String dateStringDate) {
		this.dateStringDate = dateStringDate;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAcount() {
		return acount;
	}
	public void setAcount(String acount) {
		this.acount = acount;
	}
	public String getCucount() {
		return cucount;
	}
	public void setCucount(String cucount) {
		this.cucount = cucount;
	}
	public Date getCreatedate1() {
		return createdate1;
	}
	public void setCreatedate1(Date createdate1) {
		this.createdate1 = createdate1;
	}
	public Date getCreatedate2() {
		return createdate2;
	}
	public void setCreatedate2(Date createdate2) {
		this.createdate2 = createdate2;
	}
	public Date getCreatedate3() {
		return createdate3;
	}
	public void setCreatedate3(Date createdate3) {
		this.createdate3 = createdate3;
	}
	
	
	
	
	
}
