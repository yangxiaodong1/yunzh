/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.service.workstatistics;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TServeInfo;
import com.thinkgem.jeesite.modules.inspection.dao.workstatistics.TServeInfoDao;

/**
 * 服务收费报表Service
 * @author xuxiaolong
 * @version 2015-11-24
 */
@Service
@Transactional(readOnly = true)
public class TServeInfoService extends CrudService<TServeInfoDao, TServeInfo> {

	public TServeInfo get(String id) {
		return super.get(id);
	}
	
	public List<TServeInfo> findList(TServeInfo tServeInfo) {
		return super.findList(tServeInfo);
	}
	
	public Page<TServeInfo> findPage(Page<TServeInfo> page, TServeInfo tServeInfo) {
		page.setPageSize(9);
		return super.findPage(page, tServeInfo);
	}
	public String findListMax(TServeInfo tServeInfo){
		return dao.findListMax(tServeInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(TServeInfo tServeInfo) {
		super.save(tServeInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(TServeInfo tServeInfo) {
		super.delete(tServeInfo);
	}
	
}