/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.backupandrecover.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.backupandrecover.entity.TbackupRecover;
import com.thinkgem.jeesite.modules.backupandrecover.dao.TbackupRecoverDao;

/**
 * 备份与回复Service
 * @author 备份与回复
 * @version 2016-02-03
 */
@Service
@Transactional(readOnly = true)
public class TbackupRecoverService extends CrudService<TbackupRecoverDao, TbackupRecover> {

	public TbackupRecover get(String id) {
		return super.get(id);
	}
	
	public List<TbackupRecover> findList(TbackupRecover tbackupRecover) {
		return super.findList(tbackupRecover);
	}
	
	public Page<TbackupRecover> findPage(Page<TbackupRecover> page, TbackupRecover tbackupRecover) {
		return super.findPage(page, tbackupRecover);
	}
	
	@Transactional(readOnly = false)
	public void save(TbackupRecover tbackupRecover) {
		super.save(tbackupRecover);
	}
	@Transactional(readOnly = false)
	public Map<String, Object> tbackuprecover(Map<String, Object> map) {
		return dao.tbackuprecover(map);
	}
	@Transactional(readOnly = false)
	public void delete(TbackupRecover tbackupRecover) {
		super.delete(tbackupRecover);
	}
	public  List<TbackupRecover> findListbyfdbid(@Param(value="fdbid")String fdbid){
		return dao.findListbyfdbid(fdbid);
	}
	public static void createFileFile(String backupNumber){
		//path表示你所创建文件的路径
		String path = Global.getBackupandrecoverPath()+backupNumber;
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		} 
		
	}	
	/**
	* @desc 将源文件/文件夹生成指定格式的压缩文件,格式zip
	* @param resourePath 源文件/文件夹
	* @param targetPath  目的压缩文件保存路径
	* @return void
	* @throws Exception 
	*/
	public void compressedFile(String resourcesPath,String targetPath) throws Exception{
        File resourcesFile = new File(resourcesPath);     //源文件
        File targetFile = new File(targetPath);           //目的
        //如果目的路径不存在，则新建
        if(!targetFile.exists()){     
            return;
        }
        
        String targetName = resourcesFile.getName()+".zip";   //目的压缩文件名
        FileOutputStream outputStream = new FileOutputStream(targetPath+"\\"+targetName);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));
        
        createCompressedFile(out, resourcesFile, "");
        out.close();  
    }
	  public void createCompressedFile(ZipOutputStream out,File file,String dir) throws Exception{
	        //如果当前的是文件夹，则进行进一步处理
	        if(file.isDirectory()){
	            //得到文件列表信息
	            File[] files = file.listFiles();
	            //将文件夹添加到下一级打包目录
	            out.putNextEntry(new ZipEntry(dir+"/"));
	            dir = dir.length() == 0 ? "" : dir +"/";
	            
	            //循环将文件夹中的文件打包
	            for(int i = 0 ; i < files.length ; i++){
	                createCompressedFile(out, files[i], dir + files[i].getName());         //递归处理
	            }
	        }
	        else{   //当前的是文件，打包处理
	            //文件输入流
	            FileInputStream fis = new FileInputStream(file);
	            
	            out.putNextEntry(new ZipEntry(dir));
	            //进行写操作
	            int j =  0;
	            byte[] buffer = new byte[1024];
	            while((j = fis.read(buffer)) > 0){
	                out.write(buffer,0,j);
	            }
	            //关闭输入流
	            fis.close();
	        }
	    }
	
} 
