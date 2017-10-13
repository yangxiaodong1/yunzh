/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucherexp.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 显示凭证Entity
 * @author cy
 * @version 2016-02-19
 */
public class TVoucherexpvo extends DataEntity<TVoucherexpvo> {
	
	private static final long serialVersionUID = 1L;
	private String tvoucherexptvid;		// 凭证表的主键
	private String username;		// 记账人
	private String vouchertitlenumber;		//记账凭证
	private Date accountdate;		// 记凭证日期
	private double totalamount;		// 总金额
	private String expcomment;		// 批注字段
	private String commenresult;		// 批注状态
	private String count;		// 记录的条数
	private String showdebit;		// 页面展示的借方金额
	private String showcredit;		// 页面显示的贷方金额
	private String selecttvoucher;		// 查询最近账期
	private String money;		// 总金额
	private String tvoucherList;//凭证集
	
	public String getTvoucherList() {
		return tvoucherList;
	}

	public void setTvoucherList(String tvoucherList) {
		this.tvoucherList = tvoucherList;
	}

	public TVoucherexpvo() {
		super();
	}

	public TVoucherexpvo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="凭证表的主键长度必须介于 0 和 64 之间")
	public String getTvoucherexptvid() {
		return tvoucherexptvid;
	}

	public void setTvoucherexptvid(String tvoucherexptvid) {
		this.tvoucherexptvid = tvoucherexptvid;
	}
	
	@Length(min=0, max=64, message="记账人长度必须介于 0 和 64 之间")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=200, message="vouchertitlenumber长度必须介于 0 和 200 之间")
	public String getVouchertitlenumber() {
		return vouchertitlenumber;
	}

	public void setVouchertitlenumber(String vouchertitlenumber) {
		this.vouchertitlenumber = vouchertitlenumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAccountdate() {
		return accountdate;
	}

	public void setAccountdate(Date accountdate) {
		this.accountdate = accountdate;
	}
	
	public double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}
	
	@Length(min=0, max=300, message="批注字段长度必须介于 0 和 300 之间")
	public String getExpcomment() {
		return expcomment;
	}

	public void setExpcomment(String expcomment) {
		this.expcomment = expcomment;
	}
	
	@Length(min=0, max=100, message="批注状态长度必须介于 0 和 100 之间")
	public String getCommenresult() {
		return commenresult;
	}

	public void setCommenresult(String commenresult) {
		this.commenresult = commenresult;
	}
	
	@Length(min=0, max=11, message="记录的条数长度必须介于 0 和 11 之间")
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	@Length(min=0, max=255, message="页面展示的借方金额长度必须介于 0 和 255 之间")
	public String getShowdebit() {
		return showdebit;
	}

	public void setShowdebit(String showdebit) {
		this.showdebit = showdebit;
	}
	
	@Length(min=0, max=255, message="页面显示的贷方金额长度必须介于 0 和 255 之间")
	public String getShowcredit() {
		return showcredit;
	}

	public void setShowcredit(String showcredit) {
		this.showcredit = showcredit;
	}
	
	@Length(min=0, max=255, message="查询最近账期长度必须介于 0 和 255 之间")
	public String getSelecttvoucher() {
		return selecttvoucher;
	}

	public void setSelecttvoucher(String selecttvoucher) {
		this.selecttvoucher = selecttvoucher;
	}
	
	@Length(min=0, max=255, message="总金额长度必须介于 0 和 255 之间")
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
}