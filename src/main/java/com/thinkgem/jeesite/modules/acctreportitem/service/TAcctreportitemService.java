/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acctreportitem.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.acctreportitem.entity.TAcctreportitem;
import com.thinkgem.jeesite.modules.acctreportitem.dao.TAcctreportitemDao;

/**
 * 公式表Service
 * @author zhangtong
 * @version 2015-12-05
 */
@Service
@Transactional(readOnly = true)
public class TAcctreportitemService extends CrudService<TAcctreportitemDao, TAcctreportitem> {

	public TAcctreportitem get(String id) {
		return super.get(id);
	}
	
	public List<TAcctreportitem> findList(TAcctreportitem tAcctreportitem) {
		return super.findList(tAcctreportitem);
	}
	
	public Page<TAcctreportitem> findPage(Page<TAcctreportitem> page, TAcctreportitem tAcctreportitem) {
		return super.findPage(page, tAcctreportitem);
	}
	
	@Transactional(readOnly = false)
	public void save(TAcctreportitem tAcctreportitem) {
		super.save(tAcctreportitem);
	}
	
	@Transactional(readOnly = false)
	public void delete(TAcctreportitem tAcctreportitem) {
		super.delete(tAcctreportitem);
	}
	/**
	 * 根据id 删除  公式
	 * @param tAcctreportitem
	 */
	@Transactional(readOnly = false)
	public void deleteAcctreporttitemById(TAcctreportitem tAcctreportitem){
		dao.deleteAcctreporttitemById(tAcctreportitem);
	}
	/**
	 * 自己的保存 公式
	 * @param tAcctreportitem
	 */
	@Transactional(readOnly = false)
	public void savaAcctreporttitem(TAcctreportitem tAcctreportitem){
		dao.savaAcctreporttitem(tAcctreportitem);
	}
}