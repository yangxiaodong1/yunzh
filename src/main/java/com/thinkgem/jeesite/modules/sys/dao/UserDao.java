/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);
	/**
	 * 验证重名
	 * */
	public int validatorLoginName(User user);
	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（查询用户列表的时候用）
	 * @param user
	 * @return
	 */
	public List<User> findByOfficeId(User user);
	public List<User> findAll();//新展示后台用户列表
	public List<User> finddj();//新展示后台用户列表yang对接人
	public List<User> selectuser(User user);
	public List<User> selectuserzNodes(User user);
	
	
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	public int updatePasswordByIdnew(User user);
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	/**
	 * 删除用户权限关系数据yang
	 * @param user
	 * @return
	 */
	public int deleteUserPower(User user);
	public int insertUserPower(User user);//插入用户权限角色
	/**
	 * 插入有记账公司id的用户yxd
	 * @param user
	 */
	public void insertchargeCompanyId(User user);
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	/**
	 * 更新用户的状态
	 * @param user
	 * @return
	 * yxd
	 */
	public int updateStatus(User user);
	public int updateuserStatus(User user);//修改是否停止使用的时候停止
	/**
	 * 获取所有的用户
	 * @return
	 * **/
	public List<User> allUserList();
	
	public List<User> findListNew(User user);

	
	/**
	 * 获取公司管理员
	 * @return
	 * **/
	public List<User> findCompanyAdmin(User user);
	
	
	/**
	 * 根据company_id获取公司的记账员集合xxl
	 * @return
	 * **/
	public List<User> findUserByCompanyid(User user);
	

	/**
	 * 根据用户id获取该用户
	 * **/
	public User findByid(String id);
	public User findByidnew(String id);//新的用id获取用户
	
	/**
	 * yang
	 * 根据用户id和主账户获取该用户信息yang
	 * @param id
	 * @return
	 */
	public User findByidandaccount(User user);
	
	public List<User> findsoncount(User user);//获取子账号个数yang
	public List<User> findcompcount(User user);
	
	/**
	 * 初始账套
	 * **/
	public String inputbasedata(Map<String, Object> map);
	/**
	 * 重新初始化
	 * **/
	public String againinputbasedata(Map<String, Object> map);

	public List<User> findListNewLeave(User user);

	public List<User> findUserListBySearch(String search, String companyId);
	
	/**
	 * 修改用户头像
	 * @param id 用户的id
	 */
	public void  updateUserPhoto(User user);
}
