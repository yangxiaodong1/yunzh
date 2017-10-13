/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucherexp.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucher.service.TVoucherService;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherExp;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherexpvo;
import com.thinkgem.jeesite.modules.voucherexp.service.TVoucherExpService;

/**
 * 凭证摘要Controller
 * @author 凭证摘要
 * @version 2015-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/voucherexp/tVoucherExp")
public class TVoucherExpController extends BaseController {

	@Autowired
	private TVoucherExpService tVoucherExpService;
	@Autowired
	private TVoucherService tVoucherService;
	@Autowired
	private TAccountService tAccountService;
	@Autowired
	private TCustomerService tCustomerService;
	@Autowired
	private TBalanceService tBalanceService;
	
	@ModelAttribute
	public TVoucherExp get(@RequestParam(required=false) String id) {
		TVoucherExp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tVoucherExpService.get(id);
		}
		if (entity == null){
			entity = new TVoucherExp();
		}
		return entity;
	}
	
	@RequiresPermissions("voucherexp:tVoucherExp:view")
	@RequestMapping(value = {"list", ""})
	public String list(TVoucherExp tVoucherExp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TVoucherExp> page = tVoucherExpService.findPage(new Page<TVoucherExp>(request, response), tVoucherExp); 
		model.addAttribute("page", page);
		return "modules/voucherexp/tVoucherExpList";
	}
	
	//显示凭证信息
	@RequestMapping(value = "periodShow")
	public String periodShow(Model model,TVoucher tVoucher,HttpSession session){
		DecimalFormat  df=new DecimalFormat("###,##0.00");
		TCustomer tCustomer=(TCustomer) session.getAttribute("sessionCustomer");
		if(tCustomer.getId()==null || tCustomer.getId()=="")
			tVoucher.setFdbid("7");
		else
			tVoucher.setFdbid(tCustomer.getId());

		//1.页面初始的时候必须要有的变量Period
		TVoucherExp tVoucherExp=new TVoucherExp();
		model.addAttribute("tVoucherExp", tVoucherExp);
		model.addAttribute("tVoucher", tVoucher);
		//公司当前账期
		TVoucher tVoucherfirst=new TVoucher();
		tVoucherfirst.setAccountPeriod(tCustomerService.get(tVoucher.getFdbid()).getCurrentperiod());
		model.addAttribute("tVoucherfirst",tVoucherfirst);
		//2.客户的所有账期(不能有重复的，所以用分组 查询)
		List<TVoucher> periodListshow=new ArrayList<TVoucher>();
		String defaultPeriod =  tCustomerService.get(tVoucher.getFdbid()).getCurrentperiod(); //公司的当前账期
		/*List<TVoucher> periodList=new ArrayList<TVoucher>();
		List<TVoucher> periodListshow=new ArrayList<TVoucher>();
		List<TVoucher> periodList1=tVoucherService.finperiods(tVoucher.getFdbid());
		
		String defaultPeriod =  tCustomerService.get(tVoucher.getFdbid()).getCurrentperiod(); //公司的当前账期
		boolean flag = false;
		for (TVoucher tvo : periodList1) {
			if (tvo.getAccountPeriod().equals(defaultPeriod)) {
				flag = true;	//如果存在
			}

//		if(periodList1.size()==0){
//			periodList.add(tVoucherfirst);

		}
		periodList = periodList1;
		//如果不存在
		if (!flag) {
			TVoucher voucher = new TVoucher();
			voucher.setAccountPeriod(defaultPeriod);
			periodList.add(voucher);
		}*/
		//------------------
		// 获得所有账期
		List<String> periodList = tBalanceService.getAllAccountperiod(tCustomer.getId());
		if(periodList.size()<1){
			periodList.add(tCustomer.getCurrentperiod());
		}
		
		//存放凭证摘要
		List<TVoucherExp> tvoucherexpList=new ArrayList<TVoucherExp>();
		
		//3. 获取凭证信息过滤条件： 选择的客户ID、期数（默认是当前期）
		List<TVoucher> tvoucherList=new ArrayList<TVoucher>();
		
		//假设当前账期为201502（初次加载）
		if(tVoucher.getAccountPeriod()==null){
			//根据客户外键来获取该客户的当前账期
			System.out.println(tCustomerService.get(tVoucher.getFdbid()).getCurrentperiod());
			tVoucher.setAccountPeriod(tCustomerService.get(tVoucher.getFdbid()).getCurrentperiod());
		}
		tvoucherList=tVoucherService.findShowList(tVoucher);
		
		
		//判断 该公司‘ 凭证信息 ’ 是否有数据（遍历凭证信息）
		if(tvoucherList.size()>0){
			for(TVoucher tv:tvoucherList){
				List<TVoucherExp> list=tVoucherExpService.findbytvid(tv.getId());
				if(tv.getCommenResult()==null){
					tv.setCommenResult("0");
				}
				periodListshow.add(tv);
				//得到该凭证的记录条数
				tv.setCount(list.size());
				if(list.size()>0){
					double totalamount=0;
					for(TVoucherExp te:list){
						if(tv.getId().equals(te.getTVId()))
						{
							totalamount=totalamount+te.getCredit();
							if(te.getDebit()==0){
								te.setShowdebit("");
							}else{
								te.setShowdebit(df.format(te.getDebit()));
							}
							if(te.getCredit()==0){
								te.setShowcredit("");
							}else{
								te.setShowcredit(df.format(te.getCredit()));
							}
							//计算借贷方金额
							
//							te.setShowcredit(df.format(te.getCredit()));
//							te.setShowdebit(df.format(te.getDebit()));
						}
						tvoucherexpList.add(te);
					}
					tv.setTotalAmount(totalamount);
					tv.setTotalAmountshow(df.format(totalamount));
					//转换成大写
					BigDecimal numberOfMoney =BigDecimal.valueOf(Double.valueOf(String.valueOf(totalamount)));
					String money=NumberToCN.number2CNMontrayUnit(numberOfMoney);
					tv.setMoney(money);
					System.out.println(tv.getMoney());
				}
			}
		}
		
		model.addAttribute("tvoucherList", periodListshow);
		model.addAttribute("tvoucherexpList", tvoucherexpList);
		model.addAttribute("periodList", periodList);
		model.addAttribute("Currentperiod",tCustomerService.get(tVoucher.getFdbid()).getCurrentperiod());
		System.out.println();
		
		TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息
		String updateDate = customerinfo.getCurrentperiod();
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, Integer.parseInt(updateDate.substring(0, 4)));
		// 设置月份
		cal.set(Calendar.MONTH, Integer.parseInt(updateDate.substring(4, 6)) - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		model.addAttribute("date", lastDayOfMonth);
		User currentUser = UserUtils.getUser();
		model.addAttribute("user", currentUser);
		
		//---------------查询出‘当前账期最近的’-----------------
		/*List<TVoucher> listtouver=selectTvoucher(defaultPeriod,tVoucher.getFdbid());
		//System.out.println(listtouver.get(0).getAccountPeriod());
		if(listtouver.size()!=0)
			model.addAttribute("selectTvoucher",listtouver.get(0).getAccountPeriod());*/
		//陈明加的
		String latelynextperiod=tBalanceService.selectPeriod(customerinfo.getId(), defaultPeriod);
		if(!"".equals(latelynextperiod)){
			model.addAttribute("selectTvoucher",latelynextperiod);
		}
		return "modules/voucherexp/tVoucherExpList";
	}
	
	//ajax显示
	@RequestMapping(value = "periodAjax")
	@ResponseBody
	public List<TVoucherexpvo> periodAjax(Model model,TVoucher tVoucher,HttpSession session){
		//显示凭证的vo类
		List<TVoucherexpvo> volist=new ArrayList<TVoucherexpvo>();
		
		DecimalFormat  df=new DecimalFormat("###,##0.00");
		TCustomer tCustomer=(TCustomer) session.getAttribute("sessionCustomer");
		System.out.println(tCustomer.getId());
		if(tCustomer.getId()==null || tCustomer.getId()=="")
			tVoucher.setFdbid("7");
		else
			tVoucher.setFdbid(tCustomer.getId());
		String defaultPeriod =  tCustomerService.get(tVoucher.getFdbid()).getCurrentperiod(); //公司的当前账期
		//1.页面初始的时候必须要有的变量Period
		TVoucherExp tVoucherExp=new TVoucherExp();
		model.addAttribute("tVoucherExp", tVoucherExp);
		model.addAttribute("tVoucher", tVoucher);
		//公司当前账期
		TVoucher tVoucherfirst=new TVoucher();
		tVoucherfirst.setAccountPeriod(tCustomerService.get(tVoucher.getFdbid()).getCurrentperiod());
		model.addAttribute("tVoucherfirst",tVoucherfirst);
		//2.客户的所有账期(不能有重复的，所以用分组 查询)
		List<TVoucher> periodList=new ArrayList<TVoucher>();
		List<TVoucher> periodListshow=new ArrayList<TVoucher>();
		List<TVoucher> periodList1=tVoucherService.finperiods(tVoucher.getFdbid());
		
		
		System.out.println(defaultPeriod);
		boolean flag = false;
		for (TVoucher tvo : periodList1) {
			if (tvo.getAccountPeriod().equals(defaultPeriod)) {
				flag = true;	//如果存在
			}

		}
		periodList = periodList1;
		//如果不存在
		if (!flag) {
			TVoucher voucher = new TVoucher();
			voucher.setAccountPeriod(defaultPeriod);
			periodList.add(voucher);
		}
		//------------------
		
		
		//存放凭证摘要
		
		
		//3. 获取凭证信息过滤条件： 选择的客户ID、期数（默认是当前期）
		List<TVoucher> tvoucherList=new ArrayList<TVoucher>();
		
		//假设当前账期为201502（初次加载）
		if(tVoucher.getAccountPeriod()==null){
			//根据客户外键来获取该客户的当前账期
			System.out.println(tCustomerService.get(tVoucher.getFdbid()).getCurrentperiod());
			tVoucher.setAccountPeriod(tCustomerService.get(tVoucher.getFdbid()).getCurrentperiod());
		}
		tvoucherList=tVoucherService.findShowList(tVoucher);
		
		
		//判断 该公司‘ 凭证信息 ’ 是否有数据（遍历凭证信息）
		if(tvoucherList.size()>0){
			for(TVoucher tv:tvoucherList){
				StringBuffer json=new StringBuffer();		//用于存放json数据
				StringBuffer json2=new StringBuffer();
				json.append("[");
				TVoucherexpvo vo=new TVoucherexpvo();
				vo.setTvoucherexptvid(tv.getId());//.setTvoucherexptVId(tv.getId());
				vo.setUsername(tv.getUserName());//.setUserName(tv.getUserName());
				vo.setAccountdate(tv.getAccountDate());//.setAccountDate(tv.getAccountDate());
				vo.setVouchertitlenumber(tv.getVoucherTitleNumber());//.setVoucherTitleNumber(tv.getVoucherTitleNumber());
				List<TVoucherExp> list=tVoucherExpService.findbytvid(tv.getId());
				if(tv.getCommenResult()==null){
					//vo.setCommenResult("0");
				}
				periodListshow.add(tv);
				//得到该凭证的记录条数
				//vo.setCount(list.size());
				if(list.size()>0){
					double totalamount=0;
					List<TVoucherExp> tvoucherexpList=new ArrayList<TVoucherExp>();
					for(TVoucherExp te:list){
					
						if(tv.getId().equals(te.getTVId()))
						{
							 
							totalamount=totalamount+te.getCredit();
							if(te.getDebit()==0){
								te.setShowdebit("");
							}else{
								te.setShowdebit(df.format(te.getDebit()));
							}
							if(te.getCredit()==0){
								te.setShowcredit("");
							}else{
								te.setShowcredit(df.format(te.getCredit()));
							}
							//计算借贷方金额
							
//							te.setShowcredit(df.format(te.getCredit()));
//							te.setShowdebit(df.format(te.getDebit()));
						}
						tvoucherexpList.add(te);
						json2.append("{");
						json2.append("exp:").append("\"").append(te.getExp()).append("\",");
						json2.append("accountName:").append("\"").append(te.getAccountName()).append("\",");
						json2.append("debit:").append("\"").append(te.getDebit()).append("\",");
						json2.append("credit:").append("\"").append(te.getCredit()).append("\"");
						json2.append("},");
					}
					tv.setTotalAmount(totalamount);
					vo.setTotalamount(totalamount);//.setTotalAmount(totalamount);
					/*JsonConfig config = new JsonConfig();
					config.setExcludes(new String[]{"tVId"});
					config.setExcludes(new String[]{"accountId"});
					config.setExcludes(new String[]{"fdbid"});
					JSONArray listjson=JSONArray.fromObject(tvoucherexpList,config);*/
					//json.append("exp:").append("\"").append(tvoucherexpList.get(0).getAccountName()).append("\"");
					String t=json2.substring(0, json2.length()-1);
					json.append(t);
					json.append("]");
					System.out.println(json.toString());
					/*System.out.println(listjson.toString());*/
					vo.setTvoucherList(json.toString());//.setTVoucherList(tvoucherexpList);
					//转换成大写
					BigDecimal numberOfMoney =BigDecimal.valueOf(Double.valueOf(String.valueOf(totalamount)));
					String money=NumberToCN.number2CNMontrayUnit(numberOfMoney);
					tv.setMoney(money);
					vo.setMoney(money);
					System.out.println(tv.getMoney());
				}
				volist.add(vo);
			}
		}
		//model.addAttribute("voshow",volist);
		model.addAttribute("periodList", periodList);
		model.addAttribute("Currentperiod",defaultPeriod);
		System.out.println();
		
		TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息
		String updateDate = customerinfo.getCurrentperiod();
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, Integer.parseInt(updateDate.substring(0, 4)));
		// 设置月份
		cal.set(Calendar.MONTH, Integer.parseInt(updateDate.substring(4, 6)) - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		model.addAttribute("date", lastDayOfMonth);
		User currentUser = UserUtils.getUser();
		model.addAttribute("user", currentUser);
		
		//---------------查询出‘当前账期最近的’-----------------
		List<TVoucher> listtouver=selectTvoucher(defaultPeriod,tVoucher.getFdbid());
		if(listtouver.size()!=0)
			model.addAttribute("selectTvoucher",listtouver.get(0).getAccountPeriod());
				
		//System.out.println(volist.get(0).getTVoucherList());
		//JSONArray jsonob=JSONArray.fromObject(volist);
		//System.out.println(jsonob);
		return volist;
	}
	
	
	@RequiresPermissions("voucherexp:tVoucherExp:view")
	@RequestMapping(value = "form")
	public String form(TVoucherExp tVoucherExp, Model model) {
		model.addAttribute("tVoucherExp", tVoucherExp);
		return "modules/voucherexp/tVoucherExpForm";
	}
	/**
	 * 根据凭证编号获取该编号下的摘要
	 * */
	public List<TVoucherExp> allbytvid(String tvoucherId){
		List<TVoucherExp> list=tVoucherExpService.findbytvid(tvoucherId);
		System.out.println("length:"+list.size());
		return list;
	}
	/**
	 * 反结账最近的账期
	 * **/
	public List<TVoucher> selectTvoucher(String accountPeriod,String fdbid){
		TVoucher tvoucher=new TVoucher();
		tvoucher.setAccountPeriod(accountPeriod);
		tvoucher.setFdbid(fdbid);
		List<TVoucher> list=tVoucherService.selectPeriod(tvoucher);
		System.out.println("length:"+list.size());
		return list;
	}
	@RequiresPermissions("voucherexp:tVoucherExp:edit")
	@RequestMapping(value = "save")
	public String save(TVoucherExp tVoucherExp, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tVoucherExp)){
			return form(tVoucherExp, model);
		}
		tVoucherExpService.save(tVoucherExp);
		addMessage(redirectAttributes, "保存凭证摘要成功");
		return "redirect:"+Global.getAdminPath()+"/voucherexp/tVoucherExp/?repage";
	}
	
	//@RequiresPermissions("voucherexp:tVoucherExp:edit")
	@RequestMapping(value = "delete")
	public String delete(TVoucherExp tVoucherExp, RedirectAttributes redirectAttributes) {
		tVoucherExpService.delete(tVoucherExp);
		addMessage(redirectAttributes, "删除凭证摘要成功");
		return "redirect:"+Global.getAdminPath()+"/voucherexp/tVoucherExp/?repage";
	}
	
	public void deleteAndupdate(TVoucher tVoucher){
		//获取 比 要删除的记字号 大的字号
		List<TVoucher>  TVoucherlist=tVoucherService.findShowList(tVoucher);
		System.out.println("length:"+TVoucherlist.size());
		for(TVoucher tv:TVoucherlist){
			System.out.println("tv :"+tv.getVoucherTitleNumber()+"id:"+tv.getId());
			tv.setVoucherTitleNumber(String.valueOf(Integer.valueOf(tv.getVoucherTitleNumber())-1));
			tVoucherService.updateanddelete(tv);
		}	
	}
	@RequestMapping(value = "deletebytvid")
	//@ResponseBody
	public String deletebytvid(TVoucherExp tVoucherExp, RedirectAttributes redirectAttribute,Model model,HttpSession session,HttpServletRequest request){
		
		String fresult="0";
		try{
			String aa= request.getHeader("referer");
			if(aa!=null){
				fresult="1";
			}
						
		}catch(Exception e){
			System.out.println("输入的地址错误!");
		}finally{
			
		}
		if("1".equals(fresult)){			
		
			//根据要删除的记录主键来得到凭证表信息(因为要得到voucher_title_number数值)
			System.out.println(request.getParameter("tVId"));
			String resultString="0";
			TVoucher tVoucher=tVoucherService.get(tVoucherExp.getTVId());
			
				try{
					Map<String, Object> map=new HashMap<String, Object>();
					System.out.println(tVoucherExp.getTVId());
					map.put("tvbid",tVoucherExp.getTVId());
					map.put("result","");
					tVoucherExpService.deleteTvourcher(map);
					resultString=map.get("result").toString();
				}catch(Exception e){
					resultString="2";
					e.printStackTrace();
				}
			/*System.out.println(resultString);
			//删除凭证表和凭证摘要表的信息
			tVoucherExpService.deletebytvid(tVoucherExp.getTVId());
			tVoucherService.deletebyid(tVoucherExp.getTVId());*/
			//修改字号-1
			if("1".equals(resultString)){
				try{
				deleteAndupdate(tVoucher);
				TVoucher tVoucher1=new TVoucher();
				tVoucher1.setFdbid(tVoucher.getFdbid());
				}catch(Exception e){
					resultString="2";
					e.printStackTrace();
				}
			}
			TVoucher tVoucher1=new TVoucher();
			tVoucher1.setFdbid(tVoucher.getFdbid());
			return periodShow(model,tVoucher1,session);
		}else{
			return "";
		}		
		//return periodAjax(model,tVoucher1,session);
		//return resultString;
	}
	/**
	 * 反结账功能
	 * @param tVoucherExp
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "tvoucherFan")
	@ResponseBody
	public String tvoucherFan(TVoucherExp tVoucherExp,HttpServletRequest request,HttpSession session){
		TCustomer tCustomer=(TCustomer) session.getAttribute("sessionCustomer");
		System.out.println(request.getParameter("fdbid"));
		//TVoucher tVoucher=new TVoucher();
		String curr=tCustomerService.get(request.getParameter("fdbid")).getCurrentperiod();
		//当前账期、客户id
		String listtouver= tBalanceService.selectPeriod(tCustomer.getId(), curr);
		/*List<TVoucher> listtouver=selectTvoucher(curr,request.getParameter("fdbid"));*/
		Map<String, Object> map=new HashMap<String, Object>();
		//客户id
		map.put("fcustomerid", request.getParameter("fdbid"));
		//当前账期
		map.put("currentperiod", curr);
		System.out.println("当前账期"+curr);
		System.out.println("最新账期"+tCustomer.getLatelyperiod());
		
		
		//当前账期的前一期
		map.put("beforeperiod", listtouver);
		map.put("latelyperiod", tCustomer.getLatelyperiod());
		System.out.println("最近账期："+tCustomer.getLatelyperiod());
		map.put("result", "");
		Map<String, Object> result=tVoucherExpService.backCheckout(map);
		String resultString=map.get("result").toString();
		tCustomer.setCurrentperiod(listtouver);
		System.out.println(resultString);
		return "1";
	}

}