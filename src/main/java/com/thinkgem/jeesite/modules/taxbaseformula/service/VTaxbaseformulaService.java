/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.taxbaseformula.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.taxbase.entity.TTaxbase;
import com.thinkgem.jeesite.modules.taxbaseformula.entity.VTaxbaseformula;
import com.thinkgem.jeesite.modules.taxbaseformula.dao.VTaxbaseformulaDao;

/**
 * 税率公式和科目表Service
 * @author zt
 * @version 2016-01-11
 */
@Service
@Transactional(readOnly = true)
public class VTaxbaseformulaService extends CrudService<VTaxbaseformulaDao, VTaxbaseformula> {

	public VTaxbaseformula get(String id) {
		return super.get(id);
	}
	
	public List<VTaxbaseformula> findList(VTaxbaseformula vTaxbaseformula) {
		return super.findList(vTaxbaseformula);
	}
	
	public Page<VTaxbaseformula> findPage(Page<VTaxbaseformula> page, VTaxbaseformula vTaxbaseformula) {
		return super.findPage(page, vTaxbaseformula);
	}
	
	@Transactional(readOnly = false)
	public void save(VTaxbaseformula vTaxbaseformula) {
		super.save(vTaxbaseformula);
	}
	
	@Transactional(readOnly = false)
	public void delete(VTaxbaseformula vTaxbaseformula) {
		super.delete(vTaxbaseformula);
	}
	/**
	 * 根据税种编号 和公司id 取值
	 * @return
	 */
	public List<VTaxbaseformula> findTTaxbaseFormulaByfdbIdAndTaxCategory(@Param("fdbid")String fdbid,@Param("taxCategory")String taxCategory) {
		return dao.findTTaxbaseFormulaByfdbIdAndTaxCategory(fdbid,taxCategory);
	}
}