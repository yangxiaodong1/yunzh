/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.backupandrecover.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.backupandrecover.entity.TbackupRecover;

/**
 * 备份与回复DAO接口
 * @author 备份与回复
 * @version 2016-02-03
 */
@MyBatisDao
public interface TbackupRecoverDao extends CrudDao<TbackupRecover> {
	public Map<String, Object> tbackuprecover(Map<String, Object> map);
	public  List<TbackupRecover> findListbyfdbid(@Param(value="fdbid")String fdbid);
}