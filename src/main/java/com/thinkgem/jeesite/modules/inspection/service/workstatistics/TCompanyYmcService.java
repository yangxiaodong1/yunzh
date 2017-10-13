/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.service.workstatistics;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TCompanyYmc;
import com.thinkgem.jeesite.modules.inspection.dao.workstatistics.TCompanyYmcDao;

/**
 * 客户总数记录Service
 * @author xuxiaolong
 * @version 2016-02-24
 */
@Service
@Transactional(readOnly = true)
public class TCompanyYmcService extends CrudService<TCompanyYmcDao, TCompanyYmc> {

	public TCompanyYmc get(String id) {
		return super.get(id);
	}
	
	public List<TCompanyYmc> findList(TCompanyYmc tCompanyYmc) {
		return super.findList(tCompanyYmc);
	}
	
	public Page<TCompanyYmc> findPage(Page<TCompanyYmc> page, TCompanyYmc tCompanyYmc) {
		return super.findPage(page, tCompanyYmc);
	}
	
	@Transactional(readOnly = false)
	public void save(TCompanyYmc tCompanyYmc) {
		super.save(tCompanyYmc);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCompanyYmc tCompanyYmc) {
		super.delete(tCompanyYmc);
	}
	/**
	 * 查询当前companyId当前年月的正常服务客户数
	 * */
	public TCompanyYmc findYMCountBycompanyId(TCompanyYmc tCompanyYmc){
		return dao.findYMCountBycompanyId(tCompanyYmc);
	}
	public TCompanyYmc MaxTCompanyYmcByCompanyId(String companyId){
		return dao.MaxTCompanyYmcByCompanyId(companyId);
	}
	@Transactional(readOnly = false)
	public int insertlist(List<TCompanyYmc> listtCompanyYmc){
		return dao.insertlist(listtCompanyYmc);
	}
	
	/**
	 * 当前companyId当前年月的正常服务客户数+1
	 * */
	@Transactional(readOnly = false)
	public int updatePlus(TCompanyYmc tCompanyYmc){
		return dao.updatePlus(tCompanyYmc);
	}
	/**
	 * 当前companyId当前年月的正常服务客户数-1
	 * */
	@Transactional(readOnly = false)
	public int updateSubtract(TCompanyYmc tCompanyYmc){
		return dao.updateSubtract(tCompanyYmc);
	}
	/**
	 * 当前companyId当前年月的正常服务客户数-n
	 * */
	@Transactional(readOnly = false)
	public int updateSubtracts(TCompanyYmc tCompanyYmc){
		return dao.updateSubtracts(tCompanyYmc);
	}
	
	/**
	 * 添加企业的条件
	 * @param tCompanyYmc
	 * @return
	 */
	public TCompanyYmc insertTerm(TCompanyYmc tCompanyYmc){
		return dao.insertTerm(tCompanyYmc);
	}
	@Transactional(readOnly = false)
	public int updateymonthcount(TCompanyYmc tCompanyYmc){
		return dao.updateymonthcount(tCompanyYmc);
	}
	
	/**
	 * 查询最近的账期
	 * @param tCompanyYmc
	 * @return
	 */
	public TCompanyYmc selectzjymc(TCompanyYmc tCompanyYmc){
		return dao.selectzjymc(tCompanyYmc);
	}
	@Transactional(readOnly = false)
	public int insert(TCompanyYmc tCompanyYmc){
		return dao.insert(tCompanyYmc);
	}
}