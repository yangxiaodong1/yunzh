/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.web.setup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sysnews.entity.Tsysnews;
import com.thinkgem.jeesite.modules.sysnews.service.TsysnewsService;

/**
 * 
 * @author 	
 * @version 2015-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/inspection/setup/sysMessage")
public class SysMessageController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private TsysnewsService tsysnewsService;
	
	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getUser(id);
		} else {
			return new User();
		}
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Tsysnews tsysnews, HttpServletRequest request,HttpServletResponse response, Model model) {
		List<Tsysnews> list = tsysnewsService.findListAll(tsysnews);
		model.addAttribute("tsysNewsList", list);
		return "modules/inspection/setup/sysMessageList";
	}
	@RequestMapping(value = {"findListAll"})
	public String findListAll(Tsysnews tsysnews, HttpServletRequest request,HttpServletResponse response, Model model) {
		List<Tsysnews> list = tsysnewsService.findListAll(tsysnews);
		model.addAttribute("tsysNewsList", list);
		return "modules/inspection/setup/tNewUserAllList";
	}
	@RequestMapping(value = {"findListByUserId"})
	public String findListByUserId(Tsysnews tsysnews, HttpServletRequest request,HttpServletResponse response, Model model) {
		List<Tsysnews> list = tsysnewsService.findListByUserId(tsysnews);
		if(list.size()>0)
			tsysnewsService.insertNewUser(list);
		model.addAttribute("tsysNewsList", list);
		return "modules/inspection/setup/tNewUserList";
	}
}