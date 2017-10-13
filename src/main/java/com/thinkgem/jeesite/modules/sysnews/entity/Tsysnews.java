/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sysnews.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 系统消息表Entity
 * @author yang
 * @version 2016-01-14
 */
public class Tsysnews extends DataEntity<Tsysnews> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 消息标题
	private String content;		// 消息内容
	//private String sendtime;		// 发送时间
	private Date sendtime;              //发送时间，日期类型
	private String sendstatus;		// 消息的发送状态,
	private String settimestatus;		// 是否定时发送状态,0代表定时1代表不定时
	private String createString;
	private String sendtimeString;//发送时间日期类型
	private String updateByStrinig;
	private String userId=UserUtils.getPrincipal().getId();//登陆人id
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUpdateByStrinig() {
		return updateByStrinig;
	}

	public void setUpdateByStrinig(String updateByStrinig) {
		this.updateByStrinig = updateByStrinig;
	}

	public String getSendtimeString() {
		return sendtimeString;
	}

	public void setSendtimeString(String sendtimeString) {
		this.sendtimeString = sendtimeString;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	
	public String getCreateString() {
		return createString;
	}

	public void setCreateString(String createString) {
		this.createString = createString;
	}

	public Tsysnews() {
		super();
	}

	public Tsysnews(String id){
		super(id);
	}

	@Length(min=0, max=64, message="消息标题长度必须介于 0 和 64 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
//	public String getSendtime() {
//		return sendtime;
//	}
//
//	public void setSendtime(String sendtime) {
//		this.sendtime = sendtime;
//	}
	
	@Length(min=0, max=1, message="消息的发送状态,长度必须介于 0 和 1 之间")
	public String getSendstatus() {
		return sendstatus;
	}

	public void setSendstatus(String sendstatus) {
		this.sendstatus = sendstatus;
	}
	
	@Length(min=0, max=1, message="是否定时发送状态,0代表定时1代表不定时长度必须介于 0 和 1 之间")
	public String getSettimestatus() {
		return settimestatus;
	}

	public void setSettimestatus(String settimestatus) {
		this.settimestatus = settimestatus;
	}
	
}