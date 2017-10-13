/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.service.workstatistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.customer.dao.TCustomerDao;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TServiceCharge;
import com.thinkgem.jeesite.modules.inspection.dao.workstatistics.TServiceChargeDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 收费审核Service
 * @author xuxiaoong
 * @version 2015-11-25
 */
@Service
@Transactional(readOnly = true)
public class TServiceChargeService extends CrudService<TServiceChargeDao, TServiceCharge> {
	@Autowired
	private TServiceChargeDao dao;
	public TServiceCharge get(String id) {
		return super.get(id);
	}
	
	public List<TServiceCharge> findList(TServiceCharge tServiceCharge) {
		return super.findList(tServiceCharge);
	}
	public Page<TServiceCharge> findListBycustomerIdAndPayeeMan(Page<TServiceCharge> page,TServiceCharge tServiceCharge){
		page.setPageSize(3);
		tServiceCharge.setPage(page);
		return page.setList(dao.findListBycustomerIdAndPayeeMan( tServiceCharge));
	}
	
	public Page<TServiceCharge> findPage(Page<TServiceCharge> page, TServiceCharge tServiceCharge) {
		
		return super.findPage(page, tServiceCharge);
	}
	
	public String getMaxServicedate(TServiceCharge tServiceCharge){
		return dao.getMaxServicedate(tServiceCharge);
	}
	
	public String getMaxServiceChargeNo(TServiceCharge tServiceCharge){
		return dao.getMaxServiceChargeNo(tServiceCharge);
	}
	
	@Transactional(readOnly = false)
	public void save(TServiceCharge tServiceCharge) {
		super.save(tServiceCharge);
	}
	
	@Transactional(readOnly = false)
	public void delete(TServiceCharge tServiceCharge) {
		super.delete(tServiceCharge);
	}
	
}