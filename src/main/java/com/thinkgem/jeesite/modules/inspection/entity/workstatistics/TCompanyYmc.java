/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.entity.workstatistics;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 客户总数记录Entity
 * @author xuxiaolong
 * @version 2016-02-24
 */
public class TCompanyYmc extends DataEntity<TCompanyYmc> {
	
	private static final long serialVersionUID = 1L;
	private String companyId;		// 记账公司id
	private String ymonth;		// 当前年月
	private Integer ymonthCount;		// 当前年月正常服务客户数量
	
	public TCompanyYmc() {
		super();
	}

	public TCompanyYmc(String id){
		super(id);
	}

	@Length(min=0, max=64, message="记账公司id长度必须介于 0 和 64 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=6, message="当前年月长度必须介于 0 和 6 之间")
	public String getYmonth() {
		return ymonth;
	}

	public void setYmonth(String ymonth) {
		this.ymonth = ymonth;
	}
	
	public Integer getYmonthCount() {
		return ymonthCount;
	}

	public void setYmonthCount(Integer ymonthCount) {
		this.ymonthCount = ymonthCount;
	}
	
}