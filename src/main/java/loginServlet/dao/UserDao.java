package loginServlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import loginServlet.entity.SysUser;
import loginServlet.jdbcUtils.JdbcUtil;

public class UserDao {
	public SysUser findByNamePwd(String userName,String password){
		SysUser com=null;
		
		Connection conn=JdbcUtil.getConnection();
		String sql="select  id,name,phone,photo,email from sys_user where login_name=? and password=?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				 com=new SysUser();
				 com.setId(rs.getString(1));
				 com.setUserName(rs.getString(2));
				com.setPhone(rs.getString(3));
				com.setPhotoUrl(rs.getString(4));
		         com.setEmail(rs.getString(5));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JdbcUtil.close();
		return com;
	}
	
	public String selectGetPwdByName(String userName){
		Connection conn=JdbcUtil.getConnection();
		String sql="select password from sys_user where login_name=?";
		String dbPwd = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				dbPwd = rs.getString("password");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JdbcUtil.close();
		return dbPwd;
	}
}
