/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.helpcenter.service;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.helpcenter.entity.Thelpcenter;
import com.thinkgem.jeesite.modules.helpcenter.dao.ThelpcenterDao;

/**
 * 帮助中心Service
 * @author yang
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class ThelpcenterService extends CrudService<ThelpcenterDao, Thelpcenter> {

	public Thelpcenter get(String id) {
		return super.get(id);
	}
	
	public List<Thelpcenter> findList(Thelpcenter thelpcenter) {
		return super.findList(thelpcenter);
	}
	
	public Page<Thelpcenter> findPage(Page<Thelpcenter> page, Thelpcenter thelpcenter) {
		return super.findPage(page, thelpcenter);
	}
	
	@Transactional(readOnly = false)
	public void save(Thelpcenter thelpcenter) {
if (thelpcenter.getContent()!=null){
			
	thelpcenter.setContent(StringEscapeUtils.unescapeHtml4(thelpcenter.getContent()));
		}
		super.save(thelpcenter);
	}
	
	@Transactional(readOnly = false)
	public void delete(Thelpcenter thelpcenter) {
		super.delete(thelpcenter);
	}
	
}