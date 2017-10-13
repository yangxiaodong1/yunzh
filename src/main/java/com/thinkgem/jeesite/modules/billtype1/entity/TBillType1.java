
package com.thinkgem.jeesite.modules.billtype1.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 发票类型1Entity
 * @author 发票类型1
 * @version 2015-10-21
 */
public class TBillType1 extends DataEntity<TBillType1> {
	
	private static final long serialVersionUID = 1L;
	private String billTypeName;		// bill_type_name
	
	private String tbId;
	
	public TBillType1() {
		super();
	}

	public TBillType1(String id){
		super(id);
	}

	@Length(min=0, max=100, message="bill_type_name长度必须介于 0 和 100 之间")
	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getTbId() {
		return tbId;
	}

	public void setTbId(String tbId) {
		this.tbId = tbId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}