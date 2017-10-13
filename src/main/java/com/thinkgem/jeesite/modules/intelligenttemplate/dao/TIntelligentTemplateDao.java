/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.intelligenttemplate.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.intelligenttemplate.entity.TIntelligentTemplate;

/**
 * intelligenttemplateDAO接口
 * @author intelligenttemplate
 * @version 2015-12-07
 */
@MyBatisDao
public interface TIntelligentTemplateDao extends CrudDao<TIntelligentTemplate> {
	
}