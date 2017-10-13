/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.amortize.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.account.entity.AccountAndamortize;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.amortize.entity.TAmortize;
import com.thinkgem.jeesite.modules.amortize.dao.TAmortizeDao;
import com.thinkgem.jeesite.modules.settleAccounts.entity.TSettleAccounts;

/**
 * 摊销设置Service
 * @author yang
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true)
public class TAmortizeService extends CrudService<TAmortizeDao, TAmortize> {

	public TAmortize get(String id) {
		return super.get(id);
	}
	
	
	@Transactional(readOnly = false)
	//修改状态
	public void update_status(TAmortize tAmortize){
		dao.update_status(tAmortize);
	}
	public List<TAmortize> findList(TAmortize tAmortize) {
		return super.findList(tAmortize);
	}
	
	public Page<TAmortize> findPage(Page<TAmortize> page, TAmortize tAmortize) {
		return super.findPage(page, tAmortize);
	}
	
	@Transactional(readOnly = false)
	public void save(TAmortize tAmortize) {
		super.save(tAmortize);
	}
	
	@Transactional(readOnly = false)
	public void delete(TAmortize tAmortize) {
		super.delete(tAmortize);
	}
	
	
	
	/**
	 * 结账统计
	 * 
	 * @return
	 */
	public List<TAmortize> settleAccounts(String accId,String fdbid,String accountPeriod,String expName){
		return dao.settleAccounts(accId, fdbid, accountPeriod,expName);
	}

	/**
	 * 计提折旧
	 * 
	 * @return
	 */
	public List<TAmortize> settleAccountsProvisionDepreciation(String accId,String fdbid,String accountPeriod,String expName){
		return dao.settleAccountsProvisionDepreciation(accId, fdbid, accountPeriod,expName);
	}
	
	/**
	 * 摊销查询(chenming)
	 */
	public List<AccountAndamortize> infobyfdbidandaccountid(String fdbid,String accId){
		return dao.infobyfdbidandaccountid(fdbid, accId);
	}
	/**
	 * 摊销修改(chenming)
	 */	
	@Transactional(readOnly = false)
	public int update(TAmortize tAmortize){
		return dao.update(tAmortize);
	}
	
	public List<TAmortize> selectbyamortize_account_id(TAmortize tAmortize){
		return dao.selectbyamortize_account_id(tAmortize);
	}
	/**
	 * 更新totalOldPosition +1
	 * @param accId
	 * @param fdbid
	 * @param accountPeriod
	 * @param expName
	 */
	@Transactional(readOnly = false)
	public void updatetotalOldPositionById(String accId,String fdbid, String expName,String Period){
		dao.updatetotalOldPositionById(accId,fdbid,expName,Period);
	}
	/**
	 * 查询所有的初始余额时候创建的摊销
	 * @param Period
	 * @param fdbid
	 * @return
	 */
	public List<String> selectAccountIdByPeriodAndFdbid(String Period,String fdbid) {
		return dao.selectAccountIdByPeriodAndFdbid(Period, fdbid);
	}
	
	/**
	 * 批量跟新原值
	 * @param listtAccounts
	 * @param period
	 * @param fdbid
	 */
	@Transactional(readOnly = false)
	public void updateAmortizeFromOriginalValue(List<TAccount> listtAccounts, String period,String fdbid ){
		dao.updateAmortizeFromOriginalValue(listtAccounts,period,fdbid);
	}
}