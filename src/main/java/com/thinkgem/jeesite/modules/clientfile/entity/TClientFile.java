/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clientfile.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 添加图片Entity
 * @author yang
 * @version 2016-03-12
 */
public class TClientFile extends DataEntity<TClientFile> {
	
	private static final long serialVersionUID = 1L;
	private String oldName;		// 文件原名
	private String fileName;		// 上传文件路径
	
	public TClientFile() {
		super();
	}

	public TClientFile(String id){
		super(id);
	}

	@Length(min=0, max=100, message="文件原名长度必须介于 0 和 100 之间")
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