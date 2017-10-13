/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.workterrace.entity.WagesVo;

/**
 * 用户DAO接口
 * @author cy
 * @version 2015-11-23
 */
@MyBatisDao
public interface TCustomerDao extends CrudDao<TCustomer> {
	/**
	 * 获取所有的客户信息，cy
	 * */
	public List<TCustomer> AllList();
	/**
	 * 根据用户获取手里的客户信息，cy
	 * */
	public List<TCustomer> listBySupervisor(TCustomer tCustomer);
	/**
	 * 根据用户客户信息，yxd
	 * */
	public List<TCustomer> findBySupervisor(TCustomer tCustomer);
	
	/**
	 * 根据用户获取手里的客户信息，
	 * */
	public List<TCustomer> listTCustomerBySupervisor(TCustomer tCustomer);
	public List<TCustomer> listTCustomerBySupervisors(TCustomer tCustomer);

	/**
	 * 根据用户获取手里的客户信息工资，
	 * */
	public List<TCustomer> listWagesBySupervisor(TCustomer tCustomer);
	/**
	 * 根据用户获取手里的客户信息报税，
	 * */
	public List<TCustomer> listTaxBySupervisor(TCustomer tCustomer);
	
	
	/**
	 * 根据用户获取手里的客户信息工资，
	 * */
	public List<TCustomer> listDailyBySupervisor(TCustomer tCustomer);
	/**
	 * 根据首页统计数量，
	 * */
	public WagesVo homePageCount(TCustomer tCustomer);
	
	public int countTCustomerBySupervisor(TCustomer tCustomer);
	
	
	/**
	 * 根据企业id修改企业的审核人 ，cy
	 * **/
	public void updateSupervisor(TCustomer tCustomer);
	/**
	 * 还原
	 * **/
	public void restore(String id);
	public void deleteCustomer(String id);
	
	
	public List<TCustomer> listDelectBySupervisor(TCustomer tCustomer);
	public List<TCustomer> ChargeToAccountBySupervisor(TCustomer tCustomer);
	
	
	/**
	 * 当当前账期的值为空时将起始账期的值赋给当前账期
	 * @param tCustomer
	 * @return
	 */
	public void  updatecurrentPeriod(TCustomer tCustomer);
	public List<TCustomer> getName( TCustomer tCustomer);	
	/**
	 * chf
	 * **/  
	public TCustomer getId(TCustomer tCustomer);

	/**
	 * 添加客户信息
	 * **/
	public void insertcustomer(TCustomer tCustomer);
	
	/**
	 * 修改结束账期
	 * **/
	public void updateServiceexpirationdate(TCustomer tCustomer);

	
	/**
	 * 获取标准的id值（模拟sequence序列）
	 * **/
	public String selectId();
	/**
	 * 获取记账统计个数
	 * **/
	public TCustomer customercount(TCustomer tCustomer);
	
	/**
	 * 获取工资统计个数
	 * **/
	public TCustomer wagesCount(TCustomer tCustomer);
	/**
	 * 获取日常统计个数
	 * **/
	public WagesVo dailyCount(TCustomer tCustomer);
	/**
	 * 获取报税统计个数
	 * **/
	public TCustomer taxcount(TCustomer tCustomer);
	
	
	/**
	 * 根据ID，获取该用户的信息
	 * **/
	public TCustomer get(String id);
	
	public int urgentupdates0(List<String> ids);
	public int urgentupdates1(List<String> ids);
	public int outOfServiceupdates1(List<String> ids);
	public int outOfServiceupdate0(String ids);
	
	
	
}