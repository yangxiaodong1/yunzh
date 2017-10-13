/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clientfile.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.clientfile.entity.TClientFile;
import com.thinkgem.jeesite.modules.clientfile.dao.TClientFileDao;

/**
 * 添加图片Service
 * @author yang
 * @version 2016-03-12
 */
@Service
@Transactional(readOnly = true)
public class TClientFileService extends CrudService<TClientFileDao, TClientFile> {

	public TClientFile get(String id) {
		return super.get(id);
	}
	
	public List<TClientFile> findList(TClientFile tClientFile) {
		return super.findList(tClientFile);
	}
	
	public Page<TClientFile> findPage(Page<TClientFile> page, TClientFile tClientFile) {
		return super.findPage(page, tClientFile);
	}
	
	@Transactional(readOnly = false)
	public void save(TClientFile tClientFile) {
		super.save(tClientFile);
	}
	
	@Transactional(readOnly = false)
	public void delete(TClientFile tClientFile) {
		super.delete(tClientFile);
	}
	
}