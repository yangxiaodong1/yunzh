/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cashflow.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 现金流量表Entity
 * @author zhangtong
 * @version 2015-12-14
 */
public class TCashflow extends DataEntity<TCashflow> {
	
	private static final long serialVersionUID = 1L;
	private String fdbid;		// fdbid
	private String reportitem;		// reportitem
	private String groupid;		// groupid
	private String yearperiod;		// yearperiod
	private String fcur;		// fcur
	private String ytdendamount;		// ytdendamount
	private String ytdbeginamount;		// ytdbeginamount
	private String lytdendamount;		// lytdendamount
	private String lytdbeginamount;		// lytdbeginamount
	private String flowtype;		// flowtype
	private Date fadddate;		// fadddate
	private String periodtype;		// periodtype
	
	public TCashflow() {
		super();
	}

	public TCashflow(String id){
		super(id);
	}

	@Length(min=0, max=64, message="fdbid长度必须介于 0 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	@Length(min=0, max=64, message="reportitem长度必须介于 0 和 64 之间")
	public String getReportitem() {
		return reportitem;
	}

	public void setReportitem(String reportitem) {
		this.reportitem = reportitem;
	}
	
	@Length(min=0, max=4, message="groupid长度必须介于 0 和 4 之间")
	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
	@Length(min=0, max=4, message="yearperiod长度必须介于 0 和 4 之间")
	public String getYearperiod() {
		return yearperiod;
	}

	public void setYearperiod(String yearperiod) {
		this.yearperiod = yearperiod;
	}
	
	@Length(min=0, max=64, message="fcur长度必须介于 0 和 64 之间")
	public String getFcur() {
		return fcur;
	}

	public void setFcur(String fcur) {
		this.fcur = fcur;
	}
	
	public String getYtdendamount() {
		return ytdendamount;
	}

	public void setYtdendamount(String ytdendamount) {
		this.ytdendamount = ytdendamount;
	}
	
	public String getYtdbeginamount() {
		return ytdbeginamount;
	}

	public void setYtdbeginamount(String ytdbeginamount) {
		this.ytdbeginamount = ytdbeginamount;
	}
	
	public String getLytdendamount() {
		return lytdendamount;
	}

	public void setLytdendamount(String lytdendamount) {
		this.lytdendamount = lytdendamount;
	}
	
	public String getLytdbeginamount() {
		return lytdbeginamount;
	}

	public void setLytdbeginamount(String lytdbeginamount) {
		this.lytdbeginamount = lytdbeginamount;
	}
	
	@Length(min=0, max=4, message="flowtype长度必须介于 0 和 4 之间")
	public String getFlowtype() {
		return flowtype;
	}

	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFadddate() {
		return fadddate;
	}

	public void setFadddate(Date fadddate) {
		this.fadddate = fadddate;
	}
	
	@Length(min=0, max=4, message="periodtype长度必须介于 0 和 4 之间")
	public String getPeriodtype() {
		return periodtype;
	}

	public void setPeriodtype(String periodtype) {
		this.periodtype = periodtype;
	}
	
}