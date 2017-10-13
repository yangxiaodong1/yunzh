/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.balance.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableBiMap;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.entity.AccountAndamortize;
import com.thinkgem.jeesite.modules.account.entity.BalanceSum;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.entity.TaccountNext;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.amortize.entity.TAmortize;
import com.thinkgem.jeesite.modules.amortize.service.TAmortizeService;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfo;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucher.service.TVoucherService;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherExp;

/**
 * 金额平衡表Controller
 * @author zhangtong
 * @version 2015-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/balance/tBalance")
public class TBalanceController extends BaseController {

	@Autowired
	private TBalanceService tBalanceService;
	
	@Autowired
	private TAccountService tAccountService;
	
	//客户的Service
	@Autowired
	private TCustomerService customerService;
	
	//摊销
	@Autowired
	private TAmortizeService tAmortizeService;
	@ModelAttribute
	public TBalance get(@RequestParam(required=false) String id) {
		TBalance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tBalanceService.get(id);
		}
		if (entity == null){
			entity = new TBalance();
		}
		return entity;
	}
	
	//@RequiresPermissions("balance:tBalance:view")
	@RequestMapping(value = {"list", ""})
	public String list(TBalance tBalance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TBalance> page = tBalanceService.findPage(new Page<TBalance>(request, response), tBalance); 
		model.addAttribute("page", page);
		return "modules/balance/tBalanceList";
	}

	//@RequiresPermissions("balance:tBalance:view")
	@RequestMapping(value = "form")
	public String form(TBalance tBalance, Model model) {
		model.addAttribute("tBalance", tBalance);
		return "modules/balance/tBalanceForm";
	}

	//@RequiresPermissions("balance:tBalance:edit")
	@RequestMapping(value = "save")
	public String save(TBalance tBalance, HttpSession session, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {	
		String nTop=request.getParameter("nTop");	
		String nTop1=request.getParameter("nTop1");
		String selectnum=request.getParameter("selectnum");		
		TCustomer customerinfo=(TCustomer)session.getAttribute("sessionCustomer");//获得当前的客户信息
		String fdbid=customerinfo.getId();		
		String initperiod=customerinfo.getInitperiod();//本公司起始账期
		String contactPerson=customerinfo.getCurrentperiod();//当前的账期
		String cjddiq=initperiod.substring(0,4);//起始账期本年的一月份的账期
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<TaccountNext> originalInfo=null;
		//如果当前期和最近期不想等的时候
		if(customerinfo.getLatelyperiod()!=null&&!(customerinfo.getCurrentperiod()).equals(customerinfo.getLatelyperiod())){
			//获得第一期的原始信息
			originalInfo=tAccountService.balanceInfoBydyq(fdbid,cjddiq);
			if(originalInfo.size()>0){
				for(int i=0;i<originalInfo.size();i++){
					map.put(originalInfo.get(i).getId(),originalInfo.get(i));
				}				
			}
		}
		
		
		
		//判断当前账期是不是本公司起始的账期
		if(contactPerson.equals(initperiod)){
			//从前台提取的json串
			String jsoninfo=new String(request.getParameter("info").getBytes("ISO-8859-1"),"utf-8");	
			TBalance info=new TBalance();
			JSONArray jsonArray = new JSONArray(jsoninfo);//把json串转换成json数组
			for(int i=0;i<jsonArray.length();i++){
				TBalance balance=new TBalance();
				String account=jsonArray.getJSONObject(i).get("accountId").toString();//从json数组提取科目id
				String id=jsonArray.getJSONObject(i).get("id1").toString();//初始余额 表的id
				balance.setId(id);
				balance.setAccountId(account);
				String beginfo=jsonArray.getJSONObject(i).get("beginbalance").toString();//起初余额
				//double endinfo=0.0;
				String ytddebittotalamount="0";
				String ytdcredittotalamount="0";
				String ytdamountfor="0";
				String fdc=jsonArray.getJSONObject(i).get("fdc").toString();//余额方向	
				TBalance ut=null;
				if(!"".equals(id)){
					ut=tBalanceService.get(id);
					if(ut!=null){
						ytddebittotalamount=ut.getYtddebittotalamount();
						ytdcredittotalamount=ut.getCredittotalamount();
						ytdamountfor=ut.getYtdamountfor();
					}					
				}		
				if(!"".equals(beginfo)&&beginfo!=null){
					if("-1".equals(fdc)){
						balance.setBeginbalance(String.valueOf((-Double.parseDouble(beginfo))));
						beginfo=String.valueOf((-Double.parseDouble(beginfo)));
					}else{
						balance.setBeginbalance(beginfo);
					}				
				}else{
					balance.setBeginbalance("0");
					beginfo="0";
				}
				if(!"".equals(ytddebittotalamount)&&ytddebittotalamount!=null){
						balance.setYtddebittotalamount(ytddebittotalamount);
						balance.setDebittotalamount(ytddebittotalamount);
				
				}else{
					balance.setYtddebittotalamount("0");
					balance.setDebittotalamount("0");
					ytddebittotalamount="0";
				}			
				if(!"".equals(ytdcredittotalamount)&&ytdcredittotalamount!=null){
						balance.setYtdcredittotalamount(ytdcredittotalamount);
						balance.setCredittotalamount(ytdcredittotalamount);
				}else{
					balance.setYtdcredittotalamount("0");
					balance.setCredittotalamount("0");
					ytdcredittotalamount="0";
				}
				
				BigDecimal endinfo=new BigDecimal(ytddebittotalamount).add(new BigDecimal(beginfo)).subtract(new BigDecimal(ytdcredittotalamount));
				//endinfo=Double.valueOf(ytddebittotalamount)+Double.valueOf(beginfo)-Double.valueOf(ytdcredittotalamount);
				String fgroupId=jsonArray.getJSONObject(i).get("fgroupId").toString();//起初余额
				balance.setEndbalance(String.valueOf(endinfo));
				if("5".equals(fgroupId)){					
					balance.setAmountfor(beginfo);
					balance.setYtdamountfor(beginfo);
					if(ut!=null && "0.00".equals(ut.getEndbalance())){
						balance.setBeginbalance("0");
						balance.setEndbalance("0");
					}else if(ut==null){
						balance.setBeginbalance("0");
						balance.setEndbalance("0");
					}else{
						balance.setBeginbalance(ut.getBeginbalance());
						balance.setEndbalance(ut.getEndbalance());
					}				
				}else {
					if(!"".equals(ytdamountfor)&&ytdamountfor!=null){
						balance.setYtdamountfor(ytdamountfor);
						balance.setAmountfor(ytdamountfor);
					}else{
						balance.setYtdamountfor("0");
						balance.setAmountfor("0");
					}
				}							
				balance.setFdbid(fdbid);
				balance.setAccountPeriod(cjddiq);
				balance.setFcur("RMB");
				tBalanceService.save(balance);
				//维护上级科目
				while(1==1){
					//通过科目id获取科目的父级id
					String fparentid=tAccountService.parindIdById(account);
					System.out.println(fparentid);
					if(fparentid!=null && !"".equals(fparentid) && !"0".equals(fparentid)){
						
						TBalance ba=tBalanceService.cjsdxxBy(fdbid,fparentid,cjddiq);//本公司本期父级的信息
						if(ba!=null){
							BigDecimal beg=new BigDecimal(ba.getBeginbalance());
							BigDecimal end=new BigDecimal(ba.getEndbalance());
							BigDecimal amount=new BigDecimal(ba.getAmountfor());
							String beginbalance="0";
							String endbalance="0";
							String amountfor="0";
							if(ut!=null){
								beginbalance=String.valueOf(beg.subtract(new BigDecimal(ut.getBeginbalance())).add(new BigDecimal(beginfo)));
								endbalance=String.valueOf(end.subtract(new BigDecimal(ut.getEndbalance())).add(new BigDecimal(endinfo.toString())));
								amountfor=String.valueOf(amount.subtract(new BigDecimal(ut.getAmountfor())).add(new BigDecimal(beginfo)));
							}else{
								beginbalance=String.valueOf(beg.add(new BigDecimal(beginfo)));
								endbalance=String.valueOf(end.add(new BigDecimal(endinfo.toString())));
								amountfor=String.valueOf(amount.add(new BigDecimal(beginfo)));
							}							
							if("5".equals(fgroupId)){								
								ba.setAmountfor(amountfor);
								ba.setYtdamountfor(amountfor);
							}else{
								ba.setBeginbalance(beginbalance);
								ba.setEndbalance(endbalance);
							}
							tBalanceService.save(ba);
						}else{							
							TBalance balance2=new TBalance();
							balance2.setFdbid(fdbid);
							balance2.setAccountPeriod(cjddiq);
							balance2.setFcur("RMB");
							balance2.setBeginbalance(beginfo);
							balance2.setEndbalance(String.valueOf(endinfo));
							balance2.setYtddebittotalamount("0");
							balance2.setYtdcredittotalamount("0");								
							balance2.setDebittotalamount("0");
							balance2.setCredittotalamount("0");
							if("5".equals(fgroupId)){
								balance2.setAmountfor(String.valueOf(endinfo));
								balance2.setYtdamountfor(String.valueOf(endinfo));
								balance2.setBeginbalance("0");
								balance2.setEndbalance(String.valueOf("0"));
							}else{
								balance2.setAmountfor("0");
								balance2.setYtdamountfor("0");
							}							
							balance2.setAccountId(fparentid);
							tBalanceService.save(balance2);							
						}
						
						
					}
					if(fparentid==null || "0".equals(fparentid)){
						break;
					}else{
						account=fparentid;				
						}
									
				}				
			}
			//如果公司的初始的第一期不是本年的一月份需要给创建的第一期计算
			
				// 获得所有账期
				List<String> list = tBalanceService.findAllperiod(fdbid);
				if(list.size()<=1){
					list.add(contactPerson);
				}
				//获得第一期的信息
				List<TaccountNext> sourcelist=tAccountService.balanceInfoBydyq(fdbid,cjddiq);
				for (int j = 0; j < list.size(); j++) {	
					if(!cjddiq.equals(list.get(j))){	
						for(int i=0;i<sourcelist.size();i++){
							TBalance tab=new TBalance();
							TaccountNext infob=sourcelist.get(i);
							if(infob!=null){
								//判断当前期是不是有信息
								TBalance tabl=tBalanceService.cjsdxxBy(fdbid,infob.getAccountId(),list.get(j));
								if(tabl!=null){
									if(list.size()>2){
										int isHave=0;		
										TaccountNext orvalue=(TaccountNext)map.get(sourcelist.get(i).getId().toString());							
											if(orvalue!=null && (orvalue.getId()).equals(sourcelist.get(i).getId().toString())){
												tabl.setBeginbalance(String.valueOf(new BigDecimal((infob.getEndbalance()==null?'0':infob.getEndbalance()).toString()).add(new BigDecimal((tabl.getBeginbalance()==null?'0':tabl.getBeginbalance()).toString()).subtract(new BigDecimal((orvalue.getBeginbalance()==null?'0':orvalue.getBeginbalance()).toString())))));
												tabl.setYtdamountfor(String.valueOf(new BigDecimal((infob.getYtdamountfor()==null?'0':infob.getYtdamountfor()).toString()).add(new BigDecimal((tabl.getYtdamountfor()==null?'0':tabl.getYtdamountfor()).toString()).subtract(new BigDecimal((orvalue.getYtdamountfor()==null?'0':orvalue.getYtdamountfor()).toString())))));
												isHave=1;
											}								
										
										if(isHave!=1){	
											tabl.setBeginbalance(String.valueOf(new BigDecimal((infob.getEndbalance()==null?'0':infob.getEndbalance()).toString()).add(new BigDecimal((tabl.getBeginbalance()==null?'0':tabl.getBeginbalance()).toString()))));
											tabl.setYtdamountfor(String.valueOf(new BigDecimal((infob.getYtdamountfor()==null?'0':infob.getYtdamountfor()).toString()).add(new BigDecimal((tabl.getYtdamountfor()==null?'0':tabl.getYtdamountfor()).toString()))));
										}	
									}else{
										tabl.setBeginbalance(infob.getEndbalance());
										tabl.setYtdamountfor(String.valueOf(new BigDecimal((infob.getYtdamountfor()==null?'0':infob.getYtdamountfor()).toString()).add(new BigDecimal((tabl.getAmountfor()==null?'0':tabl.getAmountfor()).toString()))));
									}									
									/*tabl.setYtdcredittotalamount(String.valueOf(new BigDecimal(infob.getYtdcredittotalamount()==null?"0":infob.getYtdcredittotalamount()).add(new BigDecimal((tabl.getCredittotalamount()==null?'0':tabl.getCredittotalamount()).toString()))));
									tabl.setYtddebittotalamount(String.valueOf(new BigDecimal((infob.getYtddebittotalamount()==null?'0':infob.getYtddebittotalamount()).toString()).add(new BigDecimal((tabl.getDebittotalamount()==null?'0':tabl.getDebittotalamount()).toString()))));*/
									
									
									
									BigDecimal end=new BigDecimal((tabl.getBeginbalance()==null?'0':tabl.getBeginbalance()).toString()).add(new BigDecimal((tabl.getDebittotalamount()==null?'0':tabl.getDebittotalamount()).toString())).subtract(new BigDecimal((tabl.getCredittotalamount()==null?'0':tabl.getCredittotalamount()).toString()));
									//double end=Double.parseDouble((infob.getBeginbalance()==null?'0':infob.getBeginbalance()).toString())+Double.parseDouble((tabl.getDebittotalamount()==null?'0':tabl.getDebittotalamount()).toString())-Double.parseDouble((tabl.getCredittotalamount()==null?'0':tabl.getCredittotalamount()).toString());
									tabl.setEndbalance(String.valueOf(end));
									
									tabl.setAccountPeriod(list.get(j));
									tBalanceService.save(tabl);
								}else{
									tab.setYtdamountfor((infob.getYtdamountfor()==null?'0':infob.getYtdamountfor()).toString());
									tab.setAccountPeriod(list.get(j));	
									tab.setAccountId((infob.getAccountId()==null?'0':infob.getAccountId()).toString());						
									tab.setYtdcredittotalamount((infob.getYtdcredittotalamount()==null?'0':infob.getYtdcredittotalamount()).toString());
									tab.setYtddebittotalamount((infob.getYtddebittotalamount()==null?'0':infob.getYtddebittotalamount()).toString());
									tab.setBeginbalance((infob.getBeginbalance()==null?'0':infob.getBeginbalance()).toString());
									tab.setEndbalance((infob.getBeginbalance()==null?'0':infob.getBeginbalance()).toString());
									tab.setDebittotalamount("0");
									tab.setCredittotalamount("0");
									tab.setAmountfor("0");
									tab.setFcur("RMB");
									tab.setFdbid(fdbid);
									tBalanceService.save(tab);
								}
								
							}
					}
				}
			}
		}		
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+"/a/account/tAccount/balan?nTop="+nTop+"&nTop1="+nTop1+"&selectnum="+selectnum;
	}
	
	@RequestMapping(value = "parentdetail")
	public void parentdetail(PrintWriter printWriter,HttpServletResponse response,HttpSession session,HttpServletRequest request){
		response.setHeader("Pragma","No-Cache");
    	response.setHeader("Cache-Control","No-Cache");
    	response.setDateHeader("Expires", 0);
    	TCustomer customerinfo=(TCustomer)session.getAttribute("sessionCustomer");//获得当前的客户信息
		String fdbid=customerinfo.getId();
    	String accountId=request.getParameter("id"); 
    	
    	TBalance tBalance=tBalanceService.cjsdxxBy(fdbid, accountId, customerinfo.getCurrentperiod());
    	int count=0;
    	if(customerinfo.getCurrentperiod().equals(customerinfo.getInitperiod())){
	    	if(tBalance!=null){
	    		if (!"0.00".equals(tBalance.getBeginbalance()) || !"0.00".equals(tBalance.getYtdamountfor())||!"0.00".equals(tBalance.getYtdcredittotalamount())||!"0.00".equals(tBalance.getYtddebittotalamount())||!"0.00".equals(tBalance.getEndbalance())) {
	        		count=1;
	        	}
	    	} 
    	}else{
    		if(tBalance!=null){
    			count=1;
    		}
    	}
    	String jsonString="";
    	JsonMapper json=new JsonMapper();		
		jsonString=json.toJsonString(count);				
        printWriter.write(jsonString);  
        printWriter.flush();  
        printWriter.close(); 
	}
	
	
	
	//@RequiresPermissions("balance:tBalance:edit")
	@RequestMapping(value = "delete")
	public String delete(TBalance tBalance, RedirectAttributes redirectAttributes) {
		tBalanceService.delete(tBalance);
		addMessage(redirectAttributes, "删除金额平衡表成功");
		return "redirect:"+Global.getAdminPath()+"/balance/tBalance/?repage";
	}

	//获取科目上一期的 endbalance
	@RequestMapping(value = "getLastAccountEndBalance")
	@ResponseBody
	public Object  getLastAccountEndBalance(String accountId,HttpSession session) {
		TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息
		String accountPeriod = customerinfo.getCurrentperiod();
		return tBalanceService.getLastAccountEndBalance(customerinfo.getId(), accountId,accountPeriod);
	}
	@RequestMapping(value = "saveVoucher")
	@ResponseBody
	public Object saveVoucher(String voucherExpStr, String billinfoid, TAmortize amortize, String debitAccountId, String creditAccountId,String debitAmortizeId, String creditAmortizeId, HttpServletRequest request,
			HttpSession session) {
		boolean flag = true;		
			
		// 账期 有session获取
		TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		/*System.out.println(df.format(new Date()));*/
		amortize.setEnterAccountDate(df.format(new Date()));
		if(null!=amortize){
			amortize.setAmortizeStatus("1");
			amortize.settVoucherExpId(amortize.getAmortizeAccountId()); //代表初始化余额时候的自动摊销
			amortize.setAccountId(debitAccountId);
			amortize.setIfInit("1");
			amortize.setIsNewRecord(true);
			amortize.setDc("1");
			// add by wub 20151224
			amortize.setFdbid(customerinfo.getId());
			amortize.setCurrentPeriod(customerinfo.getCurrentperiod());
			if(null!=debitAmortizeId && !"".equals(debitAmortizeId)){
				amortize.setId(debitAmortizeId);
				tAmortizeService.update(amortize);
			}else{
				/*amortize.setId(UUID.randomUUID().toString());*/
				tAmortizeService.save(amortize);
			}			
			amortize.setIsNewRecord(true);
			amortize.setAccountId(creditAccountId);
			amortize.setDc("-1");
			if(null!=creditAmortizeId && !"".equals(creditAmortizeId)){
				amortize.setId(creditAmortizeId);
				tAmortizeService.update(amortize);
			}else{
				/*amortize.setId(UUID.randomUUID().toString());*/
				tAmortizeService.save(amortize);
			}
			
		} else {
			flag = false;
		}
		return ImmutableBiMap.of("suc", flag);
	}
	
	 @RequestMapping(value = "infoamortize", method = RequestMethod.GET)  
	    public void infoamortize(PrintWriter printWriter,HttpServletResponse response, HttpSession session,HttpServletRequest request) { 
	    	response.setHeader("Pragma","No-Cache");
	    	response.setHeader("Cache-Control","No-Cache");
	    	response.setDateHeader("Expires", 0);
	    	TCustomer customerinfo=(TCustomer)session.getAttribute("sessionCustomer");//获得当前的客户信息
			String fdbid=customerinfo.getId();
			String accId=request.getParameter("id");
			List<AccountAndamortize> list=tAmortizeService.infobyfdbidandaccountid(fdbid,accId);
			
	    	String jsonString="";
	    	JsonMapper json=new JsonMapper(); 	
			if(list!=null && list.size()>0){
				jsonString=json.toJsonString(list);
			}				
	        printWriter.write(jsonString);  
	        printWriter.flush();  
	        printWriter.close();  
	    }  
}