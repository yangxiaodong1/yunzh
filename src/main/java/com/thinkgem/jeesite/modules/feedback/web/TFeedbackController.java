/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.feedback.entity.TFeedback;
import com.thinkgem.jeesite.modules.feedback.entity.TFeedbackFile;
import com.thinkgem.jeesite.modules.feedback.service.TFeedbackFileService;
import com.thinkgem.jeesite.modules.feedback.service.TFeedbackService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 意见反馈Controller
 * @author rongjd
 * @version 2016-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/feedback")
public class TFeedbackController extends BaseController {
	
	@Autowired
	private TFeedbackService tFeedbackService;
	@Autowired
	private TFeedbackFileService tFeedbackFileService;
	@Autowired
	private SystemService systemService;//用户的service
	
	@ModelAttribute
	public TFeedback get(@RequestParam(required=false) String id) {
		TFeedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tFeedbackService.get(id);
		}
		if (entity == null){
			entity = new TFeedback();
		}
		return entity;
	}
	/**
	 * 管理员意见反馈回复
	 * @param tFeedback
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"back", ""})
	public String feedback(TFeedback tFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		this.getFeedBackList(tFeedback, request, model);
		
		return "modules/adminback/feedback";
	}
	
	/**
	 * 
	 * 客户意见提交
	 * @param tFeedback
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(TFeedback tFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		this.getFeedBackList(tFeedback, request, model);
		User currentuser=UserUtils.getUser();
		model.addAttribute("currentuser",currentuser);
		return "modules/feedback/tFeedbackList";
	}
	
	@RequiresPermissions("feedback:tFeedback:view")
	@RequestMapping(value = "form")
	public String form(TFeedback tFeedback, Model model) {
		model.addAttribute("tFeedback", tFeedback);
		return "modules/feedback/tFeedbackForm";
	}

//	@RequiresPermissions("feedback:tFeedback:edit")
	@RequestMapping(value = "save")
	public String save(TFeedback tFeedback, Model model, RedirectAttributes redirectAttributes) {
		tFeedbackService.save(tFeedback);
		addMessage(redirectAttributes, "意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/feedback/list?repage";
	}
	@RequestMapping(value = "update")
	public String update(TFeedback tFeedback, Model model, RedirectAttributes redirectAttributes) {
		tFeedbackService.save(tFeedback);
		addMessage(redirectAttributes, "意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/feedback/back/?repage";
	}
	
	@RequiresPermissions("feedback:tFeedback:edit")
	@RequestMapping(value = "delete")
	public String delete(TFeedback tFeedback, RedirectAttributes redirectAttributes) {
		tFeedbackService.delete(tFeedback);
		addMessage(redirectAttributes, "删除意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/feedback/tFeedback/?repage";
	}
	/**
	 * 文件的上传方法，cy
	 * **/
	@RequestMapping(value = "fileUpload")
	@ResponseBody
	public String upload(HttpServletRequest request){
		StringBuffer result = new StringBuffer();
		MultipartHttpServletRequest  mRequest = (MultipartHttpServletRequest)(request);  
		//解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//先判断request中是否包涵multipart类型的数据，
		if(multipartResolver.isMultipart(request)){
			//再将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			 //迭代器
			Iterator<?> iter = multiRequest.getFileNames();
			StringBuffer sb = new StringBuffer();
			while(iter.hasNext()){
				MultipartFile file = multiRequest.getFile((String)iter.next());
				Map<String,Object> map = FileUtils.upload(file, Global.getFeedBackFilePath(), mRequest);
				if(!map.isEmpty()){
					TFeedbackFile f = new TFeedbackFile();
					f.setFileName(map.get("newName").toString());
					f.setOldName(map.get("oldName").toString());
					tFeedbackFileService.save(f);
					sb.append("{\"id\":\"").append(f.getId());
					sb.append("\",\"name\":\"").append(f.getFileName()).append("\"},");
				}
			}
			result.append("{\"state\":200");
			if(sb.length()>0){
				String d = sb.substring(0, sb.length()-1);
				result.append(",\"data\":[").append(d).append("]}");
			}else{
				result.append(",\"data\":[]}");
			}
		}else{
			result.append("{\"state\":250,\"msg\":\"请选择上传文件。\"}");
		}
		return result.toString();
	}
	@RequestMapping(value = "/getFile")
	public void imageGet(String fileName,HttpServletResponse response){
        FileInputStream fis = null;
        response.setContentType("image/gif");
        try {
            OutputStream out = response.getOutputStream();
            File file = new File(Global.getFeedBackFilePath()+File.separator+fileName);
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	    
	private void getFeedBackList(TFeedback tFeedback, HttpServletRequest request,Model model){
		List<TFeedback> findList = tFeedbackService.findList(tFeedback);
		for(int i=0;i<findList.size();i++){
			TFeedback feed = findList.get(i);
			String ids = feed.getFeedFiles();
			if(ids != null && !"".equals(ids)){
				String[] temp = ids.split(",");
				for(int j=0;j<temp.length;j++){
					TFeedbackFile f = tFeedbackFileService.get(temp[j]);
					temp[j] = f.getFileName();
				}
				feed.setImages(temp);
			}
			feed.setCreateBy(systemService.getUser(feed.getCreateBy().getId()));
			feed.setUpdateBy(systemService.getUser(feed.getUpdateBy().getId()));
		}
		User user = tFeedback.getCurrentUser();
		model.addAttribute("list",findList);
		model.addAttribute("user",user);
	}
}