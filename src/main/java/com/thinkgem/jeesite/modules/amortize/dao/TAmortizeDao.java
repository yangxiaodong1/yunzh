/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.amortize.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.entity.AccountAndamortize;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.amortize.entity.TAmortize;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.settleAccounts.entity.TSettleAccounts;

/**
 * 摊销设置DAO接口
 * @author yang
 * @version 2015-12-09
 */
@MyBatisDao
public interface TAmortizeDao extends CrudDao<TAmortize> {
	public void update_status(TAmortize tAmortize);
	
	
	
	/**
	 * 结账统计
	 * 
	 * @return
	 */
	public List<TAmortize> settleAccounts(@Param("accountId") String accId, @Param("fdbid") String fdbid, @Param("accountPeriod") String accountPeriod,@Param("expName")String expName);

	
	/**
	 * 计提折旧
	 * 
	 * @return
	 */
	public List<TAmortize> settleAccountsProvisionDepreciation(@Param("accountId") String accId, @Param("fdbid") String fdbid, @Param("accountPeriod") String accountPeriod, @Param("expName") String expName);

	/**
	 * 摊销查询(chenming)
	 */
	public List<AccountAndamortize> infobyfdbidandaccountid(@Param("fdbid") String fdbid,@Param("accountId") String accId);
	/**
	 * 摊销修改(chenming)
	 */	
	public int update(TAmortize tAmortize);
	
	/**
	 * 根据tVoucherExpId分组查询出来记录
	 * */
	public List<TAmortize> selectbyamortize_account_id(TAmortize tAmortize);
	/**
	 * 更新totalOldPosition +1
	 * @param accId
	 * @param fdbid
	 * @param accountPeriod
	 * @param expName
	 */
	public void updatetotalOldPositionById(@Param("accountId") String accId, @Param("fdbid") String fdbid,  @Param("expName") String expName,@Param("currentPeriod")String Period);
	
	/**
	 * 查询所有的初始余额时候创建的摊销
	 * @param Period
	 * @param fdbid
	 * @return
	 */
	public List<String> selectAccountIdByPeriodAndFdbid(@Param("currentPeriod")String Period,@Param("fdbid")String fdbid);
	/**
	 * 批量跟新原值
	 * @param listtAccounts
	 * @param period
	 * @param fdbid
	 */
	public void updateAmortizeFromOriginalValue(@Param("list")List<TAccount> listtAccounts, @Param("currentPeriod")String period,@Param("fdbid")String fdbid );
}