/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.comquestion.service;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.comquestion.entity.Tcomquest;
import com.thinkgem.jeesite.modules.comquestion.dao.TcomquestDao;

/**
 * 常见问题表Service
 * @author yang
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class TcomquestService extends CrudService<TcomquestDao, Tcomquest> {

	public Tcomquest get(String id) {
		return super.get(id);
	}
	
	public List<Tcomquest> findList(Tcomquest tcomquest) {
		return super.findList(tcomquest);
	}
	
	public Page<Tcomquest> findPage(Page<Tcomquest> page, Tcomquest tcomquest) {
		return super.findPage(page, tcomquest);
	}
	
	@Transactional(readOnly = false)
	public void save(Tcomquest tcomquest) {
if (tcomquest.getContent()!=null){
			
	tcomquest.setContent(StringEscapeUtils.unescapeHtml4(tcomquest.getContent()));
		}
		super.save(tcomquest);
	}
	
	@Transactional(readOnly = false)
	public void delete(Tcomquest tcomquest) {
		super.delete(tcomquest);
	}
	
}