/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucherexp.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherExp;
import com.thinkgem.jeesite.modules.voucherexp.dao.TVoucherExpDao;

/**
 * 凭证摘要Service
 * @author 凭证摘要
 * @version 2015-11-27
 */
@Service
@Transactional(readOnly = true)
public class TVoucherExpService extends CrudService<TVoucherExpDao, TVoucherExp> {

	public TVoucherExp get(String id) {
		return super.get(id);
	}
	
	public List<TVoucherExp> findList(TVoucherExp tVoucherExp) {
		return super.findList(tVoucherExp);
	}
	
	public Page<TVoucherExp> findPage(Page<TVoucherExp> page, TVoucherExp tVoucherExp) {
		return super.findPage(page, tVoucherExp);
	}
	 public List<TVoucherExp> findbytvid(String tvoucherId){
		 return dao.findbytvid(tvoucherId);
	 }
	@Transactional(readOnly = false)
	public void save(TVoucherExp tVoucherExp) {
		super.save(tVoucherExp);
	}
	
	@Transactional(readOnly = false)
	public void deletebytvid(String tvid){
		 dao.deletebytvid(tvid);
	 }
	
	@Transactional(readOnly = false)
	public void delete(TVoucherExp tVoucherExp) {
		super.delete(tVoucherExp);
	}
	@Transactional(readOnly = false)
	public Map<String, Object> backCheckout(Map<String, Object> map){
		 return dao.backCheckout(map);
	 }
	public String findMixAccountId(){
		return dao.findMixAccountId();
	}
	
	@Transactional(readOnly = false)
	public Map<String, Object>  deleteTvourcher(Map<String, Object> tvid){
		return dao.deleteTvourcher(tvid);
	}
	/**
	  * chengming当科目表添加子级的第一个科目的时候如果凭证摘要表有关联需要修改
	  * */
	@Transactional(readOnly = false)
	 public void  updatebyaccountid(@Param(value="accountId")String accountId,@Param(value="accountName")String accountName,@Param(value="faccountId")String faccountId){
		 dao.updatebyaccountid(accountId, accountName, faccountId);
	} 
	 /**
	  * chengming通过科目id查询
	  * */
	 public TVoucherExp  getbyaccountId(@Param(value="accountId")String accountId){
		 return dao.getbyaccountId(accountId);
	 }
	 /**
	  * 根据两个科目id查询是否有相关的凭证如果 存在 返回一
	  * @param accountPeriod
	  * @param fdbid
	  * @param accIdDebit
	  * @param accIdCredit
	  * @return
	  */
	 public Integer getCountByAccIDAndAccID(@Param("accountPeriod")String accountPeriod,@Param("fdbid")String fdbid, @Param("accIdDebit")String accIdDebit,@Param("accIdCredit")String accIdCredit) {
		return dao.getCountByAccIDAndAccID(accountPeriod, fdbid, accIdDebit, accIdCredit);
	}
}