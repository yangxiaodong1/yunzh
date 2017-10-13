/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.workterrace.entity.TCustomerSalary;
import com.thinkgem.jeesite.modules.workterrace.dao.TCustomerSalaryDao;

/**
 * 工资Service
 * @author xuxiaolong
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class TCustomerSalaryService extends CrudService<TCustomerSalaryDao, TCustomerSalary> {

	public TCustomerSalary get(String id) {
		return super.get(id);
	}
	
	public List<TCustomerSalary> findList(TCustomerSalary tCustomerSalary) {
		return super.findList(tCustomerSalary);
	}
	
	public Page<TCustomerSalary> findPage(Page<TCustomerSalary> page, TCustomerSalary tCustomerSalary) {
		return super.findPage(page, tCustomerSalary);
	}
	@Transactional(readOnly = false)
	public String copyCustomerSalary(Map<String, Object> map) {
		return dao.copyCustomerSalary(map);
	}
	@Transactional(readOnly = false)
	public void save(TCustomerSalary tCustomerSalary) {
		super.save(tCustomerSalary);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCustomerSalary tCustomerSalary) {
		super.delete(tCustomerSalary);
	}
	public TCustomerSalary findListBymax(TCustomerSalary tCustomerSalary){
		return dao.findListBymax(tCustomerSalary);
	}
	
	/**
	 * 统计客户账期
	 * @param fdbid
	 * @param period
	 * @return
	 */
	public TCustomerSalary sumSalary(String fdbid,String period){
		return dao.sumSalary(fdbid, period);
	}
	/**
	 * 对工资操作处理
	 * @param fdbid
	 * @param period
	 * @return
	 */
	public TCustomerSalary findSalaryOperation(@Param("fdbid")String fdbid,@Param("period")String period){
		return dao.findSalaryOperation(fdbid,period);
	}
}