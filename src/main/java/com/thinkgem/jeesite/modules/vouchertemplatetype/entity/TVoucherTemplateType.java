/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertemplatetype.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 凭证模板类别Entity
 * @author 凭证模板类别
 * @version 2015-10-30
 */
public class TVoucherTemplateType extends DataEntity<TVoucherTemplateType> {
	
	private static final long serialVersionUID = 1L;
	private String templateTypeName;		// template_type_name
	
	public TVoucherTemplateType() {
		super();
	}

	public TVoucherTemplateType(String id){
		super(id);
	}

	@Length(min=0, max=64, message="template_type_name长度必须介于 0 和 64 之间")
	public String getTemplateTypeName() {
		return templateTypeName;
	}

	public void setTemplateTypeName(String templateTypeName) {
		this.templateTypeName = templateTypeName;
	}
	
}