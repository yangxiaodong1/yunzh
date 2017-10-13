/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.web.workstatistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TCompanyYmc;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TWorkGather;
import com.thinkgem.jeesite.modules.inspection.service.workstatistics.TCompanyYmcService;
import com.thinkgem.jeesite.modules.inspection.service.workstatistics.TWorkGatherService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 工作量汇总表Controller
 * @author xuxiaolong
 * @version 2015-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/inspection/workstatistics/tWorkGather")
public class TWorkGatherController extends BaseController {

	@Autowired
	private TWorkGatherService tWorkGatherService;
	
	@Autowired
	private TCompanyYmcService tCompanyYmcService;
	
	@ModelAttribute
	public TWorkGather get(@RequestParam(required=false) String id) {
		TWorkGather entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tWorkGatherService.get(id);
		}
		if (entity == null){
			entity = new TWorkGather();
		}
		return entity;
	}
	
	//@RequiresPermissions("inspection:workstatistics:tWorkGather:view")
	@RequestMapping(value = {"list", ""})
	public String list(String ByYear, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		TWorkGather tWorkGather=new TWorkGather();
		if("".equals(ByYear)||null==ByYear){
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			 ByYear = formatter.format(new Date());
		}
		String CompanyId=UserUtils.getUser().getCompany().getId();
		tWorkGather.setByYear(ByYear);
		tWorkGather.setCompanyId(CompanyId);
		
		TCompanyYmc maxTCompanyYmc =tCompanyYmcService.MaxTCompanyYmcByCompanyId(CompanyId);
		String nowtimeString=String.valueOf(Calendar.getInstance().get(Calendar.YEAR))
				+String.valueOf(Calendar.getInstance().get(Calendar.MONTH)<9 ? 
						"0"+(Calendar.getInstance().get(Calendar.MONTH)+1) : 
							(Calendar.getInstance().get(Calendar.MONTH)+1)
							);
		if(null!=maxTCompanyYmc&& Integer.parseInt(maxTCompanyYmc.getYmonth())<Integer.parseInt(nowtimeString)){
			List<String> getMonthBetween=this.getMonthBetween(maxTCompanyYmc.getYmonth(),nowtimeString);
			List<TCompanyYmc> listtCompanyYmc=new ArrayList<TCompanyYmc>();
			getMonthBetween.remove(0);
			int ymountCount=maxTCompanyYmc.getYmonthCount();
			for(String ymonthString : getMonthBetween){
				TCompanyYmc tCompanyYmc1 = new TCompanyYmc();
				tCompanyYmc1.setCompanyId(CompanyId);
				tCompanyYmc1.setYmonth(ymonthString);
				tCompanyYmc1.setYmonthCount(ymountCount);
				
				listtCompanyYmc.add(tCompanyYmc1);
			}
			tCompanyYmcService.insertlist(listtCompanyYmc);
		}
		String[] strString=new String[]{"'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'"};
		if(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).equals(ByYear))
			strString=Arrays.copyOfRange(strString,0,Calendar.getInstance().get(Calendar.MONTH)+1);
		String ss=","+StringUtils.join(strString, ",")+",";
		if(Integer.parseInt(ByYear)>Calendar.getInstance().get(Calendar.YEAR)){
			ss=null;
			tWorkGather.setNotnull("1");
		}
		TWorkGather tWorkGather1=tWorkGatherService.tWorkGather1(tWorkGather);
		try {
			tWorkGather.setCustomerSum(getMouthCount(ByYear,tWorkGather1.getYmonths(),tWorkGather1.getCustomerSum()));
		} catch (Exception e) {
			tWorkGather.setCustomerSum(ss);
		}
		TWorkGather tWorkGather2=tWorkGatherService.tWorkGather2(tWorkGather);
		try {
			tWorkGather.setBillSum(getMouthCount(ByYear,tWorkGather2.getYmonths(),tWorkGather2.getBillSum()));
		} catch (Exception e) {
			tWorkGather.setBillSum(ss);
		}
		TWorkGather tWorkGather3=tWorkGatherService.tWorkGather3(tWorkGather);
		try {
			tWorkGather.setVoucherSum(getMouthCount(ByYear,tWorkGather3.getYmonths(),tWorkGather3.getVoucherSum()));
		} catch (Exception e) {
			tWorkGather.setVoucherSum(ss);
		}
		model.addAttribute("tWorkGather",tWorkGather);
		
//		List<TWorkGather> listTWorkGather=tWorkGatherService.findList(tWorkGather);
//		if(listTWorkGather.size()<1){
//			TWorkGather twg1=new TWorkGather();
//			twg1.setByYear(ByYear);
//			listTWorkGather.add(twg1);
//		}
//		model.addAttribute("tWorkGather",listTWorkGather.get(0));
		
		return "modules/inspection/workstatistics/tWorkGatherList";
	}

	//@RequiresPermissions("inspection:workstatistics:tWorkGather:view")
	@RequestMapping(value = "form")
	public String form(TWorkGather tWorkGather, Model model) {
		model.addAttribute("tWorkGather", tWorkGather);
		return "modules/inspection/workstatistics/tWorkGatherForm";
	}

	//@RequiresPermissions("inspection:workstatistics:tWorkGather:edit")
	@RequestMapping(value = "save")
	public String save(TWorkGather tWorkGather, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tWorkGather)){
			return form(tWorkGather, model);
		}
		tWorkGatherService.save(tWorkGather);
		addMessage(redirectAttributes, "保存工作量汇总表成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tWorkGather/?repage";
	}
	
	//@RequiresPermissions("inspection:workstatistics:tWorkGather:edit")
	@RequestMapping(value = "delete")
	public String delete(TWorkGather tWorkGather, RedirectAttributes redirectAttributes) {
		tWorkGatherService.delete(tWorkGather);
		addMessage(redirectAttributes, "删除工作量汇总表成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tWorkGather/?repage";
	}
	public String getMouthCount(String ByYear,String str1,String str2){
		String[] strString=new String[]{"'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'"};
		if(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).equals(ByYear))
			strString=Arrays.copyOfRange(strString,0,Calendar.getInstance().get(Calendar.MONTH)+1);
		String[] strArray1 = str1.split(",");
		int[] numMouth = new int[strArray1.length];
		for (int i = 0; i < strArray1.length; i++) {  
			numMouth[i] = Integer.parseInt(strArray1[i]);  
		}
		String[] strArray2 = str2.split(",");
		
		int j = 0;
		for(int i : numMouth){
			for (; j < strArray2.length; ) {  
				strString[i-1]=strArray2[j];  
				j++;
				break;
			} 
		}
		return ","+StringUtils.join(strString, ",")+",";
	}
	private static  List<String> getMonthBetween(String minDate, String maxDate) throws ParseException  {
	    ArrayList<String> result = new ArrayList<String>();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月
	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();
	    min.setTime(sdf.parse(minDate));
	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
	    max.setTime(sdf.parse(maxDate));
	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
	    Calendar curr = min;
	    while (curr.before(max)) {
	     result.add(sdf.format(curr.getTime()));
	     curr.add(Calendar.MONTH, 1);
	    }
	    return result;
	  }

}