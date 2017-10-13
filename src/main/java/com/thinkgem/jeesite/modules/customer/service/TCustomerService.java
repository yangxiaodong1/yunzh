/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.dao.TCustomerDao;
import com.thinkgem.jeesite.modules.workterrace.entity.WagesVo;

/**
 * 客户Service
 * @author cy
 * @version 2015-11-23
 */
@Service
@Transactional(readOnly = true)
public class TCustomerService extends CrudService<TCustomerDao, TCustomer> {
	//依赖注入
	@Autowired
	private TCustomerDao tCustomerDao;
	
	public TCustomer get(String id) {
		return super.get(id);
	}
	
	public List<TCustomer> findList(TCustomer tCustomer) {
		return super.findList(tCustomer);
	}
	
	public Page<TCustomer> findPage(Page<TCustomer> page, TCustomer tCustomer) {
		return super.findPage(page, tCustomer);
	}
	public Page<TCustomer> listWagesBySupervisor(Page<TCustomer> page, TCustomer tCustomer) {
		tCustomer.setPage(page);
		page.setList(dao.listWagesBySupervisor(tCustomer));
		return page;
	}
	public Page<TCustomer> listDailyBySupervisor(Page<TCustomer> page, TCustomer tCustomer) {
		tCustomer.setPage(page);
		page.setList(dao.listDailyBySupervisor(tCustomer));
		return page;
	}
	public List<TCustomer> listDailyBySupervisor(TCustomer tCustomer) {
		return dao.listDailyBySupervisor(tCustomer);
		
	}
	
	public List<TCustomer> ChargeToAccountBySupervisor(TCustomer tCustomer) {
		return dao.ChargeToAccountBySupervisor(tCustomer);
		
	}
	
	
	
	public int countTCustomerBySupervisor(TCustomer tCustomer){
		return dao.countTCustomerBySupervisor(tCustomer);
	}
	
	public Page<TCustomer> listTaxBySupervisor(Page<TCustomer> page,TCustomer tCustomer){
		tCustomer.setPage(page);
		page.setList(dao.listTaxBySupervisor(tCustomer));
		return page;
	}
	public List<TCustomer> listTaxBySupervisor(TCustomer tCustomer){
		return dao.listTaxBySupervisor(tCustomer);
	}
	public Page<TCustomer> listDelectBySupervisor(Page<TCustomer> page,TCustomer tCustomer){
		tCustomer.setPage(page);
		page.setList(dao.listDelectBySupervisor(tCustomer));
		return page;
	}
	
	
	
	public List<TCustomer> listWagesBySupervisor(TCustomer tCustomer) {
		return dao.listWagesBySupervisor(tCustomer);
	}
	
	@Transactional(readOnly = false)
	public void save(TCustomer tCustomer) {
		super.save(tCustomer);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCustomer tCustomer) {
		super.delete(tCustomer);
	}
	
	/**
	 * 还原
	 * **/
	@Transactional(readOnly = false)
	public void restore(String id){
		dao.restore(id);
	}
	@Transactional(readOnly = false)
	public void deleteCustomer(String id){
		dao.deleteCustomer(id);
	}
	
	/**
	 * 根据企业id修改企业的审核人 ，cy
	 * **/
	public List<TCustomer> findAllList(TCustomer tCustomer){
		return tCustomerDao.findAllList(tCustomer);
	}/**
	 * 获取所有的客户信息，cy
	 * **/
	public List<TCustomer> AllCustomer(){
		//return tCustomerDao.AllList();
		return dao.AllList();
	}
	/**
	 * 根据企业的 ，cy
	 * **/
	public List<TCustomer> ListBySupervisor(String supervisorId){
		TCustomer tCustomer1=new TCustomer();
		tCustomer1.setSupervisor(supervisorId);
		return dao.listBySupervisor(tCustomer1);
	}
	
