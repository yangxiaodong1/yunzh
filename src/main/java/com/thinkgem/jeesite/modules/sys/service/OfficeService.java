/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}
	//获得自动增长id
	public String getautoid(){
		return dao.getautoid();
	}
	public String getautoidfile(){
		return dao.getautoidfile();
	}
	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	@Transactional(readOnly = false)
	public void insertCompany(Office office){
		if (office.getIsNewRecord()){
			office.preInsert();
			dao.insertCompany(office);
			UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		}else{
			office.preUpdate();
			dao.update(office);
		}
		
		
	}
	public String getbyOfficeId(String id){
		return dao.getbyOfficeId(id);
	}
	//yang
	@Transactional(readOnly = false)
	public void insertChargeCompany(Office office){
		if (office.getIsNewRecord()){
			office.preInsert();
			dao.insertChargeCompany(office);
			UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		}else{
			office.preUpdate();
			dao.updatenew(office);
		}
		
		
	}
	//yang整合数据库更新
	@Transactional(readOnly = false)
	public void updatenew(Office office){
		office.preUpdate();
		dao.updatenew(office);
	}
	
	@Transactional(readOnly = true)
	public List<Office> findByParentId(Office office){
		return dao.findByParentIdsLike(office);
	}
	public List<Office> findByParentIds(Office office){
		return dao.findByParentIds(office);
	}
	public List<Office> findByParentIdszNodes(Office office){
		return dao.findByParentIdszNodes(office);
	}
	
	
	public Page<Office> findByParentIds(Page<Office> page, Office office) {
		// 设置分页参数
		office.setPage(page);
		// 执行分页查询
		page.setList(dao.findByParentIds(office));
		return page;
	}
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	@Transactional(readOnly = false)
	public void deletes(Office office) {
		dao.deletes(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	
}
