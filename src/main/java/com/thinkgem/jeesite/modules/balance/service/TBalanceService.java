/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.balance.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.thinkgem.jeesite.common.utils.StringUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.account.dao.TAccountDao;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.balance.dao.TBalanceDao;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.settleAccounts.entity.TSettleAccounts;

/**
 * 金额平衡表Service
 * 
 * @author zhangtong
 * @version 2015-12-04
 */
@Service
@Transactional(readOnly = true)
public class TBalanceService extends CrudService<TBalanceDao, TBalance> {

	@Autowired
	private TAccountDao tAccountDao;

	public TBalance get(String id) {
		return super.get(id);
	}

	public List<TBalance> findList(TBalance tBalance) {
		return super.findList(tBalance);
	}

	public Page<TBalance> findPage(Page<TBalance> page, TBalance tBalance) {
		return super.findPage(page, tBalance);
	}

	@Transactional(readOnly = false)
	public void save(TBalance tBalance) {
		super.save(tBalance);
	}

	@Transactional(readOnly = false)
	public void delete(TBalance tBalance) {
		super.delete(tBalance);
	}

	public List<TBalance> findListByAccountId(@Param("fdbid") String fdbid, @Param("accountP") String accountPeriod, @Param("list") List<String> list) {
		return dao.findListByAccountId(fdbid, accountPeriod, list);
	}

	/**
	 * 根据科目id查询 它发生的所有的账期取年份 总账页面调用 科目明细账调用
	 * 
	 * @param accId
	 * @return
	 */
	public List<TBalance> listHappenYear(@Param("accId") String accId, @Param("fdbid") String fdbid) {
		return dao.listHappenYear(accId, fdbid);
	}

	public TBalance infoByAccountId(String accountId, String cjddiq, String fdbid) {
		return dao.infoByAccountId(accountId, cjddiq, fdbid);
	}

	public int isAllUse(String did) {
		return dao.isAllUse(did);
	}

	/**
	 * 结账统计
	 * 
	 * @param accId
	 * @param fdbid
	 * @param accountPeriod
	 * @return
	 */
	public List<TSettleAccounts> settleAccounts(String accId, String fdbid, String accountPeriod) {
		return dao.settleAccounts(accId, fdbid, accountPeriod);
	}

	public TBalance cjsdxxBy(String fdbid, String accountId, String accountPeriod) {
		return dao.cjsdxxBy(fdbid, accountId, accountPeriod);
	}

	public List<String> findAllperiod(String fdbid) {
		return dao.findAllperiod(fdbid);
	}

