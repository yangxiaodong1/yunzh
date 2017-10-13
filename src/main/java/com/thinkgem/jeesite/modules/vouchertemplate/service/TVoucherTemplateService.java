/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertemplate.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.vouchertemplate.entity.TVoucherTemplate;
import com.thinkgem.jeesite.modules.vouchertemplate.dao.TVoucherTemplateDao;
import com.thinkgem.jeesite.modules.vouchertemplateexp.dao.TVoucherTemplateExpDao;
import com.thinkgem.jeesite.modules.vouchertemplateexp.entity.TVoucherTemplateExp;

/**
 * 凭证模板Service
 * @author 凭证模板
 * @version 2015-10-30
 */
@Service
@Transactional(readOnly = true)
public class TVoucherTemplateService extends CrudService<TVoucherTemplateDao, TVoucherTemplate> {

	@Autowired
	TVoucherTemplateDao dao;
	public TVoucherTemplate get(String id) {
		return super.get(id);
	}
	
	public List<TVoucherTemplate> findList(TVoucherTemplate tVoucherTemplate) {
		return super.findList(tVoucherTemplate);
	}
	
	public Page<TVoucherTemplate> findPage(Page<TVoucherTemplate> page, TVoucherTemplate tVoucherTemplate) {
		return super.findPage(page, tVoucherTemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(TVoucherTemplate tVoucherTemplate) {
		super.save(tVoucherTemplate);
	}
	
	@Transactional(readOnly = false)
	public void delete(TVoucherTemplate tVoucherTemplate) {
		super.delete(tVoucherTemplate);
	}
	
	public List<TVoucherTemplate> getByTempTypeId(String templateId,String fdbid){
		return dao.findAllListByType(templateId,fdbid);
	}
	
	@Transactional(readOnly = false)
	public boolean update(TVoucherTemplate tVoucherTemplate){
		return dao.update(tVoucherTemplate) > 0;
	}
	
	@Transactional(readOnly = false)
	public boolean deleteById(String id){
		return dao.deleteById(id) > 0 ;
	}
	/**
	 * 根据公司id和类型
	 * @param templateTypeId
	 * @param fdbid
	 * @return
	 */
	public List<TVoucherTemplate> findAllListByTypeAndFdbid(String templateTypeId,String fdbid){
		return dao.findAllListByTypeAndFdbid(templateTypeId,fdbid);
	}
	
	/**
	 * 根据公司id和类型
	 * @param templateTypeId
	 * @param fdbid
	 * @return
	 */
	public TVoucherTemplate findAllListByIdAndFdbid(String id,String fdbid){
		return dao.findAllListByIdAndFdbid(id,fdbid);
	}
}