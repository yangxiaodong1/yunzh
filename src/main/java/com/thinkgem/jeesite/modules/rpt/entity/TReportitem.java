/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 所有报表Entity
 * @author zhangtong
 * @version 2015-12-03
 */
public class TReportitem extends DataEntity<TReportitem> {
	
	private static final long serialVersionUID = 1L;
	private String reportitem;		// 报表编码
	private String fdesc;		// 名称
	private String nameture;		// 真正的名字
	private String dc;		// 借贷方向
	private String groupid;		// 科目分组表外键
	private String forder;		// 排列秩序
	private String frownum;		// 第几行
	private String datasource;		// 数据源 表示本行处于那哪一级别
	private String formula;		// 公式  用来计算合计的
	private String fdbid;		// 公司客户外键
	private String reportId;		// 报表分组的外键
	private String accountPeriod;
	private String periodEnd;
	private String periodtype; // 周期类型 月报 或者 季报
	
	public String getPeriodtype() {
		return periodtype;
	}

	public void setPeriodtype(String periodtype) {
		this.periodtype = periodtype;
	}

	private boolean emptyRecal;		//是否清空重算
	
	public boolean isEmptyRecal() {
		return emptyRecal;
	}

	public void setEmptyRecal(boolean emptyRecal) {
		this.emptyRecal = emptyRecal;
	}

	public String getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}

	public String getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	public TReportitem() {
		super();
	}

	public TReportitem(String id){
		super(id);
	}

	@Length(min=0, max=64, message="报表编码长度必须介于 0 和 64 之间")
	public String getReportitem() {
		return reportitem;
	}

	public void setReportitem(String reportitem) {
		this.reportitem = reportitem;
	}
	
	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	public String getFdesc() {
		return fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}
	
	@Length(min=0, max=64, message="真正的名字长度必须介于 0 和 64 之间")
	public String getNameture() {
		return nameture;
	}

	public void setNameture(String nameture) {
		this.nameture = nameture;
	}
	
	@Length(min=0, max=2, message="借贷方向长度必须介于 0 和 2 之间")
	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}
	
	@Length(min=0, max=4, message="科目分组表外键长度必须介于 0 和 4 之间")
	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
	@Length(min=0, max=4, message="排列秩序长度必须介于 0 和 4 之间")
	public String getForder() {
		return forder;
	}

	public void setForder(String forder) {
		this.forder = forder;
	}
	
	@Length(min=0, max=4, message="第几行长度必须介于 0 和 4 之间")
	public String getFrownum() {
		return frownum;
	}

	public void setFrownum(String frownum) {
		this.frownum = frownum;
	}
	
	@Length(min=0, max=4, message="数据源 表示本行处于那哪一级别长度必须介于 0 和 4 之间")
	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	
	@Length(min=0, max=500, message="公式  用来计算合计的长度必须介于 0 和 500 之间")
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	@Length(min=0, max=64, message="公司客户外键长度必须介于 0 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	@Length(min=0, max=11, message="报表分组的外键长度必须介于 0 和 11 之间")
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
}