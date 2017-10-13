/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sysnews.service;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sysnews.entity.Tsysnews;
import com.thinkgem.jeesite.modules.sysnews.dao.TsysnewsDao;

/**
 * 系统消息表Service
 * @author yang
 * @version 2016-01-14
 */
@Service
@Transactional(readOnly = true)
public class TsysnewsService extends CrudService<TsysnewsDao, Tsysnews> {
	@Autowired
	private TsysnewsDao tsysnewsDao;
	public Tsysnews get(String id) {
		return super.get(id);
	}
	
	public List<Tsysnews> findList(Tsysnews tsysnews) {
		return super.findList(tsysnews);
	}
	
	public Page<Tsysnews> findPage(Page<Tsysnews> page, Tsysnews tsysnews) {
		return super.findPage(page, tsysnews);
	}
	
	@Transactional(readOnly = false)
	public void save(Tsysnews tsysnews) {
		if (tsysnews.getContent()!=null){
			
			tsysnews.setContent(StringEscapeUtils.unescapeHtml4(tsysnews.getContent()));
		}
		super.save(tsysnews);
	}
	
	@Transactional(readOnly = false)
	public void delete(Tsysnews tsysnews) {
		super.delete(tsysnews);
	}
	@Transactional(readOnly = false)
	public int insertNewUser(List<Tsysnews> tsysnewslist) {
		return dao.insertNewUser(tsysnewslist);
	}
	
	
	public List<Tsysnews> findListByUserId(Tsysnews tsysnews){
		return dao.findListByUserId(tsysnews);
	}
	public Page<Tsysnews> findListByUserId(Page<Tsysnews> page,Tsysnews tsysnews){
		tsysnews.setPage(page);
		page.setList(dao.findListByUserId(tsysnews));
		return page;
	}
	

	public List<Tsysnews> findListAll(Tsysnews tsysnews) {
		// TODO Auto-generated method stub
		return tsysnewsDao.findAllList(tsysnews);
	}
	
}