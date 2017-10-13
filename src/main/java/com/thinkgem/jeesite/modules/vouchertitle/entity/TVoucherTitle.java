/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertitle.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 凭证标题Entity
 * @author 凭证标题
 * @version 2015-10-30
 */
public class TVoucherTitle extends DataEntity<TVoucherTitle> {
	
	private static final long serialVersionUID = 1L;
	private String voucherTitleName;		// voucher_title_name
	private String printTitle;		// print_title
	private String isDefault;		// 0不是默认            1为默认
	
	public TVoucherTitle() {
		super();
	}

	public TVoucherTitle(String id){
		super(id);
	}

	@Length(min=0, max=64, message="voucher_title_name长度必须介于 0 和 64 之间")
	public String getVoucherTitleName() {
		return voucherTitleName;
	}

	public void setVoucherTitleName(String voucherTitleName) {
		this.voucherTitleName = voucherTitleName;
	}
	
	@Length(min=0, max=64, message="print_title长度必须介于 0 和 64 之间")
	public String getPrintTitle() {
		return printTitle;
	}

	public void setPrintTitle(String printTitle) {
		this.printTitle = printTitle;
	}
	
	@Length(min=0, max=1, message="0不是默认            1为默认长度必须介于 0 和 1 之间")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
}