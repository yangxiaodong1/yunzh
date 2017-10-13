/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.service.workstatistics;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TWorkGatherInfo;
import com.thinkgem.jeesite.modules.inspection.dao.workstatistics.TWorkGatherInfoDao;

/**
 * 工作量汇总明细Service
 * @author xuxiaolong
 * @version 2015-11-26
 */
@Service
@Transactional(readOnly = true)
public class TWorkGatherInfoService extends CrudService<TWorkGatherInfoDao, TWorkGatherInfo> {

	public TWorkGatherInfo get(String id) {
		return super.get(id);
	}
	
	public List<TWorkGatherInfo> findList(TWorkGatherInfo tWorkGatherInfo) {
		return super.findList(tWorkGatherInfo);
	}
	
	public Page<TWorkGatherInfo> findPage(Page<TWorkGatherInfo> page, TWorkGatherInfo tWorkGatherInfo) {
		return super.findPage(page, tWorkGatherInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(TWorkGatherInfo tWorkGatherInfo) {
		super.save(tWorkGatherInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(TWorkGatherInfo tWorkGatherInfo) {
		super.delete(tWorkGatherInfo);
	}
	
}