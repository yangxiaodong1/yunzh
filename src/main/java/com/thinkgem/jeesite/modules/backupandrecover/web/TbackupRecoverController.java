/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.backupandrecover.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.zxing.Writer;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.backupandrecover.entity.TbackupRecover;
import com.thinkgem.jeesite.modules.backupandrecover.service.TbackupRecoverService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;

/**
 * 备份与回复Controller
 * @author 备份与回复
 * @version 2016-02-03
 */
@Controller
@RequestMapping(value = "${adminPath}/backupandrecover/tbackupRecover")
public class TbackupRecoverController extends BaseController {

	@Autowired
	private TbackupRecoverService tbackupRecoverService;
	
	@ModelAttribute
	public TbackupRecover get(@RequestParam(required=false) String id) {
		TbackupRecover entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tbackupRecoverService.get(id);
		}
		if (entity == null){
			entity = new TbackupRecover();
		}
		return entity;
	}	
	//@RequiresPermissions("backupandrecover:tbackupRecover:view")
	@RequestMapping(value = {"list", ""})
	public String list(HttpSession session, TbackupRecover tbackupRecover, HttpServletRequest request, HttpServletResponse response, Model model) {
		TCustomer customerObject = (TCustomer) session.getAttribute("sessionCustomer");
		List list=tbackupRecoverService.findListbyfdbid(customerObject.getId());
		model.addAttribute("backup", list);
		return "modules/backupandrecover/tbackupRecoverList";
	}

	//@RequiresPermissions("backupandrecover:tbackupRecover:view")
	@RequestMapping(value = "form")
	public String form(TbackupRecover tbackupRecover, Model model) {
		model.addAttribute("tbackupRecover", tbackupRecover);
		return "modules/backupandrecover/tbackupRecoverForm";
	}

	//@RequiresPermissions("backupandrecover:tbackupRecover:edit")
	@RequestMapping(value = "save")
	public String save(TbackupRecover tbackupRecover, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tbackupRecover)){
			return form(tbackupRecover, model);
		}
		tbackupRecoverService.save(tbackupRecover);
		addMessage(redirectAttributes, "保存备份与回复成功");
		return "redirect:"+Global.getAdminPath()+"/backupandrecover/tbackupRecover/?repage";
	}
	 /**
     * 删除空目录
     * @param dir 将要删除的目录路径
     */
    private void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
	//@RequiresPermissions("backupandrecover:tbackupRecover:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(TbackupRecover tbackupRecover,String backupNumber, RedirectAttributes redirectAttributes) {
		tbackupRecoverService.delete(tbackupRecover);
		addMessage(redirectAttributes, "删除备份与回复成功");
		
		boolean flag = false;
    	/*String sPath="d:/tbackuprecover/";*/
		String sPath=Global.getBackupandrecoverPath();
    	
    	
    	
    	//跨平台的时候，考虑File.separator，相当于/
        File file = new File(sPath + File.separator + backupNumber+".zip");
        File files = new File(sPath + File.separator + backupNumber);
        System.out.println(file.getPath());
        boolean success = deleteDir(new File(files.getPath()));
        
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }		
		return "1";
	}  
	@RequestMapping(value = "tbackuprecoveradd")
	public void tbackuprecoveradd(PrintWriter printWriter,HttpSession session, RedirectAttributes redirectAttributes) throws IOException {
		TCustomer customerObject = (TCustomer) session.getAttribute("sessionCustomer");
		//创建文件夹
		String backnumber=UUID.randomUUID().toString().replaceAll("-", "");
		
		System.out.println(backnumber);
		tbackupRecoverService.createFileFile(backnumber);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("ffdbid",customerObject.getId());
		map.put("backupNumber",backnumber);
		map.put("rtn", "");
		Map<String, Object> map2=tbackupRecoverService.tbackuprecover(map);
		String result=map.get("rtn").toString();
		//压缩文件加
		try {
			tbackupRecoverService.compressedFile(Global.getBackupandrecoverPath()+backnumber,"d:/tbackuprecover/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		InputStream stream = new FileInputStream(Global.getBackupandrecoverPath()+backnumber+".zip");
		long size=stream.available();
		String sizefile="";
		BigDecimal ld=new BigDecimal(size);
		ld=ld.divide(new BigDecimal("1024"),0,BigDecimal.ROUND_HALF_UP);
		ld=ld.add(new BigDecimal("1"));
		sizefile=ld+"KB";
		if(Double.parseDouble(ld.toString())>=1024){
			ld=ld.divide(new BigDecimal("1024"),0,BigDecimal.ROUND_HALF_UP);
			ld=ld.add(new BigDecimal("1"));
			sizefile=ld+"MB";
			if(Double.parseDouble(ld.toString())>=1024){
				ld=ld.divide(new BigDecimal("1024"),2,BigDecimal.ROUND_HALF_UP);
				sizefile=ld+"G";
			}
		}
		stream.close();
		TbackupRecover tbackupRecover=new TbackupRecover();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		System.out.println(time);
		/*System.out.println(time+format.parse(time));*/
		/*try {
			tbackupRecover.setBackupDate(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		tbackupRecover.setBackupDate(time);
		tbackupRecover.setIsNewRecord(true);
		tbackupRecover.setBackupName(backnumber+".zip");
		tbackupRecover.setBackupNumber(backnumber);
		tbackupRecover.setFileSize(sizefile);
		tbackupRecover.setFdbid(customerObject.getId());
		tbackupRecoverService.save(tbackupRecover);
		String jsonString = "";
		JsonMapper json = new JsonMapper();
		if (tbackupRecover != null) {
			jsonString = json.toJsonString(tbackupRecover);
		}
		printWriter.write(jsonString);
		printWriter.flush();
		printWriter.close();
	}	
	/**
	 * 文件的下载方法
	 * @throws IOException 
	 * **/
	@RequestMapping(value = "downFile")
	public String  downFile(String fileName,HttpServletResponse response) throws IOException{
		 
		 response.setCharacterEncoding("utf-8");
	     response.setContentType("multipart/form-data");
	     //添加头文件，协议的扩展
	     response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
	     
	     try {
	    	 	//这个download目录为啥建立在classes下的,---------线程获取当前项目的编译路径+download
	            String path = Global.getBackupandrecoverPath();
	            //Thread.currentThread().getContextClassLoader().getResource("").getPath()+ "download";	            
	            System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
	            
	            //如果考虑跨平台，如：C:\tmp\test.txt  ==  "C:" + File.separator + "tmp" + File.separator, "test.txt"
	            InputStream inputStream = new FileInputStream(new File(path + File.separator + fileName));
	            
	            //定义一个输出流
	            OutputStream os = response.getOutputStream();
	            
	            //创建byte变量，用于对流一个一个字节的读，返回的int就是这个字节的int表达方式（以整数形式返回实际读取的字节数）。
	            byte[] b = new byte[2048];
	            
	            //定义一个长度
	            int length;
	            
	            //判断输入流的长度
	            while ((length = inputStream.read(b)) > 0) {
	            	//将指定 byte 数组中从偏移量 0 开始的 length 个字节写入此输出流
	                os.write(b, 0, length);
	            }
	            
	            // 这里主要输出关闭。
	            os.close();
	            //这里输入流关闭
	            inputStream.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	     //返回值要注意，要不然就出现下面这句错误！
         //java+getOutputStream() has already been called for this response
	     return null;
	}
}