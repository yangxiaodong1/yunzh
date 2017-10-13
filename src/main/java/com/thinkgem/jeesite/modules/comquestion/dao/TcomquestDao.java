/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.comquestion.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.comquestion.entity.Tcomquest;

/**
 * 常见问题表DAO接口
 * @author yang
 * @version 2016-01-15
 */
@MyBatisDao
public interface TcomquestDao extends CrudDao<Tcomquest> {
	
}