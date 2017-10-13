/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.balance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.settleAccounts.entity.TSettleAccounts;

/**
 * 金额平衡表DAO接口
 * 
 * @author zhangtong
 * @version 2015-12-04
 */
@MyBatisDao
public interface TBalanceDao extends CrudDao<TBalance> {
	public List<TBalance> findListByAccountId(@Param("fdbid")String fdbid,@Param("accountP") String accountPeriod, @Param("list") List<String> list);

	/**
	 * 根据科目id查询 它发生的所有的账期取年份 总账页面调用
	 * 
	 * @param accId
	 * @return
	 */

	public List<TBalance> listHappenYear(@Param("accId") String accId, @Param("fdbid") String fdbid);
	
	public TBalance cjsdxxBy(@Param("fdbid")String fdbid,@Param("accountId")String accountId,@Param("accountPeriod")String accountPeriod);
	/*
	 *通过客户id获取该客户的所有账期
	 * */
	public List<String> findAllperiod(String fdbid);
	/*
	 * 判断初始余额表的初始值是否为空
	 */
	public TBalance infoByAccountId(@Param("accountId")String accountId,@Param("accountPeriod")String accountPeriod,@Param("fdbid")String fdbid);
	
	/*
	 * 判断余额表是否有信息
	 * */
	public int isAllUse(String did);
	/**
	 * 结账统计
	 * 
	 * @return
	 */
	public List<TSettleAccounts> settleAccounts(@Param("accountId") String accId, @Param("fdbid") String fdbid, @Param("accountPeriod") String accountPeriod);

	
	/**
	 * 获取科目上一期的 endbalance
	 * @param fdbid
	 * @param accountId
	 * @param accountPeriod
	 * @return
	 */
	public TBalance getLastAccountEndBalance(@Param("fdbid")String fdbid,@Param("accountId")String accountId,@Param("accountPeriod")String accountPeriod);
	/**
	 * 根据科目id 客户的id 账期前后查询
	 * @param accId
	 * @param fdbid
	 * @param accountPeriod
	 * @param periodEnd
	 * @return
	 */
	public List<TBalance>findListTBalanceByInfo(@Param("accId") String accId, @Param("fdbid") String fdbid,
			@Param("accountPeriod") String accountPeriod,
			@Param("periodEnd") String periodEnd,@Param("list")List<String> accIdS);
	
	/**
	 * 根据客户id和账期获取余额表
	 * @param fdbid
	 * @param accountPeriod
	 * @return
	 */
	public List<TBalance> findListByFdbidAndAccountPeriod(@Param("fdbid") String fdbid,@Param("accountPeriod") String accountPeriod);
	/**
	 * 判断是否有下一期
	 * */
	public List<TBalance> latelynextperiod(@Param("fdbid")String fdbid,@Param("accountPeriod")String accountPeriod);
	
	/**
	 * 
	 * @param accountId
	 * @param accountPeriod
	 * @param fdbid
	 * @return
	 */
	public List<TBalance> infoSByAccountId(@Param("accountId")String accountId,@Param("accountPeriod")String accountPeriod,@Param("fdbid")String fdbid);
	
	
	
	/**
	 * 判断下几期是否都有
	 * */
	public int lastHas(@Param("fdbid")String fdbid,@Param("accountPeriod")String accountPeriod,@Param("accId")String accId);
	
	
	/**
	 * 判断当期是否有
	 * */
	public int currentHas(@Param("fdbid")String fdbid,@Param("accountPeriod")String accountPeriod,@Param("accId")String accId);
	/**
	 * 查询最近账期（查询凭证用和上面的查询最近账期不一样）
	 * */
	public	String selectPeriod(@Param("fdbid")String fdbid,@Param("accountPeriod")String accountPeriod);
	/**
	 * 查询所有的账期（查询凭证用和上面的查询所有账期不一样）
	 * */
	public List<String> getAllAccountperiod(@Param("fdbid")String fdbid);
}