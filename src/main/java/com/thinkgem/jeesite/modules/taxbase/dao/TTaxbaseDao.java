/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.taxbase.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.taxbase.entity.TTaxbase;

/**
 * 税基数表DAO接口
 * @author zt
 * @version 2016-01-11
 */
@MyBatisDao
public interface TTaxbaseDao extends CrudDao<TTaxbase> {
	/**
	 * 根据税种编号 和公司id 取值
	 * @return
	 */
	public List<TTaxbase> findTTaxbaseByfdbIdAndTaxCategory(@Param("fdbid")String fdbid,@Param("taxCategory")String taxCategory) ;
	/**
	 * 批量保存税金公式
	 * @param listTTaxbases
	 */
	public int savaListTaxbases(@Param("list")List<TTaxbase> listTTaxbases);
	/**
	 * 根据公司id 删除表信息
	 * @param fdbid
	 */
	public void deleteTaxbasesByfdbid(@Param("fdbid")String fdbid,@Param("accountId")String accountId);
	/**
	 * 根据id删除
	 * @param id
	 */
	public void deleteTasbasesByid(@Param("id")String id);
}