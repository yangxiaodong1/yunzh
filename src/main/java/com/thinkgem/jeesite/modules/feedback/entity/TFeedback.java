/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.entity;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 意见反馈Entity
 * @author rongjd
 * @version 2016-01-20
 */
public class TFeedback extends DataEntity<TFeedback> {
	
	private static final long serialVersionUID = 1L;
	private String feed;		// 意见
	private String back;		// 反馈
	private String isBack; 	// 是否有回复：0 未回复 1 已回复
	private String feedFiles;
	private String[] images;
	
	public TFeedback() {
		super();
	}

	public TFeedback(String id){
		super(id);
	}
	
	@JsonIgnore
	@Length(min=1, max=1)
	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	@Length(min=0, max=500, message="意见长度必须介于 0 和 500 之间")
	public String getFeed() {
		return feed;
	}

	public void setFeed(String feed) {
		this.feed = feed;
	}
	
	@Length(min=0, max=500, message="反馈长度必须介于 0 和 500 之间")
	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public String getFeedFiles() {
		return feedFiles;
	}

	public void setFeedFiles(String feedFiles) {
		this.feedFiles = feedFiles;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
	
}