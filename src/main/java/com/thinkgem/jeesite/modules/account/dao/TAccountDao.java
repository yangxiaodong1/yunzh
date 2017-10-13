/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.entity.BalanceSum;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.entity.TaccountNext;

/**
 * 科目信息操作DAO接口
 * @author 陈明
 * @version 2015-12-02
 */
@MyBatisDao
public interface TAccountDao extends CrudDao<TAccount> {
	public List<TAccount> findListByGroup(@Param(value="fdbid")String fdbid,@Param(value="accountid")String accountid);
	/**
	 * 在添加科目的时候根据编号
	 * @param parentNumber
	 * @param fdbid
	 * @return
	 */
	public TAccount findListByParent(String parentNumber,String fdbid);
	/**
	 * 该编号是否存在(AccuntId科目名称和fdbid公司id)
	 * @param AccuntId
	 * @param fdbid
	 * @return
	 */
	public int findListByName(String accountName,String fdbid); 
	/**
	 * 判断上级是否有子集(Id和fdbid)
	 * @param AccuntId
	 * @param fdbid
	 * @return
	 */
	public TAccount findListByAccuntId(String Id,String fdbid);
	/**
	 * 判断上级是否有下级科目如果有就不用修改，否则改为1没有下级科目(根据父亲id和公司id)
	 * @param parentId
	 * @param fdbid
	 * @return
	 */
	public int parentCount(String parentId,String fdbid);
	/**
	 * 查询所有的编号进行验证(添加下一级时自动生成编号)
	 * @param parentId
	 * @param fdbid
	 * @return
	 */
	public List<String> countNumber(String AccuntId,String fdbid);
	/**
	 * 通过账期查询
	 * @param fdbid --公司id
	 * @param accountPeriod --账期
	 * @return
	 */
	public List<TaccountNext> balanceInfoBydyq(@Param(value="fdbid")String fdbid,@Param(value="accountPeriod")String accountPeriod);
	/**
	 * 显示余额信息
	 * @param fdbid --公司id
	 * @param accountPeriod --账期
	 * @return
	 */
	public List<TaccountNext> balanceInfo(@Param(value="fdbid")String fdbid,@Param(value="accountPeriod")String accountPeriod);
	/**
	 * 初始余额表的初始余额和剩余额计算总和通过父亲parentid
	 * @param paramStr
	 * @return
	 */
	public BalanceSum sumInfo(@Param(value="parentid")String parentid,@Param(value="accountPeriod")String accountPeriod);
	
	//通过id获取父级的id
	public String parindIdById(String id);
	/**
	 * 根据accountid和name进行模糊查询
	 * @param paramStr
	 * @return
	 */
	public List<TAccount> findAllListByAccountIdOrName(@Param("paramStr")String paramStr,@Param("fdbid")String fdbid);

	/*
	 * 提取科目表的序列id主键
	 * */
	public String accountidxl();
	/*
	 * 添加科目
	 * */
	public int insert(TAccount tAccount);
	/**
	 * 根据公司客户id 查询所有的公司的信息
	 * @param fdbid
	 * @return
	 */
	public List<TAccount> findAllAccountByFdbid(@Param("fdbid")String fdbid);
	/*
	 * 导入科目的存储过程
	 * */
	public String draccount(Map<String, Object> map);
	/**
	 * 修改另一个表的accountid
	 */
	public void updateOtherTableAaccountid(Map<String, Object> map);

	/**
	 * 很据accountid 获取科目信息
	 * @param Id
	 * @param fdbid
	 * @return
	 */
	public TAccount findByAccuntId(String Id,String fdbid);
	/**
	 * 获取科目信息
	 * @param id
	 * @return
	 */
	public TAccount getAccountsInfo(@Param("id")String id,@Param("fdbid")String fdbid);
	/**
	 * 或许科目子级科目获取科目信息
	 * @param id
	 * @return
	 */
	public List<TAccount> getSubAccountsInfo(@Param("parentId")String parentId,@Param("fdbid")String fdbid);
	
	
	/**
	 * 根据名称获取科目信息
	 * @param id
	 * @return
	 */
	public TAccount getAccountsInfoByName(@Param("accountname")String accountname,@Param("fdbid")String fdbid,@Param("accId")String accId);
	
	
	/**
	 * 根据accountid前匹配
	 * @param paramStr
	 * @return
	 */
	public List<TAccount> findAllListByAccountIdBeginWith(@Param("paramStr")String paramStr,@Param("fdbid")String fdbid,@Param("dc")String dc);
	/**
	 * 根据公司id 税率 二级   税率启用的查询
	 * @param fdbid
	 * @return
	 */
	public List<TAccount> findAllListByfdbidLevelTwo(@Param("fdbid")String fdbid);
	public List<TAccount> findAllListByfdbidLevel(@Param("fdbid")String fdbid,@Param("system")int system);
	/**
	 * 修改税率和是否启用的状态
	 * @param fdbid
	 * @return
	 */
	public int updateByfdbidAndAccountid(@Param("rate")String rate,@Param("israteenable")String isEnable,@Param("id")String id);
	
	/**
	 * 根据账期公司id 和list科目编号集合联合查询科目表和科目余额表
	 * @param period
	 * @param fdbid
	 * @param accountIDStrings
	 * @return
	 */
	public List<TAccount> selectAccountAndBalanceByPeriodAndFdbid(@Param("accountPeriod")String period,@Param("fdbid")String fdbid, @Param("list")List<String> accountIDStrings);
	/**
	 * 根据客户id 和科目编号查询
	 * @param accountId
	 * @param fdbid
	 * @return
	 */
	public String selectAccountByAccountIdAndFdbid(@Param("accountId")String accountId,@Param("fdbid")String fdbid);
	/**
	 * 根据客户fdbid判断是否自己创建了科目，如果创建了就不能导入其他公司的科目模板
	 * @param fdbid
	 * @return
	 */
	public int isHaveOwnAccount(@Param("fdbid")String fdbid);	
	
	/**
	 * 获取用户前几级科目
	 * @param fdbid
	 * @param accountid
	 * @param level
	 * @return
	 */
	public List<TAccount> findListByGroupWithLevel(@Param(value="fdbid")String fdbid,@Param(value="accountid")String accountid,@Param(value="level")int level);
	
}