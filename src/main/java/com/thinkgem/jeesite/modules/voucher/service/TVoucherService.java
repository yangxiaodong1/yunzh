/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucher.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.amortize.entity.TAmortize;
import com.thinkgem.jeesite.modules.amortize.service.TAmortizeService;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.billinfo.dao.TBillInfoDao;
import com.thinkgem.jeesite.modules.billinfo.entity.IntelligentAccountContentResult;
import com.thinkgem.jeesite.modules.billinfo.entity.IntelligentAccountResult;
import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfo;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucher.dao.TVoucherDao;
import com.thinkgem.jeesite.modules.voucherexp.dao.TVoucherExpDao;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherExp;

/**
 * 记账凭证Service
 * @author 记账凭证
 * @version 2015-10-30
 */
@Service
@Transactional(readOnly = true)
public class TVoucherService extends CrudService<TVoucherDao, TVoucher> {

	@Autowired
	TVoucherDao dao;
	
	@Autowired
	TBillInfoDao billDao;
	
	@Autowired
	TVoucherExpDao tVoucherExpDao;
	
	@Autowired
	private TBalanceService tBalanceService;
	
	@Autowired
	private TAmortizeService tAmortizeService;
	
	public TVoucher get(String id) {
		return super.get(id);
	}
	
	public int getByBean(TVoucher tVoucher) {
		return dao.getByBean(tVoucher);
	}
	
	public List<TVoucher> findList(TVoucher tVoucher) {
		return super.findList(tVoucher);
	}
	
	public Page<TVoucher> findPage(Page<TVoucher> page, TVoucher tVoucher) {
		return super.findPage(page, tVoucher);
	}
	
	@Transactional(readOnly = false)
	public void save(TVoucher tVoucher) {
		super.save(tVoucher);
	}
	
	@Transactional(readOnly = false)
	public void delete(TVoucher tVoucher) {
		super.delete(tVoucher);
	}
	
	
	public int	getMaxByAccountPeriod(String accountPeriod,String company){
		return dao.getMaxByAccountPeriod(accountPeriod,company);
	}

	
	/**
	 * 删除修改（自己定义的方法）
	 * */
	@Transactional(readOnly = false)
	public void updateanddelete(TVoucher tVoucher){
		dao.updateanddelete(tVoucher);
	}
	
	/**
	 * 根据客户外键查询
	 * */
	public List<TVoucher> findShowList(TVoucher tVoucher){
		return dao.findShowList(tVoucher);
	}
	
	/**
	 * 获取所有的账期
	 * */
	public List<TVoucher> finperiods(String fdbid){
		return dao.finperiods(fdbid);
	}
	/**
	 * 删除凭证（单条）
	 * **/
	@Transactional(readOnly = false)
	public void deletebyid(String id){
		dao.deletebyid(id);
	}

	public boolean firstLoad(String fdbid){
		return dao.firstLoad(fdbid) > 0;
	}
	
	public List<TVoucher> findShowThree(TVoucher tVoucher){
		return dao.findShowThree(tVoucher);
	}
	
	/**
	 * 查询当前账期最近的一条数据
	 * **/
	public List<TVoucher> selectPeriod(TVoucher tVoucher){
		return dao.selectPeriod(tVoucher);
	}
	
	
	/**
	 * 修改批注信息
	 * **/
	@Transactional(readOnly = false)
	public void updatevoucher(TVoucher tVoucher){
		dao.updatevoucher(tVoucher);
	}
	
	/**
	 * 插入凭证前移动之后的 凭证字号
	 * @param fdbid
	 * @param accountPeriod
	 * @param voucherTitleNumber
	 */
	@Transactional(readOnly = false)
	public void insertVoucher(String fdbid,String accountPeriod,String voucherTitleNumber){
		dao.insertVoucher(fdbid, accountPeriod, voucherTitleNumber);
	}
	
