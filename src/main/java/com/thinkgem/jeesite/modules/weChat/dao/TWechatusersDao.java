/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weChat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.weChat.entity.TWechatusers;

/**
 * 微信用户操作DAO接口
 * @author zhangtong
 * @version 2016-01-26
 */
@MyBatisDao
public interface TWechatusersDao extends CrudDao<TWechatusers> {
	/**
	 * 根据用户名称查询用户密码
	 * @param userName
	 * @param password
	 * @return
	 */
	public String selectkWeChatUserPassword(@Param("userName")String userName);
	/**
	 * 查询微信用户信息
	 * @param userName
	 * @param password
	 * @return
	 */
	public TWechatusers selectWeChatUserInfo(@Param("userName")String userName,@Param("password")String password);
	/**
	 * 保存用户
	 * @param tWechatusers
	 */
	public void saveUserInfo(TWechatusers tWechatusers);
	/**
	 * 根据微信用户的fdbid来查用户名密码等
	 * @param userName
	 * @param password
	 * @return
	 */
	public TWechatusers selectByFdbid(@Param("fdbid")String fdbid);
	/**
	 * 根据fdbid修改微信用户的密码
	 * @param tWechatusers
	 */
	public void updatepwd(TWechatusers tWechatusers);

	/**
	 * 根据账户模糊查询 by：zt
	 * @param username
	 * @return
	 */
	public int selectLikeUserName(@Param("userName")String userName);

	/**
	 * 根据fdbid修改微信用户的用户名
	 * @param tWechatusers
	 */
	public void updateuesrname(TWechatusers tWechatusers);

}