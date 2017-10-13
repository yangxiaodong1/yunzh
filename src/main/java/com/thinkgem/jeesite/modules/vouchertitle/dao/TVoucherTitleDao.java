/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertitle.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.vouchertitle.entity.TVoucherTitle;

/**
 * 凭证标题DAO接口
 * @author 凭证标题
 * @version 2015-10-30
 */
@MyBatisDao
public interface TVoucherTitleDao extends CrudDao<TVoucherTitle> {
	/**
	 * 获取默认的凭证字
	 * @return
	 */
	public TVoucherTitle getDefuault();
}