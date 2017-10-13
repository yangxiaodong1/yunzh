/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rpt.entity.Reportformula;
import com.thinkgem.jeesite.modules.rpt.dao.ReportformulaDao;

/**
 * 报表公式科目Service
 * @author zhangtong
 * @version 2015-12-07
 */
@Service
@Transactional(readOnly = true)
public class ReportformulaService extends CrudService<ReportformulaDao, Reportformula> {

	public Reportformula get(String id) {
		return super.get(id);
	}
	
	public List<Reportformula> findList(Reportformula reportformula) {
		return super.findList(reportformula);
	}
	
	public Page<Reportformula> findPage(Page<Reportformula> page, Reportformula reportformula) {
		return super.findPage(page, reportformula);
	}
	
	@Transactional(readOnly = false)
	public void save(Reportformula reportformula) {
		super.save(reportformula);
	}
	
	@Transactional(readOnly = false)
	public void delete(Reportformula reportformula) {
		super.delete(reportformula);
	}
	
}