package loginServlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



import loginServlet.entity.TBillInfo;
import loginServlet.jdbcUtils.JdbcUtil;

public class TBillInfoDao {
     
	public List<TBillInfo> findByComIdStatus(String customer_id,String status){
		
		Connection conn=null;
		List<TBillInfo> tBillInfoList=new ArrayList<TBillInfo>();
		String sql="select pay_name,sign_date,image_url,image_size from t_bill_info where customer_id=? and bill_status=?";
		conn=JdbcUtil.getConnection();
		try {
			PreparedStatement ps= conn.prepareStatement(sql);
			ps.setString(1, customer_id);
			ps.setString(2, "1");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				TBillInfo tb=new TBillInfo();
				tb.setImgName(rs.getString(1));
				tb.setDt(rs.getString(2));
				//tb.setDt(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.f").format(rs.getString(2)));
				//new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(rs.getDate(7))
				//tb.setDt(new SimpleDateFormat("yyyy-MM-dd").format(rs.getString(2)));
				tb.setType(rs.getString(3).substring(rs.getString(3).indexOf(".")+1));
				tb.setSize(rs.getString(4));
				tBillInfoList.add(tb);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JdbcUtil.close();
		return tBillInfoList;
		
	}
	// 保存刘连军票据的接口dao方法
	public void saveBillInfo(TBillInfo tbifo){
		
		Connection conn=null;
		//List<TBillInfo> tBillInfoList=new ArrayList<TBillInfo>();
		String sql="insert into t_bill_info (upload_period,image_name,abstract_msg,total_price,amount,customer_id,remarks,bill_status,is_expire,create_by,create_date,sign_date,t_b_id) values(?,?,?,?,?,?,?,?,?,?,now(),now(),'1')";
		conn=JdbcUtil.getConnection();
		try {
			PreparedStatement ps= conn.prepareStatement(sql);
			ps.setString(1, tbifo.getUpload_period());
			ps.setString(2, tbifo.getImage_url());
			ps.setString(3, tbifo.getAbstract_msg());
			ps.setString(4, tbifo.getTotal_price());
			ps.setString(5, tbifo.getTotal_price());
			ps.setString(6, tbifo.getCustomer_id());
			ps.setString(7, tbifo.getRemarks());
			//ps.setString(8, tbifo.getId());
			ps.setString(8, "2");
			ps.setString(9, "0");
			ps.setString(10, tbifo.getUser_loginname());
			//ps.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
			//ps.setDate(11, Calendar.getInstance().getTime());
			//ResultSet rs = ps.executeQuery();
			ps.executeUpdate();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JdbcUtil.close();
		//return tBillInfoList;
		
	}	
	
public String  find(){
		
		Connection conn=null;
		//List<TBillInfo> tBillInfoList=new ArrayList<TBillInfo>();
		String sql="select  NEXTVAL('billID')";
		conn=JdbcUtil.getConnection();
		String kString="";
		try {
			PreparedStatement ps= conn.prepareStatement(sql);
		
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				kString=rs.getString(1);
				
			}
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JdbcUtil.close();
		return kString;
		
	}	
	
}
