/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.books.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.books.entity.Accountbalance;
import com.thinkgem.jeesite.modules.books.dao.AccountbalanceDao;

/**
 * 总账和科目Service
 * 
 * @author zhangtong
 * @version 2015-12-04
 */
@Service
@Transactional(readOnly = true)
public class AccountbalanceService extends
		CrudService<AccountbalanceDao, Accountbalance> {

	public Accountbalance get(String id) {
		return super.get(id);
	}

	public List<Accountbalance> findList(Accountbalance accountbalance) {
		return super.findList(accountbalance);
	}

	public Page<Accountbalance> findPage(Page<Accountbalance> page,
			Accountbalance accountbalance) {
		return super.findPage(page, accountbalance);
	}

	@Transactional(readOnly = false)
	public void save(Accountbalance accountbalance) {
		super.save(accountbalance);
	}

	@Transactional(readOnly = false)
	public void delete(Accountbalance accountbalance) {
		super.delete(accountbalance);
	}

	/**
	 * 根据科目编号和账期查询
	 * 
	 * @param accountPeriod
	 * @param list
	 * @return
	 */
	public List<Accountbalance> findListByAccountId(
			@Param("accountP") String accountPeriod,
			@Param("list") List<String> list, @Param("fdbid") String fdbid) {
		return dao.findListByAccountId(accountPeriod, list, fdbid);
	}

	/**
	 * 根据科目编号和账期查询
	 * 
	 * @param accountPeriod
	 * @param list
	 * @return
	 */
	public List<Accountbalance> findListByAccId(
			@Param("accountP") String accountPeriod,
			@Param("list") List<String> list, @Param("fdbid") String fdbid) {
		return dao.findListByAccId(accountPeriod, list, fdbid);
	}

	/**
	 * 根据年期查询
	 * 
	 * @param accYear
	 * @param id
	 * @param fdbid
	 * @return
	 */
	public List<Accountbalance> findListByAccIdLikeYear(
			@Param("accYear") String accYear, @Param("id") String id,
			@Param("fdbid") String fdbid,@Param("list")List<String> accIdS) {
		return dao.findListByAccIdLikeYear(accYear, id, fdbid,accIdS);
	}

	/**
	 * 金额发生变化的科目 --总账显示所有发生金额变化的科目调用 科目明细表调用
	 * 
	 * @return
	 */
	public List<Accountbalance> findHappenByAcc(@Param("fdbid") String fdbid,
			@Param("accountPeriod") String accountPeriod,
			@Param("periodEnd") String periodEnd, @Param("accId") String accId,
			@Param("accLevel") String accLevel) {
		return dao.findHappenByAcc(fdbid, accountPeriod, periodEnd, accId,
				accLevel);
	}
	/**
	 * 科目只显示底层科目  科目明细表调用
	 * @param fdbid
	 * @param accountPeriod
	 * @param periodEnd
	 * @param accId
	 * @param accLevel
	 * @return
	 */
	public List<Accountbalance> findHappenByAccSub(@Param("fdbid") String fdbid,
			@Param("accountPeriod") String accountPeriod,
			@Param("periodEnd") String periodEnd, @Param("accId") String accId,
			@Param("accLevel") String accLevel){
		return dao.findHappenByAccSub(fdbid, accountPeriod, periodEnd, accId,
				accLevel);
	}
	/**
	 * 根据父级的id 查询子级
	 * 
	 * @param fdbid
	 * @param accountPeriod
	 * @param periodEnd
	 * @param parentId
	 * @return
	 */
	public List<Accountbalance> findSublevelAcc(@Param("fdbid") String fdbid,
			@Param("accountPeriod") String accountPeriod,
			@Param("periodEnd") String periodEnd,
			@Param("parentId") String parentId) {
		return dao.findSublevelAcc(fdbid, accountPeriod, periodEnd, parentId);
	}
}