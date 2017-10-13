/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertitle.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.vouchertitle.entity.TVoucherTitle;
import com.thinkgem.jeesite.modules.vouchertitle.dao.TVoucherTitleDao;

/**
 * 凭证标题Service
 * @author 凭证标题
 * @version 2015-10-30
 */
@Service
@Transactional(readOnly = true)
public class TVoucherTitleService extends CrudService<TVoucherTitleDao, TVoucherTitle> {

	public TVoucherTitle get(String id) {
		return super.get(id);
	}
	
	public List<TVoucherTitle> findList(TVoucherTitle tVoucherTitle) {
		return super.findList(tVoucherTitle);
	}
	
	public Page<TVoucherTitle> findPage(Page<TVoucherTitle> page, TVoucherTitle tVoucherTitle) {
		return super.findPage(page, tVoucherTitle);
	}
	
	@Transactional(readOnly = false)
	public void save(TVoucherTitle tVoucherTitle) {
		super.save(tVoucherTitle);
	}
	
	@Transactional(readOnly = false)
	public void delete(TVoucherTitle tVoucherTitle) {
		super.delete(tVoucherTitle);
	}
	
}