	/**
	 * 根据企业的 ，cy
	 * **/
	public List<TCustomer> findBySupervisor(String supervisorId){
		TCustomer tCustomer1=new TCustomer();
		tCustomer1.setSupervisor(supervisorId);
		return dao.findBySupervisor(tCustomer1);
	}
	
	/**
	 * 根据企业的 ，TCustomer
	 * **/
	public List<TCustomer> listTCustomerBySupervisor(TCustomer tCustomer){
		return dao.listTCustomerBySupervisor(tCustomer);
	}
	public List<TCustomer> listTCustomerBySupervisors(TCustomer tCustomer){
		return dao.listTCustomerBySupervisors(tCustomer);
	}
	public Page<TCustomer> listTCustomerBySupervisor(Page<TCustomer> page, TCustomer tCustomer) {
		//page.setPageSize(3);
		tCustomer.setPage(page);
		return page.setList(dao.listTCustomerBySupervisor(tCustomer));
		
	}
	public Page<TCustomer> listTCustomerBySupervisors(Page<TCustomer> page, TCustomer tCustomer) {
		//page.setPageSize(3);
		tCustomer.setPage(page);
		return page.setList(dao.listTCustomerBySupervisors(tCustomer));
		
	}
	/**
	 * 获取记账统计个数
	 * **/
	public TCustomer customercount(TCustomer tCustomer){
		return dao.customercount(tCustomer);
	}
	/**
	 * 获取工资统计个数
	 * **/
	public TCustomer wagesCount(TCustomer tCustomer){
		return dao.wagesCount(tCustomer);
	}
	/**
	 * 获取报税i统计个数
	 * **/
	public TCustomer taxcount(TCustomer tCustomer){
		return dao.taxcount(tCustomer);
	}
	
	/**
	 * 获取工资统计个数
	 * **/
	public WagesVo dailyCount(TCustomer tCustomer){
		return dao.dailyCount(tCustomer);
	}
	/**
	 * 根据首页统计数量，
	 * */
	public WagesVo homePageCount(TCustomer tCustomer){
		return dao.homePageCount(tCustomer);
	}
	/**
	 * 根据企业id修改企业的审核人 ，cy
	 * **/
	@Transactional(readOnly = false)
	public void updateSupervisor(TCustomer tCustomer){
		dao.updateSupervisor(tCustomer);
	}
	/**
	 * 当当前账期的值为空时将起始账期的值赋给当前账期
	 * @param tCustomer
	 */
	@Transactional(readOnly = false)
	public void  updatecurrentPeriod(TCustomer tCustomer){
		dao.updatecurrentPeriod(tCustomer);
	}
	
 	public List<TCustomer> getName(TCustomer tCustomer){
		return tCustomerDao.getName(tCustomer);
	}
 	
	public TCustomer getId(TCustomer tCustomer){
		return tCustomerDao.getId(tCustomer);
	}
	
	/**
	 * 客户信息添加
	 * **/
	@Transactional(readOnly = false)
	public void insertcustomer(TCustomer tCustomer){
		dao.insertcustomer(tCustomer);
	}
	/**
	 * 获取标准的id值（模拟sequence序列），cy
	 * **/
	@Transactional(readOnly = false)
	public String selectId(){
		return dao.selectId();
	}
	/**
	 * 修改客户结束账期
	 * **/
	@Transactional(readOnly = false)
	public void updateServiceexpirationdate(TCustomer tCustomer){
		dao.updateServiceexpirationdate(tCustomer);
	}
	@Transactional(readOnly = false)
	public int urgentupdates(List<String> ids,String u){
		if("0".equals(u))
			return dao.urgentupdates0(ids);
		else 
			return dao.urgentupdates1(ids);
	}
	
	@Transactional(readOnly = false)
	public int outOfServiceupdates1(List<String> ids){
		return dao.outOfServiceupdates1(ids);
	}
	@Transactional(readOnly = false)
	public int outOfServiceupdate0(String ids){
		return dao.outOfServiceupdate0(ids);
	};
	
	
}