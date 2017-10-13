package com.thinkgem.jeesite.modules.newcharge.entity;

public class TCountCompany {
	private String city;
	
	private String date;
	
	private String customer;
	
	private String companyUsers;
	
	private String companyNumber;
	
	private String nowDate;//今天的日期
	private String yesDate;//昨天的日期
	private int top;//排名
	
	
	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getYesDate() {
		return yesDate;
	}

	public void setYesDate(String yesDate) {
		this.yesDate = yesDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCompanyUsers() {
		return companyUsers;
	}

	public void setCompanyUsers(String companyUsers) {
		this.companyUsers = companyUsers;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	
}
