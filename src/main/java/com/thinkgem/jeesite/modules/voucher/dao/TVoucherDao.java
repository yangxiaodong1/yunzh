/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucher.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;

/**
 * 记账凭证DAO接口
 * @author 记账凭证
 * @version 2015-10-30
 */
@MyBatisDao
public interface TVoucherDao extends CrudDao<TVoucher> {
	/**
	 * 获取当前期凭证数
	 * @param accountPeriod
	 * @return
	 */
	public int getCurrentVoucherCount(@Param("accountPeriod")String accountPeriod);
	
	/**
	 * 某一期某一凭证号凭证的数量
	 * @param tVoucher
	 * @return
	 */
	public int getByBean(TVoucher tVoucher);
	
	/**
	 * 获取某一期凭证字号最大值
	 * @return
	 */
	public int	getMaxByAccountPeriod(@Param("accountPeriod")String accountPeriod,@Param("company")String company);
	
	/**
	 * 保存凭证
	 */
	public int insert(TVoucher tVoucher);
	
	/**
	 *根据客户ID、当前账期获取（多条件查询）（cy）
	 * */
	public List<TVoucher> findShowList(TVoucher tVoucher);
	
	/**
	 * 获取所有的账期(不会出现重复的账期)（cy）
	 * */
	public List<TVoucher> finperiods(String fdbid);
	
	/**
	 * 根据id删除（cy）
	 * */
	public void deletebyid(String id);
	
	/**
	 * 获取所有（cy）
	 * */
	public List<TVoucher> findAllList(TVoucher tVoucher);
	
	/**
	 * 删除的时候，修改比它大的记账凭证(cy)
	 * */
	public void updateanddelete(TVoucher tVoucher);
	
	/**
	 * 判断是否第一次登陆
	 * @param fdbid
	 * @return
	 */
	public int firstLoad(@Param("fdbid")String fdbid);
	
	
	/**
	 * 配合实现单张打印
	 * **/
	public List<TVoucher> findShowThree(TVoucher tVoucher);
	
	/**
	 * 查询当前账期最近的一条数据
	 * **/
	public List<TVoucher> selectPeriod(TVoucher tVoucher);
	
	/**
	 * 修改批注信息
	 * **/
	public void updatevoucher(TVoucher tVoucher);

	/**
	 * 插入凭证前移动之后的 凭证字号
	 * @param fdbid
	 * @param accountPeriod
	 * @param voucherTitleNumber
	 * @return
	 */
	public int insertVoucher(@Param("fdbid")String fdbid,@Param("accountPeriod")String accountPeriod,@Param("voucherTitleNumber")String voucherTitleNumber);
}