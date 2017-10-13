/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.intelligenttemplate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.intelligenttemplate.entity.TIntelligentTemplate;
import com.thinkgem.jeesite.modules.intelligenttemplate.dao.TIntelligentTemplateDao;

/**
 * intelligenttemplateService
 * @author intelligenttemplate
 * @version 2015-12-07
 */
@Service
@Transactional(readOnly = true)
public class TIntelligentTemplateService extends CrudService<TIntelligentTemplateDao, TIntelligentTemplate> {

	public TIntelligentTemplate get(String id) {
		return super.get(id);
	}
	
	public List<TIntelligentTemplate> findList(TIntelligentTemplate tIntelligentTemplate) {
		return super.findList(tIntelligentTemplate);
	}
	
	public Page<TIntelligentTemplate> findPage(Page<TIntelligentTemplate> page, TIntelligentTemplate tIntelligentTemplate) {
		return super.findPage(page, tIntelligentTemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(TIntelligentTemplate tIntelligentTemplate) {
		super.save(tIntelligentTemplate);
	}
	
	@Transactional(readOnly = false)
	public void delete(TIntelligentTemplate tIntelligentTemplate) {
		super.delete(tIntelligentTemplate);
	}
	
}