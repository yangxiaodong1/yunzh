/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package loginServlet.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author gyg
 * @version 2016-02-16
 */
public class TBillFeeType {
	
	private String id;//公司id
	private String billFeeTypeName;		// bill_fee_type_name
	private String level;		// level
	private String detail;		// detail
	private String parent;		// parent_id
	private String groupId;		// group_id
	private String isEnable;		// is_enable
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public TBillFeeType() {
		super();
	}

	public String getBillFeeTypeName() {
		return billFeeTypeName;
	}

	public void setBillFeeTypeName(String billFeeTypeName) {
		this.billFeeTypeName = billFeeTypeName;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	
}