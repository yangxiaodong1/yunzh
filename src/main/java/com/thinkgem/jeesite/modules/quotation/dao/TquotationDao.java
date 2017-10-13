/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.quotation.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.quotation.entity.Tquotation;

/**
 * 语录DAO接口
 * @author yang
 * @version 2016-01-14
 */
@MyBatisDao
public interface TquotationDao extends CrudDao<Tquotation> {
	public void deleteById(String id);
	public List<Tquotation> findByStartStatus(Tquotation tquotation);//根据启用状态查询
}