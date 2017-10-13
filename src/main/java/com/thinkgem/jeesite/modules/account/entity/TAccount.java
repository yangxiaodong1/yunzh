/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 科目信息操作Entity
 * @author 陈明
 * @version 2015-12-02
 */
public class TAccount extends DataEntity<TAccount> {
	private static final long serialVersionUID = 1L;
	private String accuntId;		//科目编号
	private String fdbid;		//公司id
	private String accountName;		//科目名称
	private String level;		//几级科目
	private String detail;		//是否子级
	private TAccount parentId;		//科目父级id
	private String dc;		//余额方向  1 借  -1 贷
	private String rootId;		//根id
	private String groupId;		//科目分组id
	private String isEnable;		//是否启用
	private String israteenable; //税种是否启用
	private String accountNamefor;
	private String accuntIdfor;
	private String idFor;
	private String rate;
	private int ifAmortize; //是否设置自动摊销  0 否 1 是 add by wub 20151210
	
	private String parentName;//科目父级 名称
	
	private String initName;//首字母小写
	private String initNameParent;//首字母小写
	
	private String debitTotal;//借方金额	 add  by  zt
	private String creditTotal;//贷方金额 
	
	public TAccount() {
		super();
	}

	public TAccount(String id){
		super(id);
	}

	@Length(min=0, max=64, message="accunt_id长度必须介于 0 和 64 之间")
	public String getAccuntId() {
		return accuntId;
	}

	public void setAccuntId(String accuntId) {
		this.accuntId = accuntId;
	}
	
	@Length(min=0, max=64, message="fdbid长度必须介于 0 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	@Length(min=0, max=64, message="account_name长度必须介于 0 和 64 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Length(min=0, max=64, message="level长度必须介于 0 和 64 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=64, message="detail长度必须介于 0 和 64 之间")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public TAccount getParentId() {
		return parentId;
	}

	public void setParentId(TAccount parentId) {
		this.parentId = parentId;
	}
	
	@Length(min=0, max=20, message="dc长度必须介于 0 和 20 之间")
	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}
	
	@Length(min=0, max=64, message="root_id长度必须介于 0 和 64 之间")
	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
	
	@Length(min=0, max=64, message="group_id长度必须介于 0 和 64 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@Length(min=0, max=1, message="is_enable长度必须介于 0 和 1 之间")
	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	public String getAccountNamefor() {
		return accountNamefor;
	}

	public void setAccountNamefor(String accountNamefor) {
		this.accountNamefor = accountNamefor;
	}

	public String getAccuntIdfor() {
		return accuntIdfor;
	}

	public void setAccuntIdfor(String accuntIdfor) {
		this.accuntIdfor = accuntIdfor;
	}
	
	public String getIdFor() {
		return idFor;
	}

	public void setIdFor(String idFor) {
		this.idFor = idFor;
	}
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@JsonIgnore
	public static void sortList(List<TAccount> list, List<TAccount> sourcelist, String parentIds, boolean cascade){
		for (int i=0; i<sourcelist.size(); i++){
			TAccount e = sourcelist.get(i);
			if (e.getParentId()!=null && e.getParentId()!=null
					&& e.getParentId().getId().equals(parentIds)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						TAccount child = sourcelist.get(j);
						if (child.getParentId()!=null && child.getParentId().getId()!=null
								&& child.getParentId().getId().equals(e.getId())){
							sortList(list, sourcelist, e.getId(),true);
							break;
						}
					}
				}
			}
		}
	}

	public int getIfAmortize() {
		return ifAmortize;
	}

	public void setIfAmortize(int ifAmortize) {
		this.ifAmortize = ifAmortize;
	}
	public static String roodid(){
		return "0";
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getIsrateenable() {
		return israteenable;
	}

	public void setIsrateenable(String israteenable) {
		this.israteenable = israteenable;
	}

	public String getInitName() {
		return initName;
	}

	public void setInitName(String initName) {
		this.initName = initName;
	}
	
	

	public String getInitNameParent() {
		return initNameParent;
	}

	public void setInitNameParent(String initNameParent) {
		this.initNameParent = initNameParent;
	}

	public String getDebitTotal() {
		return debitTotal;
	}

	public void setDebitTotal(String debitTotal) {
		this.debitTotal = debitTotal;
	}

	public String getCreditTotal() {
		return creditTotal;
	}

	public void setCreditTotal(String creditTotal) {
		this.creditTotal = creditTotal;
	}

}