/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.backupandrecover.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 备份与回复Entity
 * @author 备份与回复
 * @version 2016-02-03
 */
public class TbackupRecover extends DataEntity<TbackupRecover> {
	
	private static final long serialVersionUID = 1L;
	private String fdbid;		// 公司客户外键
	private String backupName;		//备份的名字
	private String backupNumber;		//备份的编号
	private String backupDate;		//备份的时间
	private String fileSize;		//文件的大小
	
	public TbackupRecover() {
		super();
	}

	public TbackupRecover(String id){
		super(id);
	}

	@Length(min=1, max=64, message="公司客户外键长度必须介于 1 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	@Length(min=0, max=64, message="税科目的编号长度必须介于 0 和 64 之间")
	public String getBackupName() {
		return backupName;
	}

	public void setBackupName(String backupName) {
		this.backupName = backupName;
	}
	
	@Length(min=0, max=64, message="科目编号长度必须介于 0 和 64 之间")
	public String getBackupNumber() {
		return backupNumber;
	}

	public void setBackupNumber(String backupNumber) {
		this.backupNumber = backupNumber;
	}
	public String getBackupDate() {
		return backupDate;
	}

	public void setBackupDate(String backupDate) {
		this.backupDate = backupDate;
	}
	
	@Length(min=0, max=64, message="file_size长度必须介于 0 和 64 之间")
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
}