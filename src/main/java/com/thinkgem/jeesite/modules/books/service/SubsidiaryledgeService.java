/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.books.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.books.entity.Subsidiaryledge;
import com.thinkgem.jeesite.modules.books.dao.SubsidiaryledgeDao;

/**
 * 凭证摘要和科目Service
 * 
 * @author zhangtong
 * @version 2015-12-04
 */
@Service
@Transactional(readOnly = true)
public class SubsidiaryledgeService extends
		CrudService<SubsidiaryledgeDao, Subsidiaryledge> {

	public Subsidiaryledge get(String id) {
		return super.get(id);
	}

	public List<Subsidiaryledge> findList(Subsidiaryledge subsidiaryledge) {
		return super.findList(subsidiaryledge);
	}

	public Page<Subsidiaryledge> findPage(Page<Subsidiaryledge> page,
			Subsidiaryledge subsidiaryledge) {
		return super.findPage(page, subsidiaryledge);
	}

	@Transactional(readOnly = false)
	public void save(Subsidiaryledge subsidiaryledge) {
		super.save(subsidiaryledge);
	}

	@Transactional(readOnly = false)
	public void delete(Subsidiaryledge subsidiaryledge) {
		super.delete(subsidiaryledge);
	}

	/**
	 * 根据账期 公司id 科目编码 查询信息
	 * 
	 * @param accId
	 * @param fdbid
	 * @param accountPeriod
	 * @param periodEnd
	 * @return
	 */
	public List<Subsidiaryledge> findSubsidiaryledgeList(
			@Param("accId") String accId, @Param("fdbid") String fdbid,
			@Param("accountPeriod") String accountPeriod,
			@Param("periodEnd") String periodEnd,
			@Param("parentId")String parentId,
			@Param("list")List<String> accIdS) {
		return dao.findSubsidiaryledgeList(accId, fdbid, accountPeriod,
				periodEnd,parentId,accIdS);
	}
}