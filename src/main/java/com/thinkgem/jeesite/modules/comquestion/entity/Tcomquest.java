/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.comquestion.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 常见问题表Entity
 * @author yang
 * @version 2016-01-15
 */
public class Tcomquest extends DataEntity<Tcomquest> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	
	private String createDateString;//创建日期string
	private String updateDateString;//创建日期string
	private String updateByStrinig;//修改人
	
	
	public String getUpdateByStrinig() {
		return updateByStrinig;
	}

	public void setUpdateByStrinig(String updateByStrinig) {
		this.updateByStrinig = updateByStrinig;
	}

	public String getCreateDateString() {
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}

	public String getUpdateDateString() {
		return updateDateString;
	}

	public void setUpdateDateString(String updateDateString) {
		this.updateDateString = updateDateString;
	}

	public Tcomquest() {
		super();
	}

	public Tcomquest(String id){
		super(id);
	}

	@Length(min=0, max=64, message="标题长度必须介于 0 和 64 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}