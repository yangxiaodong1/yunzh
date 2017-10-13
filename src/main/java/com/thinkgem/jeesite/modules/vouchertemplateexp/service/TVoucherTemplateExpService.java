/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertemplateexp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.vouchertemplateexp.dao.TVoucherTemplateExpDao;
import com.thinkgem.jeesite.modules.vouchertemplateexp.entity.TVoucherTemplateExp;

/**
 * 凭证模板摘要Service
 * @author 凭证模板摘要
 * @version 2015-10-30
 */
@Service
@Transactional(readOnly = true)
public class TVoucherTemplateExpService extends CrudService<TVoucherTemplateExpDao, TVoucherTemplateExp> {
	
	@Autowired
	TVoucherTemplateExpDao dao;

	public TVoucherTemplateExp get(String id) {
		return super.get(id);
	}
	
	public List<TVoucherTemplateExp> findList(TVoucherTemplateExp tVoucherTemplateExp) {
		return super.findList(tVoucherTemplateExp);
	}
	
	public Page<TVoucherTemplateExp> findPage(Page<TVoucherTemplateExp> page, TVoucherTemplateExp tVoucherTemplateExp) {
		return super.findPage(page, tVoucherTemplateExp);
	}
	
	@Transactional(readOnly = false)
	public void save(TVoucherTemplateExp tVoucherTemplateExp) {
		super.save(tVoucherTemplateExp);
	}
	
	@Transactional(readOnly = false)
	public void delete(TVoucherTemplateExp tVoucherTemplateExp) {
		super.delete(tVoucherTemplateExp);
	}
	
	public List<TVoucherTemplateExp> getByTempId(String templateTypeId){
		return dao.getByTempId(templateTypeId);
	}
	
	@Transactional(readOnly = false)
	public boolean delByTempId(String templateId){
		return dao.delByTempId(templateId) > 0;
	}
}