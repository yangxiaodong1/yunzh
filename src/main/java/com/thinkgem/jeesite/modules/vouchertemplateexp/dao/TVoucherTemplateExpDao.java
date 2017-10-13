/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertemplateexp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.vouchertemplateexp.entity.TVoucherTemplateExp;

/**
 * 凭证模板摘要DAO接口
 * @author 凭证模板摘要
 * @version 2015-10-30
 */
@MyBatisDao
public interface TVoucherTemplateExpDao extends CrudDao<TVoucherTemplateExp> {
	/**
	 * 根据模板id获取模板详情
	 * @param templateId
	 * @return
	 */
	public List<TVoucherTemplateExp> getByTempId(@Param("templateId")String templateId);
	
	
	/**
	 * 根据模板id删除
	 * @param templateId
	 * @return
	 */
	public int delByTempId(@Param("templateId")String templateId);
}