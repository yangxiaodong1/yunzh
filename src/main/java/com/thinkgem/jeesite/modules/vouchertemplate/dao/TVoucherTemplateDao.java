/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertemplate.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.vouchertemplate.entity.TVoucherTemplate;

/**
 * 凭证模板DAO接口
 * @author 凭证模板
 * @version 2015-10-30
 */
@MyBatisDao
public interface TVoucherTemplateDao extends CrudDao<TVoucherTemplate> {
	
	/**
	 * 根据模板类型获取模板
	 * @param templateTypeId
	 * @return
	 */
	public List<TVoucherTemplate> findAllListByType(@Param("templateTypeId")String templateTypeId,@Param("fdbid")String fdbid);
	
	/**
	 * 删除模板
	 * @param templateTypeId
	 * @return
	 */
	public int deleteById(@Param("id")String id);
	/**
	 * 根据公司id和类型
	 * @param templateTypeId
	 * @param fdbid
	 * @return
	 */
	public List<TVoucherTemplate> findAllListByTypeAndFdbid(@Param("templateTypeId")String templateTypeId,@Param("fdbid")String fdbid);
	/**
	 * 根据公司id和类型
	 * @param templateTypeId
	 * @param fdbid
	 * @return
	 */
	public TVoucherTemplate findAllListByIdAndFdbid(@Param("id")String id,@Param("fdbid")String fdbid);
}