	/**
	 * 余额维护
	 * @param accountId
	 * @param accountPeriod
	 * @param fdbid
	 * @param debit
	 * @param credit
	 * @param expInfo
	 * @param beginbalance
	 * @param ytddebittotalamount
	 * @param ytdcredittotalamount
	 * @param ytdamountfor
	 * @param endbalance
	 * @param isChild 1 维护子级 2  维护父级
	 */
	@Transactional(readOnly = false)
	public void maintainBalance(String accountId, String accountPeriod, String fdbid, double debit, double credit, String expInfo,double beginbalance,String ytddebittotalamount,String ytdcredittotalamount,String ytdamountfor,String endbalance,int isChild,String currentPeriod,int companySystem) {
		TAccount account = tAccountDao.findByAccuntId(accountId, fdbid);
		List<TBalance> balances = new ArrayList<TBalance>();
		if(1 == isChild){
			balances = dao.infoSByAccountId(accountId, accountPeriod, fdbid);
		}else if(2 == isChild){
			TBalance balance = 	dao.infoByAccountId(accountId, accountPeriod, fdbid);
			if (null == balance) {
				balance = new TBalance();
				balance.setFdbid(fdbid);
				balance.setAccountId(accountId);
				balance.setAccountPeriod(accountPeriod);
				balance.setBeginbalance("0");
				balance.setDebittotalamount("0");
				balance.setCredittotalamount("0");
				balance.setYtddebittotalamount("0");
				balance.setYtdcredittotalamount("0");
				balance.setEndbalance("0");
				balance.setFcur("RMB");
				balance.setEndbalance("0");
				balance.setAmountfor("0");
				balance.setYtdamountfor("0");
			}
			balances.add(balance);
		}
		
		
		if (CollectionUtils.isNotEmpty(balances)) {
			for (TBalance balance : balances) {
				//如果是当期的则全部维护，下一期的则只维护年
				if(currentPeriod.equals(balance.getAccountPeriod())){
					beginbalance = 0;
					if (debit > 0) {
						beginbalance = Double.parseDouble(balance.getCredittotalamount()) + Double.parseDouble(balance.getEndbalance()) - Double.parseDouble(balance.getDebittotalamount());
					} else if (credit > 0) {
						beginbalance = -(Double.parseDouble(balance.getDebittotalamount()) + (-Double.parseDouble(balance.getEndbalance())) - Double.parseDouble(balance.getCredittotalamount()));
					}
					ytddebittotalamount = debit + "";
					ytdcredittotalamount = credit + "";
					ytdamountfor = debit - credit+ "";
					endbalance = debit - credit+ "";
					balance = mainBlance(balance,account, accountId,  accountPeriod,  fdbid,  debit,  credit,  expInfo,companySystem);
					balance.setIsNewRecord(false);
					save(balance);
				}else{
					if(null == endbalance){
						endbalance = "0.00";
					}
					if(null == ytddebittotalamount){
						ytddebittotalamount = "0.00";
					}
					if(null == ytdcredittotalamount){
						ytdcredittotalamount = "0.00";
					}
					if(null == ytdamountfor){
						ytdamountfor = "0.00";
					}
					balance.setBeginbalance(new BigDecimal(balance.getBeginbalance()).add(new BigDecimal(endbalance)).toString());
					balance.setYtddebittotalamount(new BigDecimal(balance.getYtddebittotalamount()).add(new BigDecimal(ytddebittotalamount)).toString());
					balance.setYtdcredittotalamount(new BigDecimal(balance.getYtdcredittotalamount()).add(new BigDecimal(ytdcredittotalamount)).toString());
					//add by : zt
					if (companySystem == 1) {	//小企业
						if (!expInfo.equals("结转本期损益")) {
							// 损益类科目 维护本期和本年的损益发生额
							if (null != account && StringUtils.isNotBlank(account.getAccuntId()) && account.getAccuntId().startsWith("5")) {
								balance.setYtdamountfor(new BigDecimal(balance.getYtdamountfor()).add(new BigDecimal(ytdamountfor)).toString());
							}
						}
						if (!expInfo.equals("结转以前年度损益") && null != account && StringUtils.isNotBlank(account.getAccuntId()) && account.getAccuntId().startsWith("6")) {
							balance.setYtdamountfor(new BigDecimal(balance.getYtdamountfor()).add(new BigDecimal(ytdamountfor)).toString());
						}	
					} else if(companySystem == 2) {	//新规则
						if ((!expInfo.equals("结转以前年度损益")|| !expInfo.equals("结转本期损益")) && null != account && StringUtils.isNotBlank(account.getAccuntId()) && account.getAccuntId().startsWith("6")) {
							balance.setYtdamountfor(new BigDecimal(balance.getYtdamountfor()).add(new BigDecimal(ytdamountfor)).toString());
						}
					}					
					
					balance.setEndbalance(new BigDecimal(balance.getEndbalance()).add(new BigDecimal(endbalance)).toString());
					balance.setIsNewRecord(false);
					save(balance);
				}
				// 判断父级是否有 如果有注意维护
				if (null != account && StringUtils.isNotBlank(account.getParentId().getId()) && !"0".equals(account.getParentId().getId())) {
					maintainBalance(account.getParentId().getId(), balance.getAccountPeriod(), fdbid, debit, credit, expInfo,beginbalance,ytddebittotalamount,ytdcredittotalamount,ytdamountfor,endbalance,2,currentPeriod,companySystem);
				}
			}

		} else {
			TBalance balance = null;
			balance = mainBlance(balance,account, accountId,  accountPeriod,  fdbid,  debit,  credit,  expInfo,companySystem);
			save(balance);
			// 判断父级是否有 如果有注意维护
			if (null != account && StringUtils.isNotBlank(account.getParentId().getId()) && !"0".equals(account.getParentId().getId())) {
				maintainBalance(account.getParentId().getId(), balance.getAccountPeriod(), fdbid, debit, credit, expInfo,beginbalance,ytddebittotalamount,ytdcredittotalamount,ytdamountfor,endbalance,2,currentPeriod,companySystem);
			}
		}
	}

	/**
	 * 获取科目上一期的 endbalance
	 * 
	 * @param fdbid
	 * @param accountId
	 * @param accountPeriod
	 * @return
	 */
	public TBalance getLastAccountEndBalance(String fdbid, String accountId, String accountPeriod) {
		TBalance balance = dao.getLastAccountEndBalance(fdbid, accountId, accountPeriod);
		if (null == balance) {
			balance = new TBalance();
			balance.setBeginbalance(BigDecimal.ZERO.toString());
			balance.setDebittotalamount(BigDecimal.ZERO.toString());
			balance.setCredittotalamount(BigDecimal.ZERO.toString());
			balance.setYtddebittotalamount(BigDecimal.ZERO.toString());
			balance.setYtdcredittotalamount(BigDecimal.ZERO.toString());
			balance.setEndbalance(BigDecimal.ZERO.toString());
		}
		return balance;
	}

	/**
	 * 根据科目id 客户的id 账期前后查询
	 * 
	 * @param accId
	 * @param fdbid
	 * @param accountPeriod
	 * @param periodEnd
	 * @return
	 */
	public List<TBalance> findListTBalanceByInfo(@Param("accId") String accId, @Param("fdbid") String fdbid, @Param("accountPeriod") String accountPeriod, @Param("periodEnd") String periodEnd,
			@Param("list") List<String> accIdS) {
		return dao.findListTBalanceByInfo(accId, fdbid, accountPeriod, periodEnd, accIdS);

	}

