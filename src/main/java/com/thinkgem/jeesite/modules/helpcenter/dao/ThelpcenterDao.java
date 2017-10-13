/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.helpcenter.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.helpcenter.entity.Thelpcenter;

/**
 * 帮助中心DAO接口
 * @author yang
 * @version 2016-01-15
 */
@MyBatisDao
public interface ThelpcenterDao extends CrudDao<Thelpcenter> {
	
}