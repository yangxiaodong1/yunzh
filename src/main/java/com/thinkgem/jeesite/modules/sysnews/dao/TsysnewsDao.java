/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sysnews.dao;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sysnews.entity.Tsysnews;

/**
 * 系统消息表DAO接口
 * @author yang
 * @version 2016-01-14
 */
@MyBatisDao
public interface TsysnewsDao extends CrudDao<Tsysnews> {
	public List<Tsysnews> findListByUserId(Tsysnews tsysnews);
	public int insertNewUser(List<Tsysnews> tsysnewslist);
}