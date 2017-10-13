/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.service.workstatistics;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TWorkGather;
import com.thinkgem.jeesite.modules.inspection.dao.workstatistics.TWorkGatherDao;

/**
 * 工作量汇总表Service
 * @author xuxiaolong
 * @version 2015-11-27
 */
@Service
@Transactional(readOnly = true)
public class TWorkGatherService extends CrudService<TWorkGatherDao, TWorkGather> {

	public TWorkGather get(String id) {
		return super.get(id);
	}
	public TWorkGather tWorkGather1(TWorkGather tWorkGather){
		return dao.tWorkGather1(tWorkGather);
	}
	public TWorkGather tWorkGather2(TWorkGather tWorkGather){
		return dao.tWorkGather2(tWorkGather);
	}
	public TWorkGather tWorkGather3(TWorkGather tWorkGather){
		return dao.tWorkGather3(tWorkGather);
	}
	
	public List<TWorkGather> findList(TWorkGather tWorkGather) {
		return super.findList(tWorkGather);
	}
	
	public Page<TWorkGather> findPage(Page<TWorkGather> page, TWorkGather tWorkGather) {
		return super.findPage(page, tWorkGather);
	}
	
	@Transactional(readOnly = false)
	public void save(TWorkGather tWorkGather) {
		super.save(tWorkGather);
	}
	
	@Transactional(readOnly = false)
	public void delete(TWorkGather tWorkGather) {
		super.delete(tWorkGather);
	}
	
}