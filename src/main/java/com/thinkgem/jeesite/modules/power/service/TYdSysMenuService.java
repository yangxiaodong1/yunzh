/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.power.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.power.entity.TYdSysMenu;
import com.thinkgem.jeesite.modules.power.dao.TYdSysMenuDao;

/**
 * 权限列表Service
 * @author yang
 * @version 2016-01-30
 */
@Service
@Transactional(readOnly = true)
public class TYdSysMenuService extends CrudService<TYdSysMenuDao, TYdSysMenu> {

	public TYdSysMenu get(String id) {
		return super.get(id);
	}
	
	public List<TYdSysMenu> findList(TYdSysMenu tYdSysMenu) {
		return super.findList(tYdSysMenu);
	}
	/**
	 * yang
	 */
	public List<TYdSysMenu> findPower(TYdSysMenu tYdSysMenu) {
		return dao.findPower(tYdSysMenu);
	}
	public Page<TYdSysMenu> findPage(Page<TYdSysMenu> page, TYdSysMenu tYdSysMenu) {
		return super.findPage(page, tYdSysMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(TYdSysMenu tYdSysMenu) {
		super.save(tYdSysMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(TYdSysMenu tYdSysMenu) {
		super.delete(tYdSysMenu);
	}
	
}