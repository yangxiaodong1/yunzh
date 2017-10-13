/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.service.workstatistics;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TWorkInfo;
import com.thinkgem.jeesite.modules.inspection.dao.workstatistics.TWorkInfoDao;

/**
 * 工作量明细表Service
 * @author xuxiaolong
 * @version 2015-11-26
 */
@Service
@Transactional(readOnly = true)
public class TWorkInfoService extends CrudService<TWorkInfoDao, TWorkInfo> {

	public TWorkInfo get(String id) {
		return super.get(id);
	}
	
	public List<TWorkInfo> findList(TWorkInfo tWorkInfo) {
		return super.findList(tWorkInfo);
	}
	
	public Page<TWorkInfo> findPage(Page<TWorkInfo> page, TWorkInfo tWorkInfo) {
		return super.findPage(page, tWorkInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(TWorkInfo tWorkInfo) {
		super.save(tWorkInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(TWorkInfo tWorkInfo) {
		super.delete(tWorkInfo);
	}
	
}