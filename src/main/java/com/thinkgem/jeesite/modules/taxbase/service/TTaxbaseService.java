/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.taxbase.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.taxbase.entity.TTaxbase;
import com.thinkgem.jeesite.modules.taxbase.dao.TTaxbaseDao;

/**
 * 税基数表Service
 * @author zt
 * @version 2016-01-11
 */
@Service
@Transactional(readOnly = true)
public class TTaxbaseService extends CrudService<TTaxbaseDao, TTaxbase> {

	public TTaxbase get(String id) {
		return super.get(id);
	}
	
	public List<TTaxbase> findList(TTaxbase tTaxbase) {
		return super.findList(tTaxbase);
	}
	
	public Page<TTaxbase> findPage(Page<TTaxbase> page, TTaxbase tTaxbase) {
		return super.findPage(page, tTaxbase);
	}
	
	@Transactional(readOnly = false)
	public void save(TTaxbase tTaxbase) {
		super.save(tTaxbase);
	}
	
	@Transactional(readOnly = false)
	public void delete(TTaxbase tTaxbase) {
		super.delete(tTaxbase);
	}
	/**
	 * 根据税种编号 和公司id 取值
	 * @return
	 */
	public List<TTaxbase> findTTaxbaseByfdbIdAndTaxCategory(@Param("fdbid")String fdbid,@Param("taxCategory")String taxCategory) {
		return dao.findTTaxbaseByfdbIdAndTaxCategory(fdbid,taxCategory);
	}
	/**
	 * 批量保存税金公式
	 * @param listTTaxbases
	 */
	@Transactional(readOnly = false)
	public int savaListTaxbases(@Param("list")List<TTaxbase> listTTaxbases){
		return dao.savaListTaxbases(listTTaxbases);
	}
	/**
	 * 根据公司id 删除表信息
	 * @param fdbid
	 */
	@Transactional(readOnly = false)
	public void deleteTaxbasesByfdbid(@Param("fdbid")String fdbid,@Param("accountId")String accountId){
		dao.deleteTaxbasesByfdbid(fdbid,accountId);
	}
	/**
	 * 根据id删除
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteTasbasesByid(@Param("id")String id){
		dao.deleteTasbasesByid(id);
	}
}