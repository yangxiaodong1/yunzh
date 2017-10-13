/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Calendar;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 个人提醒Entity
 * @author xuxiaolong
 * @version 2016-01-15
 */
public class TPersonalReminder extends DataEntity<TPersonalReminder> {
	
	private static final long serialVersionUID = 1L;
	private String matters;		// 事项
	private String matter;		// 事项
	

	private Date warnTime=new Date();		// 提醒时间
	private String degreeImportance;		// 重要程度 高2 中1 低0
	private String degreeEvent;		// 重要事件 不重复0 重复1
	private String state;		// 状态  未完成0 已完成1
	private String userId;
	private String byYear=String.valueOf(Calendar.getInstance().get(Calendar.YEAR)); //记账年份条件
	private String byYearName=byYear+"年";		// 记账年份展示
	
	public String getByYear() {
		return byYear;
	}

	public void setByYear(String byYear) {
		this.byYear = byYear;
		this.byYearName=byYear+"年";
	}
	public String getMatter() {
		return matter;
	}

	public void setMatter(String matter) {
		this.matter = matter;
	}
	public String getByYearName() {
		return byYearName;
	}

	public void setByYearName(String byYearName) {
		this.byYearName = this.byYear+"年";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public TPersonalReminder() {
		super();
	}

	public TPersonalReminder(String id){
		super(id);
	}

	@Length(min=1, max=255, message="事项长度必须介于 1 和 255 之间")
	public String getMatters() {
		return matters;
	}

	public void setMatters(String matters) {
		this.matters = matters;
		if (matters.length()>24) {
			this.matter=matters.substring(0, 24)+"...";
		}else {
			this.matter=matters;
		}
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(Date warnTime) {
		this.warnTime = warnTime;
	}
	
	@Length(min=0, max=64, message="重要程度长度必须介于 0 和 64 之间")
	public String getDegreeImportance() {
		return degreeImportance;
	}

	public void setDegreeImportance(String degreeImportance) {
		this.degreeImportance = degreeImportance;
	}
	
	@Length(min=0, max=64, message="重要事件长度必须介于 0 和 64 之间")
	public String getDegreeEvent() {
		return degreeEvent;
	}

	public void setDegreeEvent(String degreeEvent) {
		this.degreeEvent = degreeEvent;
	}
	
	@Length(min=0, max=30, message="状态长度必须介于 0 和 30 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}