	/**
	 * 保存 智能结账的凭证
	 */
	@Transactional(readOnly = false)
	public void saveIntelligenAccount(List<IntelligentAccountResult> resultList,TCustomer tCustomer){
		User currentUser = UserUtils.getUser();
		for(IntelligentAccountResult intelligentAccountResult : resultList){
			//组装凭证信息
			TVoucher voucher = new TVoucher();
			voucher.setVoucherTitleName("记");
			int maxTitle = this.getMaxByAccountPeriod(
					voucher.getAccountPeriod(), tCustomer.getId());
			voucher.setVoucherTitleNumber((maxTitle + 1) + "");
			voucher.setAccountDate(intelligentAccountResult.getAccountDate());
			voucher.setAccountPeriod(tCustomer.getCurrentperiod());
			voucher.setRecieptNumber("1");
			voucher.setTotalAmount(intelligentAccountResult.getTotalAmount().doubleValue());
			voucher.setUserName(currentUser.getName());
			voucher.setTouchingDate(new Date());
			voucher.setIsCheck("0");
			voucher.setIsNewRecord(true);
			voucher.setFdbid(tCustomer.getId());
			voucher.setCount(intelligentAccountResult.getIntelligentAccountContentResults().size());
			voucher.setVoucherTitleNumber(String.valueOf(Integer
					.parseInt(voucher.getVoucherTitleNumber())));
			
			this.save(voucher);
			String voucherIdString = voucher.getId() + "";
			
			//组装详情
			List<TVoucherExp> voucherExpList = new ArrayList<TVoucherExp>();
			List<IntelligentAccountContentResult> contentResults = intelligentAccountResult.getIntelligentAccountContentResults();
			int companySystem =tCustomer.getSystem();
			String tVoucherExpId = null;
			for(IntelligentAccountContentResult contentResult : contentResults){
				
				TVoucherExp exp = new TVoucherExp();
				exp.setExpRowNumber(contentResult.getSubject());
				exp.setExp(contentResult.getSummary());
				exp.setAccountId(contentResult.getSubject());
				exp.setDebit(contentResult.getDebit().doubleValue());
				exp.setCredit(contentResult.getCredit().doubleValue());
				exp.setAmortize(false);
				exp.setIsNewRecord(true);
				exp.setFdbid(tCustomer.getId());
				tVoucherExpDao.insert(exp);
				if (contentResult.isAmortizeFlag()) {
					tVoucherExpId = exp.getId();
				}
				tBalanceService.maintainBalance(exp.getAccountId(),
						tCustomer.getCurrentperiod(), tCustomer.getId(),
						exp.getDebit(), exp.getCredit(),
						exp.getExp(), 0, null, null, null, null, 1,
						tCustomer.getCurrentperiod(),companySystem);
				voucherExpList.add(exp);
			}
			
			//更改关联票据信息
			TBillInfo billinfoB = intelligentAccountResult.getBillInfo();
			billinfoB.setBillStatus("3");// 已做账
			billinfoB.setRelateVoucher(voucherIdString);
			billDao.update(billinfoB);
		
			TAmortize	amortize = intelligentAccountResult.getAmortize();
			if (null != amortize && StringUtils.isNoneBlank(amortize.getExpName())) {
				amortize.setId(UUID.randomUUID().toString());
				amortize.settVoucherExpId(tVoucherExpId);
				amortize.setAccountId(intelligentAccountResult.getDebitAccountId());
				amortize.setIsNewRecord(true);
				amortize.setDc("1");
				amortize.setAmortizeStatus("1");
				amortize.setIfInit("2");
				// add by wub 20151224
				amortize.setFdbid(tCustomer.getId());
				amortize.setCurrentPeriod(new SimpleDateFormat("yyyy-MM-dd").format(intelligentAccountResult.getAccountDate()));
				tAmortizeService.save(amortize);
				amortize.setId(UUID.randomUUID().toString());
				amortize.setIsNewRecord(true);
				amortize.setAccountId(intelligentAccountResult.getCreditAccountId());
				amortize.setDc("-1");
				tAmortizeService.save(amortize);
			}
			
		}
	}
}