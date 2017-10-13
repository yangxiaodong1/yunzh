/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	/**
	 * 找到该公司的所有部门
	 * @param entity
	 * @return
	 */
	public List<Office> findByParentIds(Office office);
	public List<Office> findByParentIdszNodes(Office office);
	public void deletes(Office office);
	public void insertCompany(Office office);
	public String getbyOfficeId(String id);
	public void insertChargeCompany(Office office);//插入记账公司yang
	public String getautoid();//查询下一个id
	public String getautoidfile();
	public void updatenew(Office office);
}
