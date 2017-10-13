/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weChat.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.weChat.dao.TWechatusersDao;
import com.thinkgem.jeesite.modules.weChat.entity.TWechatusers;

/**
 * 微信用户操作Service
 * @author zhangtong
 * @version 2016-01-26
 */
@Service
@Transactional(readOnly = true)
public class TWechatusersService extends CrudService<TWechatusersDao, TWechatusers> {

	public TWechatusers get(String id) {
		return super.get(id);
	}
	
	public List<TWechatusers> findList(TWechatusers tWechatusers) {
		return super.findList(tWechatusers);
	}
	
	public Page<TWechatusers> findPage(Page<TWechatusers> page, TWechatusers tWechatusers) {
		return super.findPage(page, tWechatusers);
	}
	
	@Transactional(readOnly = false)
	public void save(TWechatusers tWechatusers) {
		super.save(tWechatusers);
	}
	
	@Transactional(readOnly = false)
	public void delete(TWechatusers tWechatusers) {
		super.delete(tWechatusers);
	}
	/**
	 * 判断用户是否存在
	 * @param userName
	 * @param password
	 * @return
	 */
	public String selectkWeChatUserPassword(String userName){
		return dao.selectkWeChatUserPassword(userName);
		
	}
	/**
	 * 查询微信用户信息
	 * @param userName
	 * @param password
	 * @return
	 */
	public TWechatusers selectWeChatUserInfo(String userName,String password){
		return dao.selectWeChatUserInfo(userName,password);
	}
	/**
	 * 保存用户
	 * @param tWechatusers
	 */
	@Transactional(readOnly = false)
	public void saveUserInfo(TWechatusers tWechatusers) {
		dao.saveUserInfo(tWechatusers);
	}
	@Transactional(readOnly = false)
	public TWechatusers selectByFdbid(String fdbid){
		return dao.selectByFdbid(fdbid);
	}
	@Transactional(readOnly = false)//根据fdbid来重置密码
	public void updatepwd(TWechatusers tWechatusers) {
		dao.updatepwd(tWechatusers);
	}

	/**
	 * 根据账户模糊查询 by：zt
	 * @param username
	 * @return
	 */
	public int selectLikeUserName(String username){
		return dao.selectLikeUserName(username);
	}

	@Transactional(readOnly = false)//根据fdbid来重置密码
	public void updateuesrname(TWechatusers tWechatusers) {
		dao.updateuesrname(tWechatusers);
	}
}