	/**
	 * 根据客户id和账期获取余额表
	 * 
	 * @param fdbid
	 * @param accountPeriod
	 * @return
	 */
	public List<TBalance> findListByFdbidAndAccountPeriod(String fdbid, String accountPeriod) {
		return dao.findListByFdbidAndAccountPeriod(fdbid, accountPeriod);
	}

	/**
	 * 判断是否有下一期
	 * */
	public List<TBalance> latelynextperiod(String fdbid, String accountPeriod) {
		return dao.latelynextperiod(fdbid, accountPeriod);
	}
	
	
	public TBalance mainBlance(TBalance balance,TAccount account,String accountId, String accountPeriod, String fdbid, double debit, double credit, String expInfo,int companySystem){
		if (null == balance) {
			balance = new TBalance();
			balance.setFdbid(fdbid);
			balance.setAccountId(accountId);
			balance.setAccountPeriod(accountPeriod);
			balance.setBeginbalance("0");
			balance.setDebittotalamount("0");
			balance.setCredittotalamount("0");
			balance.setYtddebittotalamount("0");
			balance.setYtdcredittotalamount("0");
			balance.setEndbalance("0");
			balance.setFcur("RMB");
			balance.setEndbalance("0");
			balance.setAmountfor("0");
			balance.setYtdamountfor("0");
		}
		if(!accountPeriod.equals(balance.getAccountPeriod())){
			// 维护初期余额
			double beginbalance = 0;
			if (debit > 0) {
				beginbalance = Double.parseDouble(balance.getCredittotalamount()) + Double.parseDouble(balance.getEndbalance()) - Double.parseDouble(balance.getDebittotalamount());
			} else if (credit > 0) {
				beginbalance = -(Double.parseDouble(balance.getDebittotalamount()) + (-Double.parseDouble(balance.getEndbalance())) - Double.parseDouble(balance.getCredittotalamount()));
			}
			balance.setBeginbalance(beginbalance + "");
		}
		balance.setDebittotalamount((Double.parseDouble(balance.getDebittotalamount()) + debit) + "");
		balance.setYtddebittotalamount((Double.parseDouble(balance.getYtddebittotalamount()) + debit) + "");
		balance.setCredittotalamount((Double.parseDouble(balance.getCredittotalamount()) + credit) + "");
		balance.setYtdcredittotalamount((Double.parseDouble(balance.getYtdcredittotalamount()) + credit) + "");
		//add by : zt
		if (companySystem == 1) {	//小企业
			if (!expInfo.equals("结转本期损益")) {
				// 损益类科目 维护本期和本年的损益发生额
				if (null != account && StringUtils.isNotBlank(account.getAccuntId()) && account.getAccuntId().startsWith("5")) {
					balance.setAmountfor((Double.parseDouble(balance.getAmountfor()) + (debit - credit)) + "");
					balance.setYtdamountfor((Double.parseDouble(balance.getYtdamountfor()) + (debit - credit)) + "");
				}
			}
			if (!expInfo.equals("结转以前年度损益") && null != account && StringUtils.isNotBlank(account.getAccuntId()) && account.getAccuntId().startsWith("6")) {
				balance.setAmountfor((Double.parseDouble(balance.getAmountfor()) + (debit - credit)) + "");
				balance.setYtdamountfor((Double.parseDouble(balance.getYtdamountfor()) + (debit - credit)) + "");
			}
		} else if(companySystem == 2) {	//新规则
			if ((!expInfo.equals("结转以前年度损益") || !expInfo.equals("结转本期损益")) && null != account && StringUtils.isNotBlank(account.getAccuntId()) && account.getAccuntId().startsWith("6")) {
				balance.setAmountfor((Double.parseDouble(balance.getAmountfor()) + (debit - credit)) + "");
				balance.setYtdamountfor((Double.parseDouble(balance.getYtdamountfor()) + (debit - credit)) + "");
			}
		}
		
		
		balance.setEndbalance((Double.parseDouble(balance.getDebittotalamount()) + Double.parseDouble(balance.getBeginbalance()) - Double.parseDouble(balance.getCredittotalamount()) + ""));
		
		return balance;
	}
	
	/**
	 * 判断下几期是否都有
	 * @param fdbid
	 * @param accountPeriod
	 * @param accId
	 * @return
	 */
	public int lastHas(String fdbid,String accountPeriod,String accId){
		return dao.lastHas(fdbid, accountPeriod, accId);
	}
	
	
	/**
	 * 判断当期是否有
	 * @param fdbid
	 * @param accountPeriod
	 * @param accId
	 * @return
	 */
	public int currentHas(String fdbid,String accountPeriod,String accId){
		return dao.currentHas(fdbid, accountPeriod, accId);
	}
	/**
	 * 查询最近账期（查询凭证用和上面的查询最近账期不一样）
	 * */
	public	String selectPeriod(String fdbid,String accountPeriod){
		return dao.selectPeriod(fdbid, accountPeriod);
	}
	/**
	 * 查询所有的账期（查询凭证用和上面的查询所有账期不一样）
	 * */
	public List<String> getAllAccountperiod(String fdbid){
		return dao.getAllAccountperiod(fdbid);
	}
}