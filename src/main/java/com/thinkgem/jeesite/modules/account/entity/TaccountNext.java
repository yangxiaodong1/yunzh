package com.thinkgem.jeesite.modules.account.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/*
 * 科目表和余额表
 * */
public class TaccountNext extends DataEntity<TaccountNext>{
	
	private String accuntId;		//科目编号
	private String accountName;		//科目名称
	private TaccountNext parentId;		//父级id
	private String dc;		//余额方向		
	private String accountId;	//
	private String id1;//余额表的id
	private String groupId;//科目分组编号
	private String detail;//是否有下一级
	private String level;//几级科目
	private String beginbalance;//年初余额
	private String endbalance;//起初余额
	private String ytddebittotalamount;//本年合计  借方金额
	private String ytdcredittotalamount;// 本年合计  贷方金额
	private String ytdamountfor; //损益本年的累计
	private String ifAmortize;//判断是否摊销
	
	
	public String getAccuntId() {
		return accuntId;
	}
	public void setAccuntId(String accuntId) {
		this.accuntId = accuntId;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public TaccountNext getParentId() {
		return parentId;
	}


	public void setParentId(TaccountNext parentId) {
		this.parentId = parentId;
	}


	public String getDc() {
		return dc;
	}


	public void setDc(String dc) {
		this.dc = dc;
	}


	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public String getId1() {
		return id1;
	}


	public void setId1(String id1) {
		this.id1 = id1;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}


	public String getBeginbalance() {
		return beginbalance;
	}


	public void setBeginbalance(String beginbalance) {
		this.beginbalance = beginbalance;
	}


	public String getEndbalance() {
		return endbalance;
	}


	public void setEndbalance(String endbalance) {
		this.endbalance = endbalance;
	}


	public String getYtddebittotalamount() {
		return ytddebittotalamount;
	}


	public void setYtddebittotalamount(String ytddebittotalamount) {
		this.ytddebittotalamount = ytddebittotalamount;
	}


	public String getYtdcredittotalamount() {
		return ytdcredittotalamount;
	}


	public void setYtdcredittotalamount(String ytdcredittotalamount) {
		this.ytdcredittotalamount = ytdcredittotalamount;
	}


	public String getYtdamountfor() {
		return ytdamountfor;
	}


	public void setYtdamountfor(String ytdamountfor) {
		this.ytdamountfor = ytdamountfor;
	}
	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}

	public String getIfAmortize() {
		return ifAmortize;
	}


	public void setIfAmortize(String ifAmortize) {
		this.ifAmortize = ifAmortize;
	}


	@JsonIgnore
	public static void sortList(List<TaccountNext> list, List<TaccountNext> sourcelist, String parentIds, boolean cascade) throws NumberFormatException, ParseException{
		for (int i=0; i<sourcelist.size(); i++){
			TaccountNext e = sourcelist.get(i);
			if (e.getParentId()!=null && e.getParentId()!=null
					&& e.getParentId().getId().equals(parentIds)){
				DecimalFormat f = new DecimalFormat("0,000.00");
				if("-1".equals(e.getDc())){
					if(e.getBeginbalance()!=null){		
						String beg=e.getBeginbalance();
						Double b = Double.parseDouble(f.parseObject(beg).toString());
						BigDecimal begs = new BigDecimal(b);
						e.setBeginbalance(String.valueOf((-Double.parseDouble(begs.toString()))));						
					}
					if(e.getYtdamountfor()!=null){		
						String damount=e.getYtdamountfor();
						Double m = Double.parseDouble(f.parseObject(damount).toString());
						BigDecimal damounts = new BigDecimal(m);
						e.setYtdamountfor(String.valueOf((-Double.parseDouble(damounts.toString()))));						
					}
				}		
				String fgroupid=e.getGroupId().substring(0,1);
				String end=e.getBeginbalance();
				if("0.00".equals(e.getBeginbalance())){
					e.setBeginbalance(null);
				}
				if("-0.0".equals(e.getBeginbalance())){
					e.setBeginbalance(null);
				}
				if("0.00".equals(e.getYtdamountfor())){
					e.setYtdamountfor(null);
				}
				if("-0.0".equals(e.getYtdamountfor())){
					e.setYtdamountfor(null);
				}
				String beginfo=e.getBeginbalance();
				if(beginfo!=null){
					DecimalFormat  df=new DecimalFormat("###,##0.00");
					/*DecimalFormat  dq=new DecimalFormat("0.0");*/
					Double d = Double.parseDouble(f.parseObject(beginfo).toString());
					BigDecimal beginfos = new BigDecimal(d);
					/*beginfo=dq.format(beginfos);*/
					beginfo=df.format(beginfos);			
				}
				String ytdamountfor=e.getYtdamountfor();
				if(ytdamountfor!=null){
					DecimalFormat  df=new DecimalFormat("###,##0.00");
					/*DecimalFormat  dq=new DecimalFormat("0.0");*/
					Double n = Double.parseDouble(f.parseObject(ytdamountfor).toString());
					BigDecimal ytdamountfors = new BigDecimal(n);
					/*beginfo=dq.format(beginfos);*/
					ytdamountfor=df.format(ytdamountfors);			
				}
				e.setBeginbalance(beginfo);
				e.setYtdamountfor(ytdamountfor);
				e.setGroupId(fgroupid);
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						TaccountNext child = sourcelist.get(j);
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
	public static String rootid(){
		return "0";
	}
}
