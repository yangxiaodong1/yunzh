/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.billtype.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 发票类型Entity
 * @author 发票类型
 * @version 2015-10-21
 */
public class TBillType extends DataEntity<TBillType> {
	
	private static final long serialVersionUID = 1L;
	private String billTypeName;		// bill_type_name
	
	public TBillType() {
		super();
	}

	public TBillType(String id){
		super(id);
	}

	@Length(min=0, max=100, message="bill_type_name长度必须介于 0 和 100 之间")
	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}
	
}