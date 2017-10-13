/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucherexp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherExp;

/**
 * 凭证摘要DAO接口
 * @author 凭证摘要
 * @version 2015-11-27
 */
@MyBatisDao
public interface TVoucherExpDao extends CrudDao<TVoucherExp> {

	public String findMixAccountId();

	/**
	 * 根据记账外键来获取集合
	 * */
	 public List<TVoucherExp> findbytvid(String tvoucherId);
	 /**
	  * 根据记账外键来删除该账单的信息
	  * */
	 public void deletebytvid(String tvid);
	 
	 public Map<String, Object> backCheckout(Map<String, Object> map);
	 
	 /**
	  * 删除凭证调用存储过程
	  * **/
	 public Map<String, Object>  deleteTvourcher(Map<String, Object> tvid);
	 
	 /**
	  * chengming通过科目id查询
	  * */
	 public TVoucherExp  getbyaccountId(@Param(value="accountId")String accountId);
	  /**
	  * chengming当科目表添加子级的第一个科目的时候如果凭证摘要表有关联需要修改
	  * */
	 public void  updatebyaccountid(@Param(value="accountId")String accountId,@Param(value="accountName")String accountName,@Param(value="faccountId")String faccountId);  
	 /**
	  * 根据两个科目id查询是否有相关的凭证如果 存在 返回一
	  * @param accountPeriod
	  * @param fdbid
	  * @param accIdDebit
	  * @param accIdCredit
	  * @return
	  */
	 public Integer getCountByAccIDAndAccID(@Param("accountPeriod")String accountPeriod,@Param("fdbid")String fdbid, @Param("accIdDebit")String accIdDebit,@Param("accIdCredit")String accIdCredit) ;
}