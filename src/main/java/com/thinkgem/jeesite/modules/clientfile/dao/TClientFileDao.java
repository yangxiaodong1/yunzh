/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clientfile.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.clientfile.entity.TClientFile;

/**
 * 添加图片DAO接口
 * @author yang
 * @version 2016-03-12
 */
@MyBatisDao
public interface TClientFileDao extends CrudDao<TClientFile> {
	
}