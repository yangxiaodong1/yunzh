/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.customer.entity.TAppendix;
import com.thinkgem.jeesite.modules.customer.dao.TAppendixDao;

/**
 * 附件添加Service
 * @author cy
 * @version 2015-12-16
 */
@Service
@Transactional(readOnly = true)
public class TAppendixService extends CrudService<TAppendixDao, TAppendix> {

	public TAppendix get(String id) {
		return super.get(id);
	}
	
	public List<TAppendix> findList(TAppendix tAppendix) {
		return super.findList(tAppendix);
	}
	
	public Page<TAppendix> findPage(Page<TAppendix> page, TAppendix tAppendix) {
		return super.findPage(page, tAppendix);
	}
	public List<TAppendix> findByState(String duiyingstate){
		return dao.findByState(duiyingstate);
	}
	//findBytcusid通过客户id来查找
	public List<TAppendix> findBytcusid(String cusid){
		return dao.findBytcusid(cusid);
	}
	@Transactional(readOnly = false)
	public void save(TAppendix tAppendix) {
		super.save(tAppendix);
	}
	
	@Transactional(readOnly = false)
	public void delete(TAppendix tAppendix) {
		super.delete(tAppendix);
	}
	
	
	@Transactional(readOnly = false)
	public String selectId(){
		return dao.selectId();
	}
	
	@Transactional(readOnly = false)
	public void insertNew(TAppendix tAppendix){
		dao.insertNew(tAppendix);
	}
	/**
	 * 删除语句
	 * **/
	@Transactional(readOnly = false)
	public void deleteInfo(String id){
		dao.deleteInfo(id);
	}
	/**
	 * 修改语句
	 * **/
	@Transactional(readOnly = false)
	public void updateCusid(TAppendix tAppendix){
		dao.updateCusid(tAppendix);
	}
}