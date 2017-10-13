/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.books.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.books.entity.Subsidiaryledge;

/**
 * 凭证摘要和科目DAO接口
 * 
 * @author zhangtong
 * @version 2015-12-04
 */
@MyBatisDao
public interface SubsidiaryledgeDao extends CrudDao<Subsidiaryledge> {
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
			@Param("list")List<String> accIdS);
}