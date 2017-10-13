/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertemplatetype.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.vouchertemplatetype.entity.TVoucherTemplateType;
import com.thinkgem.jeesite.modules.vouchertemplatetype.dao.TVoucherTemplateTypeDao;

/**
 * 凭证模板类别Service
 * @author 凭证模板类别
 * @version 2015-10-30
 */
@Service
@Transactional(readOnly = true)
public class TVoucherTemplateTypeService extends CrudService<TVoucherTemplateTypeDao, TVoucherTemplateType> {

	public TVoucherTemplateType get(String id) {
		return super.get(id);
	}
	
	public List<TVoucherTemplateType> findList(TVoucherTemplateType tVoucherTemplateType) {
		return super.findList(tVoucherTemplateType);
	}
	
	public Page<TVoucherTemplateType> findPage(Page<TVoucherTemplateType> page, TVoucherTemplateType tVoucherTemplateType) {
		return super.findPage(page, tVoucherTemplateType);
	}
	
	@Transactional(readOnly = false)
	public void save(TVoucherTemplateType tVoucherTemplateType) {
		super.save(tVoucherTemplateType);
	}
	
	@Transactional(readOnly = false)
	public void delete(TVoucherTemplateType tVoucherTemplateType) {
		super.delete(tVoucherTemplateType);
	}
	
}