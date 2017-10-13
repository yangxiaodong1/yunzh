package com.thinkgem.jeesite.modules.settleAccounts.entity;

import java.util.List;

import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherExp;

/**
 * 结账 转换对象使用
 * @author lanxum
 *
 */
public class TSettle {

	private List<TVoucherExp> tVoucherExps;

	public List<TVoucherExp> gettVoucherExps() {
		return tVoucherExps;
	}

	public void settVoucherExps(List<TVoucherExp> tVoucherExps) {
		this.tVoucherExps = tVoucherExps;
	}

}
