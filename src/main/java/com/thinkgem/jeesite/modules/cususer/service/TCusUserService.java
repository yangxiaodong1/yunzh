/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cususer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cususer.entity.TCusUser;
import com.thinkgem.jeesite.modules.cususer.dao.TCusUserDao;

/**
 * 转交Service
 * @author cy
 * @version 2015-11-24
 */
@Service
@Transactional(readOnly = true)
public class TCusUserService extends CrudService<TCusUserDao, TCusUser> {

	public TCusUser get(String id) {
		return super.get(id);
	}
	
	public List<TCusUser> findList(TCusUser tCusUser) {
		return super.findList(tCusUser);
	}
	
	public Page<TCusUser> findPage(Page<TCusUser> page
			, TCusUser tCusUser) {
		return super.findPage(page, tCusUser);
	}
	
	@Transactional(readOnly = false)
	public void save(TCusUser tCusUser) {
		super.save(tCusUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCusUser tCusUser) {
		super.delete(tCusUser);
	}
	
	/**
	 * 根据接受状态、转交用户来获取所有数据
	 * **/
	public List<TCusUser> accpetSate(TCusUser tCusUser){
		return dao.accpetSate(tCusUser);
	}
	
	/**
	 * 改变接受状态
	 * **/
	@Transactional(readOnly = false)
	public void updateState(TCusUser tCusUser){
		dao.updateState(tCusUser);
	}
	
	/**
	 * 根据接受状态、转交用户来获取所有数据(分组显示)
	 * **/
	public List<TCusUser> Noaccept(TCusUser tCusUser){
		return dao.Noaccept(tCusUser);
	}
}