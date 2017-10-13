package loginServlet.jdbcUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.thinkgem.jeesite.common.utils.PropertiesLoader;

public class JdbcUtil {

	// public static final String url =
	// "jdbc:mysql://localhost:3306/jeesite?useUnicode=true&characterEncoding=utf-8";
	// public static final String name = "com.mysql.jdbc.Driver";
	// public static final String user = "root";
	// public static final String password = "root";

	public static Connection conn = null;
	
	/**
	 * 属性文件加载对象
	 */
	//public static PropertiesLoader loader = new PropertiesLoader("jeesite.properties");

	public static Connection getConnection() {
		try {
			/*
			 * String path = null; try { path =
			 * Object.class.getClassLoader().getResource("").toURI().getPath();
			 * System.out.println(path+"路径"); } catch (URISyntaxException e1) {
			 * }
			 * 
			 * Properties prop = new Properties(); FileInputStream fis= null;
			 * try { fis = new FileInputStream(new
			 * File("/resources/jeesite.properties")); } catch (Exception e) {
			 * fis = new FileInputStream(new
			 * File(path+"resources/jeesite.properties")); }
			 */
			PropertiesLoader loader = new PropertiesLoader("jeesite.properties");

			/*
			Properties prop = new Properties();
			
			InputStream in = Object.class
					.getResourceAsStream("jeesite.properties");
			prop.load(in);
			 */

			String urll = loader.getProperty("jdbc.url");
			System.out.println("urll:" +urll);
			String driver = loader.getProperty("jdbc.driver");
			String userr = loader.getProperty("jdbc.username");
			String passwordd = loader.getProperty("jdbc.password");
			// System.out.println(fis +"是空的吗");
			Class.forName(driver);// 指定连接类型
			// conn = DriverManager.getConnection(url, user, password);//获取连接
			conn = DriverManager.getConnection(urll, userr, passwordd);// 获取连接
			return conn;
		} catch (Exception e) {
			throw new RuntimeException("链接错误");
			// e.printStackTrace();
		}
	}

	public static void close() {
		try {

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection c = JdbcUtil.getConnection();// 接受返回的对象，connection相当于一个接口
		if (c != null) {
			System.out.println("链接数据库成功");
		}
	}

}
