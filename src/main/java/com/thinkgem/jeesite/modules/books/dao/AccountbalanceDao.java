/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.books.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.books.entity.Accountbalance;

/**
 * 总账和科目DAO接口
 * 
 * @author zhangtong
 * @version 2015-12-04
 */
@MyBatisDao
public interface AccountbalanceDao extends CrudDao<Accountbalance> {
	public List<Accountbalance> findListByAccountId(
			@Param("accountP") String accountPeriod,
			@Param("list") List<String> list, @Param("fdbid") String fdbid);

	public List<Accountbalance> findListByAccId(
			@Param("accountP") String accountPeriod,
			@Param("list") List<String> list, @Param("fdbid") String fdbid);

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
			@Param("fdbid") String fdbid,@Param("list")List<String> accIdS);

	/**
	 * 金额发生变化的科目 --总账显示所有发生金额变化的科目调用 科目明细表调用
	 * 
	 * @return
	 */
	public List<Accountbalance> findHappenByAcc(@Param("fdbid") String fdbid,
			@Param("accountPeriod") String accountPeriod,
			@Param("periodEnd") String periodEnd, @Param("accId") String accId,
			@Param("accLevel") String accLevel);
	/**
	 * 科目只显示底层科目  科目明细表调用
	 * 
	 * @return
	 */
	public List<Accountbalance> findHappenByAccSub(@Param("fdbid") String fdbid,
			@Param("accountPeriod") String accountPeriod,
			@Param("periodEnd") String periodEnd, @Param("accId") String accId,
			@Param("accLevel") String accLevel);

	/**
	 * 根据父级的id 查询子级
	 * 
	 * @param fdbid
	 * @param accountPeriod
	 * @param periodEnd
	 * @param parentId
	 * @return
	 */
	public List<Accountbalance> findSublevelAcc(
			@Param("fdbid") String fdbid,
			@Param("accountPeriod") String accountPeriod,
			@Param("periodEnd") String periodEnd,
			@Param("parentId") String parentId);
}