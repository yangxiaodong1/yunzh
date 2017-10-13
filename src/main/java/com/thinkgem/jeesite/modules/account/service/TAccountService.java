/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.account.dao.TAccountDao;
import com.thinkgem.jeesite.modules.account.entity.BalanceSum;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.entity.TaccountNext;

/**
 * 科目信息操作Service
 * 
 * @author 陈明
 * @version 2015-12-02
 */
@Service
@Transactional(readOnly = true)
public class TAccountService extends CrudService<TAccountDao, TAccount> {

	private static TAccountDao tAccountDao = SpringContextHolder.getBean(TAccountDao.class);

	public TAccount get(String id) {
		return tAccountDao.get(id);
	}

	public List<TAccount> findList(TAccount tAccount) {
		return super.findList(tAccount);
	}

	public Page<TAccount> findPage(Page<TAccount> page, TAccount tAccount) {
		return super.findPage(page, tAccount);
	}

	@Transactional(readOnly = false)
	public void save(TAccount tAccount) {
		super.save(tAccount);
	}

	@Transactional(readOnly = false)
	public void delete(TAccount tAccount) {
		super.delete(tAccount);
	}

	public List<TAccount> findListByGroup(String fdbid,String accountid) {
		return tAccountDao.findListByGroup(fdbid,accountid);
	}

	public TAccount findListByParent(String parentNumber, String fdbid) {
		return tAccountDao.findListByParent(parentNumber, fdbid);
	}

	public int findListByNumber(String name, String fdbid) {
		return tAccountDao.findListByName(name, fdbid);
	}

	public TAccount findListByAccuntId(String AccuntId, String fdbid) {
		return tAccountDao.findListByAccuntId(AccuntId, fdbid);
	}

	public int parentCount(String parentId, String fdbid) {
		return tAccountDao.parentCount(parentId, fdbid);
	}

	public List<String> countNumber(String accuntId, String fdbid) {
		return tAccountDao.countNumber(accuntId, fdbid);
	}

	public List<TaccountNext> balanceInfo(String fdbid, String accountPeriod) {
		return tAccountDao.balanceInfo(fdbid, accountPeriod);
	}

	public BalanceSum sumInfo(String parentid, String cjddiq) {
		return tAccountDao.sumInfo(parentid, cjddiq);
	}

	public String parindIdById(String id) {
		return tAccountDao.parindIdById(id);
	}

	public List<TAccount> findAllListByAccountIdOrName(String paramStr,String fdbid) {
		return dao.findAllListByAccountIdOrName(paramStr, fdbid);
	}

	/**
	 * 根据公司客户id 查询所有的公司的信息
	 * 
	 * @param fdbid
	 * @return
	 */
	public List<TAccount> findAllAccountByFdbid(@Param("fdbid") String fdbid) {
		return dao.findAllAccountByFdbid(fdbid);
	}

	public List<TaccountNext> balanceInfoBydyq(String fdbid, String accountPeriod) {
		return tAccountDao.balanceInfoBydyq(fdbid, accountPeriod);
	}

	@Transactional(readOnly = false)
	public String accountidxl() {
		return tAccountDao.accountidxl();
	}

	@Transactional(readOnly = false)
	public int insert(TAccount tAccount) {
		return dao.insert(tAccount);
	}

	@Transactional(readOnly = false)
	public String draccount(Map<String, Object> map) {
		return dao.draccount(map);
	}

	/**
	 * 失算余额表的平衡
	 */
	@Transactional(readOnly = false)
	public void updateOtherTableAaccountid(Map<String, Object> map) {
		dao.updateOtherTableAaccountid(map);
	}
	/**
	 * 获取科目的所有上级科目
	 * 
	 * @return
	 */
	public List<TAccount> getParentAccounts(String id) {
		List<TAccount> pareAccounts = new ArrayList<TAccount>();
		if (com.thinkgem.jeesite.common.utils.StringUtils.isNotBlank(id) && !"0".equals(id)) {
			TAccount pareAccount = dao.get(id);
			if (null != pareAccount) {
				pareAccounts.add(pareAccount);
				pareAccounts.addAll(getParentAccounts(pareAccount.getParentId().getId()));
			}
		}
		return pareAccounts;
	}
	/**
	 * 获取科目信息
	 * @param id
	 * @return
	 */
	public TAccount getAccountsInfo(String id,String fdbid){
		return dao.getAccountsInfo(id,fdbid);
	}
	/**
	 * 获取科目子级科目获取科目信息
	 * @param id
	 * @return
	 */
	public List<TAccount> getSubAccountsInfo(String parentId,String fdbid){
		return dao.getSubAccountsInfo(parentId,fdbid);
	}
	
	/**
	 * 根据名称获取科目信息
	 * @param id
	 * @return
	 */
	public TAccount getAccountsInfoByName(String accountname,String fdbid,String accId){
		return dao.getAccountsInfoByName(accountname,fdbid,accId);
	}

	/**
	 * 根据accountid前匹配
	 * @param paramStr
	 * @param fdbid
	 * @return
	 */
	public List<TAccount> findAllListByAccountIdBeginWith(String paramStr,String fdbid,String dc) {
		return dao.findAllListByAccountIdBeginWith(paramStr, fdbid,dc);
	}
	/**
	 * 根据公司id 税率 二级   税率启用的查询
	 * @param fdbid
	 * @return
	 */
	public List<TAccount> findAllListByfdbidLevelTwo(@Param("fdbid")String fdbid){
		return dao.findAllListByfdbidLevelTwo(fdbid);
	}
	@Transactional(readOnly = false)
	public int updateByfdbidAndAccountid(String rate,String isEnable,String id){
		return dao.updateByfdbidAndAccountid(rate,isEnable,id);
	}
	/**
	 * 根据账期公司id 和list科目编号集合联合查询科目表和科目余额表
	 * @param period
	 * @param fdbid
	 * @param accountIDStrings
	 * @return
	 */
	public List<TAccount> selectAccountAndBalanceByPeriodAndFdbid(String period,String fdbid, List<String> accountIDStrings){
		return dao.selectAccountAndBalanceByPeriodAndFdbid(period,fdbid,accountIDStrings);
	}
	/**
	 * 根据客户id 和科目编号查询
	 * @param accountId
	 * @param fdbid
	 * @return
	 */
	public String selectAccountByAccountIdAndFdbid(String accountId,String fdbid){
		return dao.selectAccountByAccountIdAndFdbid(accountId,fdbid);
	}
	public List<TAccount> findAllListByfdbidLevel(@Param("fdbid")String fdbid,@Param("system")int system){
		return dao.findAllListByfdbidLevel(fdbid,system);
	}
	public int isHaveOwnAccount(String fdbid){
		return dao.isHaveOwnAccount(fdbid);
	}
	
	public List<TAccount> findListByGroupWithLevel(String fdbid,String accountid,int level) {
		return tAccountDao.findListByGroupWithLevel(fdbid,accountid,level);
	}
}