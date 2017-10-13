/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.newcharge.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Area;

/**
 * 加盟申请Entity
 * @author yang
 * @version 2016-01-17
 */
public class Tjoinappl extends DataEntity<Tjoinappl> {
	
	private static final long serialVersionUID = 1L;
	private String compname;		// 申请公司名字
	private String city;		// 所在城市
	private String contectperson;		// 对接人
	private String contectphone;		// 联系电话
	private String message;		// 反馈信息
	
	private Area area;
	private String createDateString;
	private String city1;//省份
	private String city2;//市
	private String city3;//区域县
	
	private String ctiy4;
	
	
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

	public String getCtiy4() {
		return ctiy4;
	}

	public void setCtiy4(String ctiy4) {
		this.ctiy4 = ctiy4;
	}

	public String getCreateDateString() {
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Tjoinappl() {
		super();
	}

	public Tjoinappl(String id){
		super(id);
	}

	@Length(min=0, max=64, message="申请公司名字长度必须介于 0 和 64 之间")
	public String getCompname() {
		return compname;
	}

	public void setCompname(String compname) {
		this.compname = compname;
	}
	
	@Length(min=0, max=64, message="所在城市长度必须介于 0 和 64 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=64, message="对接人长度必须介于 0 和 64 之间")
	public String getContectperson() {
		return contectperson;
	}

	public void setContectperson(String contectperson) {
		this.contectperson = contectperson;
	}
	
	@Length(min=0, max=64, message="联系电话长度必须介于 0 和 64 之间")
	public String getContectphone() {
		return contectphone;
	}

	public void setContectphone(String contectphone) {
		this.contectphone = contectphone;
	}
	
	@Length(min=0, max=255, message="反馈信息长度必须介于 0 和 255 之间")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}