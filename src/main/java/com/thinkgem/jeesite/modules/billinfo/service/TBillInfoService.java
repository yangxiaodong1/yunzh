/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.billinfo.service;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.billinfo.dao.TBillInfoDao;
import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfo;

/**
 * 发票信息Service
 * @author 发票信息
 * @version 2015-10-21
 */
@Service
@Transactional(readOnly = true)
public class TBillInfoService extends CrudService<TBillInfoDao, TBillInfo> {
	
	@Autowired
	private TBillInfoDao dao;

	public TBillInfo get(String id) {
		return super.get(id);
	}
	
	public List<TBillInfo> findList(TBillInfo tBillInfo) {
		return super.findList(tBillInfo);
	}
	
	public Page<TBillInfo> findPage(Page<TBillInfo> page, TBillInfo tBillInfo) {
		return super.findPage(page, tBillInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(TBillInfo tBillInfo) {
		super.save(tBillInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(TBillInfo tBillInfo) {
		super.delete(tBillInfo);
	}
	
	public List<TBillInfo> findListWithOrderBy(TBillInfo tBillInfo,String orderBy) {
		return dao.findListWithOrderBy(tBillInfo,orderBy);
	}
	
	@Transactional(readOnly = false)
	public int deleteByid(String id){
		return dao.delete(id);
	}
	
	@Transactional(readOnly = false)
	public int update(TBillInfo tBillInfo){
		return dao.update(tBillInfo);
	}
	
	public int countBillInfo(TBillInfo billInfo,String billInfoStatus){
		return dao.countBillInfo(billInfo,billInfoStatus);
	}
	
	
	public List<TBillInfo> queryUploadBillInfo(TBillInfo tBillInfo) {
		return dao.queryUploadBillInfo(tBillInfo);
	}
	/**
	 * 
	 * @param tBillInfo
	 * @param defaultFlag  0  所有  1   默认票据  除出租车 飞机 火车类的票据  
	 * @param cancelFlag true 查询错误票据
	 * @return
	 */
	public List<TBillInfo> queryDealBillInfo(TBillInfo tBillInfo,int defaultFlag,boolean cancelFlag) {
		return dao.queryDealBillInfo(tBillInfo,defaultFlag,cancelFlag);
	}
	
	@Transactional(readOnly = false)
	public int updateBill(String id , String field , String value){
		return dao.updateBill(id, field, value);
	}
}