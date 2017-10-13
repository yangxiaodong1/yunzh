/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucherexp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherexpvo;
import com.thinkgem.jeesite.modules.voucherexp.dao.TVoucherexpvoDao;

/**
 * 显示凭证Service
 * @author cy
 * @version 2016-02-19
 */
@Service
@Transactional(readOnly = true)
public class TVoucherexpvoService extends CrudService<TVoucherexpvoDao, TVoucherexpvo> {

	public TVoucherexpvo get(String id) {
		return super.get(id);
	}
	
	public List<TVoucherexpvo> findList(TVoucherexpvo tVoucherexpvo) {
		return super.findList(tVoucherexpvo);
	}
	
	public Page<TVoucherexpvo> findPage(Page<TVoucherexpvo> page, TVoucherexpvo tVoucherexpvo) {
		return super.findPage(page, tVoucherexpvo);
	}
	
	@Transactional(readOnly = false)
	public void save(TVoucherexpvo tVoucherexpvo) {
		super.save(tVoucherexpvo);
	}
	
	@Transactional(readOnly = false)
	public void delete(TVoucherexpvo tVoucherexpvo) {
		super.delete(tVoucherexpvo);
	}
	
}