/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 意见反馈附件表Entity
 * @author rongjd
 * @version 2016-01-20
 */
public class TFeedbackFile extends DataEntity<TFeedbackFile> {
	
	private static final long serialVersionUID = 1L;
	private String oldName;		// 意见反馈id
	private String fileName;		// 上传文件路径
	
	public TFeedbackFile() {
		super();
	}

	public TFeedbackFile(String id){
		super(id);
	}

	@Length(min=0, max=100, message="意见反馈id长度必须介于 0 和 100 之间")
	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	
	@Length(min=0, max=100, message="上传文件路径长度必须介于 0 和 100 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}