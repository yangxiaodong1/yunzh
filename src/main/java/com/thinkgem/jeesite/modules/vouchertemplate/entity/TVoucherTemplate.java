/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertemplate.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.vouchertemplateexp.entity.TVoucherTemplateExp;

/**
 * 凭证模板Entity
 * @author 凭证模板
 * @version 2015-10-30
 */
public class TVoucherTemplate extends DataEntity<TVoucherTemplate> {
	
	private static final long serialVersionUID = 1L;
	private String templateTypeId;		// template_type_id
	private String templateName;		// template_name
	
	private String fdbid;		//外键，客户表外键
	
	private List<TVoucherTemplateExp> debitExps;
	
	private List<TVoucherTemplateExp> creditExps;
	
	private List<TVoucherTemplateExp> exps;
	
	public TVoucherTemplate() {
		super();
	}

	public TVoucherTemplate(String id){
		super(id);
	}

	@Length(min=0, max=64, message="template_type_id长度必须介于 0 和 64 之间")
	public String getTemplateTypeId() {
		return templateTypeId;
	}

	public void setTemplateTypeId(String templateTypeId) {
		this.templateTypeId = templateTypeId;
	}
	
	@Length(min=0, max=64, message="template_name长度必须介于 0 和 64 之间")
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public List<TVoucherTemplateExp> getDebitExps() {
		return debitExps;
	}

	public void setDebitExps(List<TVoucherTemplateExp> debitExps) {
		this.debitExps = debitExps;
	}

	public List<TVoucherTemplateExp> getCreditExps() {
		return creditExps;
	}

	public void setCreditExps(List<TVoucherTemplateExp> creditExps) {
		this.creditExps = creditExps;
	}

	public List<TVoucherTemplateExp> getExps() {
		return exps;
	}

	public void setExps(List<TVoucherTemplateExp> exps) {
		this.exps = exps;
	}

	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
}