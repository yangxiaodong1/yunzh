/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucherexp.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherexpvo;

/**
 * 显示凭证DAO接口
 * @author cy
 * @version 2016-02-19
 */
@MyBatisDao
public interface TVoucherexpvoDao extends CrudDao<TVoucherexpvo> {
	
}