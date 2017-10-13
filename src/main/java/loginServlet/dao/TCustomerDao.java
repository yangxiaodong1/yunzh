package loginServlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import loginServlet.entity.TCustomer;
import loginServlet.jdbcUtils.JdbcUtil;

public class TCustomerDao {

	
	public List<TCustomer> findById(String id){
		TCustomer tc=null;
		List<TCustomer> tList=new ArrayList<TCustomer>();
		Connection conn=JdbcUtil.getConnection();
		String sql=" select id,customer_name,code,current_period,upload_sum from t_customer where supervisor=? and current_period is not null";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				tc=new TCustomer();
				tc.setId(rs.getString(1));
				tc.setName(rs.getString(2));
				tc.setCode(rs.getString(1));
				tc.setDtTime(rs.getString(4).trim());
				tc.setNum(rs.getString(5));
				tList.add(tc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JdbcUtil.close();
		
		return tList;
	}
	
}
