/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.customer.entity.TAppendix;

/**
 * 附件添加DAO接口
 * @author cy
 * @version 2015-12-16
 */
@MyBatisDao
public interface TAppendixDao extends CrudDao<TAppendix> {
	
	public List<TAppendix> findByState(String duiyingstate);
	public List<TAppendix> findBytcusid(String cusid);
	/**
	 *删除语句
	 * **/
	public void deleteInfo(String id);
	
	/**
	 * 修改保存，和用户信息同时保存
	 * **/
	public void updateCusid(TAppendix tAppendix);
	
	
	public void insertNew(TAppendix tAppendix);

	public String selectId();
	
}