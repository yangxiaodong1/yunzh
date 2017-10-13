/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.newcharge.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.newcharge.dao.TChargecompanyDao;
import com.thinkgem.jeesite.modules.newcharge.entity.TChargecompany;
import com.thinkgem.jeesite.modules.newcharge.entity.TCountCompany;
import com.thinkgem.jeesite.modules.newcharge.entity.TCountCompanynew;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 记账公司Service
 * @author yang
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class TChargecompanyService extends CrudService<TChargecompanyDao, TChargecompany> {

	public TChargecompany get(String id) {
		return super.get(id);
	}
	public List<TChargecompany> findList(TChargecompany tChargecompany) {
		return super.findList(tChargecompany);
	}
	//通过状态选择选择出来的是office对象
	public List<Office> selectByStatus(Office office) {
		return dao.selectByStatus(office);
	}
	public List<Office> selectByStatus2(Office office) {
		return dao.selectByStatus2(office);
	}
	//yang数据库整合后的，通过id获得office
	public Office getbyid(String id){
		return dao.getbyid(id);
	}
	//通过状态选择
		public List<Office> selectByStatusN(Office office) {
			return dao.selectByStatusN(office);
		}
		//通过状态选择新建
				public List<Office> selectByStatusN2(Office office) {
					return dao.selectByStatusN(office);
				}
		
		//通过试用状态选择
	public List<Office> selByStatus(Office office) {
		return dao.selByStatus(office);
	}
	//通过试用状态选择新修改
		public List<Office> selByStatus2(Office office) {
			return dao.selByStatus2(office);
		}
	//通过试用城市选择
	public List<Office> allCity(Office office) {
		return dao.allCity(office);
	}
	//根据时间查询
	public List<TChargecompany> findByTime(TChargecompany tChargecompany) {
		return dao.findByTime(tChargecompany);
	}
	
	public Page<TChargecompany> findPage(Page<TChargecompany> page, TChargecompany tChargecompany) {
		return super.findPage(page, tChargecompany);
	}
	
	@Transactional(readOnly = false)
	public void save(TChargecompany tChargecompany) {
		super.save(tChargecompany);
	}
	
	@Transactional(readOnly = false)
	public void delete(TChargecompany tChargecompany) {
		super.delete(tChargecompany);
	}
	@Transactional(readOnly=true)
	public List<TCountCompanynew> dataGeneralSituationNew(HttpServletRequest request){
		String type = request.getParameter("type");
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		Map<String,Object> map = new HashMap<String, Object>();
		String begindate = "";
		String enddate = "";
		if(type != null && !type.equals("")){
			Date curDate = new Date();
			enddate = DateUtils.formatDateTime(curDate);
			Calendar calendar = new GregorianCalendar(); 
			calendar.setTime(curDate); 
			if(type.equals("7")){
			    calendar.add(calendar.DATE,-7);//把日期往后增加一天.整数往后推,负数往前移动 
			    begindate = DateUtils.formatDateTime(calendar.getTime());
				map.put("begindate", begindate);
				map.put("enddate", enddate);
			}else if(type.equals("30")){
				calendar.add(calendar.DATE,-30);//把日期往后增加一天.整数往后推,负数往前移动 
				begindate = DateUtils.formatDateTime(calendar.getTime());
				map.put("begindate", begindate);
				map.put("enddate", enddate);
			}
		}
		if(begin != null && !"".equals(begin) && end != null && !"".equals(end)){
			begindate = begin +" 00:00:00";
			enddate =end+" 23:59:59";
			map.put("begindate", begindate);
			map.put("enddate", enddate);
		}else if((begin == null || "".equals(begin)) && end != null && !"".equals(end)){
			enddate = end+" 23:59:59";;
			map.put("begindate", "1990-01-01 00:00:00");
			map.put("enddate", enddate);
		}else if((end == null || "".equals(end)) && begin != null && !"".equals(begin)){
			begindate = begin+" 00:00:00";
			enddate = DateUtils.formatDateTime(new Date());
			map.put("begindate", begindate);
			map.put("enddate", enddate);
		}
		return dao.dataGeneralSituationNew(map);
	}
	@Transactional(readOnly=true)
	public List<TCountCompany> dataGeneralSituation(HttpServletRequest request){
		String type = request.getParameter("type");
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		Map<String,Object> map = new HashMap<String, Object>();
		String begindate = "";
		String enddate = "";
		if(type != null && !type.equals("")){
			Date curDate = new Date();
			enddate = DateUtils.formatDateTime(curDate);
			Calendar calendar = new GregorianCalendar(); 
			calendar.setTime(curDate); 
			if(type.equals("7")){
			    calendar.add(calendar.DATE,-7);//把日期往后增加一天.整数往后推,负数往前移动 
			    begindate = DateUtils.formatDateTime(calendar.getTime());
				map.put("begindate", begindate);
				map.put("enddate", enddate);
			}else if(type.equals("30")){
				calendar.add(calendar.DATE,-30);//把日期往后增加一天.整数往后推,负数往前移动 
				begindate = DateUtils.formatDateTime(calendar.getTime());
				map.put("begindate", begindate);
				map.put("enddate", enddate);
			}
		}
		if(begin != null && !"".equals(begin) && end != null && !"".equals(end)){
			begindate = begin +" 00:00:00";
			enddate =end+" 23:59:59";
			map.put("begindate", begindate);
			map.put("enddate", enddate);
		}else if((begin == null || "".equals(begin)) && end != null && !"".equals(end)){
			enddate = end+" 23:59:59";;
			map.put("begindate", "1990-01-01 00:00:00");
			map.put("enddate", enddate);
		}else if((end == null || "".equals(end)) && begin != null && !"".equals(begin)){
			begindate = begin+" 00:00:00";
			enddate = DateUtils.formatDateTime(new Date());
			map.put("begindate", begindate);
			map.put("enddate", enddate);
		}
		List<Map<String,Object>> result = dao.dataGeneralSituation(map);
		List<TCountCompany> list = new ArrayList();
		for (Map<String, Object> obj : result) {
			TCountCompany c = new TCountCompany();
			for (Map.Entry<String, Object> entry : obj.entrySet()) {
				String val = entry.getValue().toString();
				String key = entry.getKey();
				if(key.equals("create_date")){
					c.setDate(val);
				}else if(key.equals("company")){
					c.setCompanyNumber(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("customer")){
					c.setCustomer(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("users")){
					c.setCompanyUsers(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}
			}
			list.add(c);
	    }
		return list;
	}
	@Transactional(readOnly=true)
	public List<TCountCompany> cityGeneralSituation(String city){
		List<Map<String,Object>> result = dao.cityGeneralSituation(city);
		List<TCountCompany> list = new ArrayList();
		for (Map<String, Object> obj : result) {
			TCountCompany c = new TCountCompany();
			for (Map.Entry<String, Object> entry : obj.entrySet()) {
				String val = entry.getValue().toString();
				String key = entry.getKey();
				if(key.equals("city")){
					c.setCity(val);
				}else if(key.equals("company")){
					c.setCompanyNumber(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("customer")){
					c.setCustomer(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("users")){
					c.setCompanyUsers(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}
			}
			list.add(c);
		}
		return list;
	}
	
	
	@Transactional(readOnly=true)//首页城市数据统计新记账公司yang
	public List<TCountCompany> indexDatanew(String begin,String end){
		
		Map<String,Object> map = new HashMap<String, Object>();
		String begindate = "";
		String enddate = "";
		
		if(begin != null && !"".equals(begin) && end != null && !"".equals(end)){
			begindate = begin +" 00:00:00";
			enddate =end+" 23:59:59";
			map.put("begindate", begindate);
			map.put("enddate", enddate);
		}else if((begin == null || "".equals(begin)) && end != null && !"".equals(end)){
			enddate = end+" 23:59:59";;
			map.put("begindate", "1990-01-01 00:00:00");
			map.put("enddate", enddate);
		}else if((end == null || "".equals(end)) && begin != null && !"".equals(begin)){
			begindate = begin+" 00:00:00";
			enddate = DateUtils.formatDateTime(new Date());
			map.put("begindate", begindate);
			map.put("enddate", enddate);
		}
		List<Map<String,Object>> result = dao.indexDatanew(map);
		List<TCountCompany> list = new ArrayList();
		for (Map<String, Object> obj : result) {
			TCountCompany c = new TCountCompany();
			for (Map.Entry<String, Object> entry : obj.entrySet()) {
				String val = entry.getValue().toString();
				String key = entry.getKey();
				if(key.equals("create_date")){
					c.setDate(val);
				}else if(key.equals("company")){
					c.setCompanyNumber(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("customer")){
					c.setCustomer(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("users")){
					c.setCompanyUsers(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}
			}
			list.add(c);
	    }
		return list;
	}
	
	@Transactional(readOnly=true)//首页城市数据统计新会计yang
	public List<TCountCompany> indexDatanewaccount(String begin,String end){
		
		Map<String,Object> map = new HashMap<String, Object>();
		String begindate = "";
		String enddate = "";
		
		if(begin != null && !"".equals(begin) && end != null && !"".equals(end)){
			begindate = begin +" 00:00:00";
			enddate =end+" 23:59:59";
			map.put("begindate", begindate);
			map.put("enddate", enddate);
		}else if((begin == null || "".equals(begin)) && end != null && !"".equals(end)){
			enddate = end+" 23:59:59";;
			map.put("begindate", "1990-01-01 00:00:00");
			map.put("enddate", enddate);
		}else if((end == null || "".equals(end)) && begin != null && !"".equals(begin)){
			begindate = begin+" 00:00:00";
			enddate = DateUtils.formatDateTime(new Date());
			map.put("begindate", begindate);
			map.put("enddate", enddate);
		}
		List<Map<String,Object>> result = dao.indexDatanewaccount(map);
		List<TCountCompany> list = new ArrayList();
		for (Map<String, Object> obj : result) {
			TCountCompany c = new TCountCompany();
			for (Map.Entry<String, Object> entry : obj.entrySet()) {
				String val = entry.getValue().toString();
				String key = entry.getKey();
				if(key.equals("create_date")){
					c.setDate(val);
				}else if(key.equals("company")){
					c.setCompanyNumber(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("customer")){
					c.setCustomer(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("users")){
					c.setCompanyUsers(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}
			}
			list.add(c);
	    }
		return list;
	}
	
	@Transactional(readOnly=true)//首页城市数据统计新yang统计新增客户
	public List<TCountCompany> indexDatanewcustomer(String begin,String end){
		
		Map<String,Object> map = new HashMap<String, Object>();
		String begindate = "";
		String enddate = "";
		
		if(begin != null && !"".equals(begin) && end != null && !"".equals(end)){
			begindate = begin +" 00:00:00";
			enddate =end+" 23:59:59";
			map.put("begindate", begindate);
			map.put("enddate", enddate);
		}else if((begin == null || "".equals(begin)) && end != null && !"".equals(end)){
			enddate = end+" 23:59:59";;
			map.put("begindate", "1990-01-01 00:00:00");
			map.put("enddate", enddate);
		}else if((end == null || "".equals(end)) && begin != null && !"".equals(begin)){
			begindate = begin+" 00:00:00";
			enddate = DateUtils.formatDateTime(new Date());
			map.put("begindate", begindate);
			map.put("enddate", enddate);
		}
		List<Map<String,Object>> result = dao.indexDatanewcustomer(map);
		List<TCountCompany> list = new ArrayList();
		for (Map<String, Object> obj : result) {
			TCountCompany c = new TCountCompany();
			for (Map.Entry<String, Object> entry : obj.entrySet()) {
				String val = entry.getValue().toString();
				String key = entry.getKey();
				if(key.equals("create_date")){
					c.setDate(val);
				}else if(key.equals("company")){
					c.setCompanyNumber(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("customer")){
					c.setCustomer(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("users")){
					c.setCompanyUsers(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}
			}
			list.add(c);
	    }
		return list;
	}
	
	
	
	@Transactional(readOnly=true)//记账公司城市分布yang
	public List<TCountCompany> companySituation(String city){
		List<Map<String,Object>> result = dao.companySituation(city);
		List<TCountCompany> list = new ArrayList();
		for (Map<String, Object> obj : result) {
			TCountCompany c = new TCountCompany();
			for (Map.Entry<String, Object> entry : obj.entrySet()) {
				String val = entry.getValue().toString();
				String key = entry.getKey();
				if(key.equals("city")){
					c.setCity(val);
				}else if(key.equals("company")){
					c.setCompanyNumber(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("customer")){
					c.setCustomer(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("users")){
					c.setCompanyUsers(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}
			}
			list.add(c);
		}
		return list;
	}
	
	@Transactional(readOnly=true)//服务企业城市分布
	public List<TCountCompany> customerSituation(String city){
		List<Map<String,Object>> result = dao.customerSituation(city);
		List<TCountCompany> list = new ArrayList();
		for (Map<String, Object> obj : result) {
			TCountCompany c = new TCountCompany();
			for (Map.Entry<String, Object> entry : obj.entrySet()) {
				String val = entry.getValue().toString();
				String key = entry.getKey();
				if(key.equals("city")){
					c.setCity(val);
				}else if(key.equals("company")){
					c.setCompanyNumber(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("customer")){
					c.setCustomer(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("users")){
					c.setCompanyUsers(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}
			}
			list.add(c);
		}
		return list;
	}
	@Transactional(readOnly=true)//
	public List<TCountCompany> userSituation(String city){
		List<Map<String,Object>> result = dao.userSituation(city);
		List<TCountCompany> list = new ArrayList();
		for (Map<String, Object> obj : result) {
			TCountCompany c = new TCountCompany();
			for (Map.Entry<String, Object> entry : obj.entrySet()) {
				String val = entry.getValue().toString();
				String key = entry.getKey();
				if(key.equals("city")){
					c.setCity(val);
				}else if(key.equals("company")){
					c.setCompanyNumber(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("customer")){
					c.setCustomer(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}else if(key.equals("users")){
					c.setCompanyUsers(val.substring(0, val.indexOf(".")>-1?val.indexOf("."):val.length()));
				}
			}
			list.add(c);
		}
		return list;
	}
	
	
	@Transactional(readOnly=true)//
	public String conutcharge(){
		return dao.conutcharge();
	}
	@Transactional(readOnly=true)//
	public String conutaccount(){
		return dao.conutaccount();
	}
	@Transactional(readOnly=true)//
	public String conutcustomer(){
		return dao.conutcustomer();
	}
}