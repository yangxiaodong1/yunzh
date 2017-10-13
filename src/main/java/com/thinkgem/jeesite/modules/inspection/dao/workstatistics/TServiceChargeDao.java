/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.dao.workstatistics;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TServiceCharge;

/**
 * 收费审核DAO接口
 * @author xuxiaoong
 * @version 2015-11-25
 */
@MyBatisDao
public interface TServiceChargeDao extends CrudDao<TServiceCharge> {
	public List<TServiceCharge> findListBycustomerIdAndPayeeMan(TServiceCharge tServiceCharge);
	public String getMaxServicedate(TServiceCharge tServiceCharge);
	public String getMaxServiceChargeNo(TServiceCharge tServiceCharge);
}