/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.entity.workstatistics;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 服务收费报表Entity
 * @author xuxiaolong
 * @version 2015-11-24
 */
public class TServeInfo extends DataEntity<TServeInfo> {
	private String nowdate=new Date().toString();
	private static final long serialVersionUID = 1L;
	private String byYear=nowdate.substring(nowdate.length()-4, nowdate.length());		// 年份
	private String byYearName=byYear+"年";		// 年份
	private String workerName;		// 姓名
	private String chargeSum;		// 总收费
	private String companyId=UserUtils.getUser().getCompany().getId();	// 公司Id
	private String officeId;		// 部门id
	private String notnull="0";		// 部门id
	
	public String getNotnull() {
		return notnull;
	}

	public void setNotnull(String notnull) {
		this.notnull = notnull;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public TServeInfo() {
		super();
	}

	public TServeInfo(String id){
		super(id);
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
	}
	
	@Length(min=1, max=100, message="姓名长度必须介于 1 和 100 之间")
	public String getWorkerName() {
		return workerName;
	}



	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	
	@Length(min=0, max=255, message="总收费长度必须介于 0 和 255 之间")
	public String getChargeSum() {
		return chargeSum;
	}

	public void setChargeSum(String chargeSum) {
		this.chargeSum = chargeSum;
	}
	
}