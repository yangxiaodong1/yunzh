/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.taxbaseformula.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.taxbase.entity.TTaxbase;
import com.thinkgem.jeesite.modules.taxbaseformula.entity.VTaxbaseformula;

/**
 * 税率公式和科目表DAO接口
 * @author zt
 * @version 2016-01-11
 */
@MyBatisDao
public interface VTaxbaseformulaDao extends CrudDao<VTaxbaseformula> {
	/**
	 * 根据税种编号 和公司id 取值
	 * @return
	 */
	public List<VTaxbaseformula> findTTaxbaseFormulaByfdbIdAndTaxCategory(@Param("fdbid")String fdbid,@Param("taxCategory")String taxCategory);
}