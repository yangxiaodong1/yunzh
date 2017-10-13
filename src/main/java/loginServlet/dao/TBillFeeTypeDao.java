package loginServlet.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import loginServlet.entity.TBillFeeType;

import loginServlet.jdbcUtils.JdbcUtil;

public class TBillFeeTypeDao {

	
	public List<TBillFeeType> getBillFeeTypes(){
		TBillFeeType tbft=null;
		List<TBillFeeType> tList=new ArrayList<TBillFeeType>();
		Connection conn=JdbcUtil.getConnection();
		String sql=" SELECT	id,bill_fee_type_name,level,detail,parent_id FROM t_bill_fee_type ORDER BY id+0";
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				tbft=new TBillFeeType();
				tbft.setId(rs.getString(1));
				tbft.setBillFeeTypeName(rs.getString(2));
				tbft.setLevel(rs.getString(3));
				tbft.setDetail(rs.getString(4).trim());
				tbft.setParent(rs.getString(5));
				tList.add(tbft);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JdbcUtil.close();
		
		return tList;
	}
	
}
