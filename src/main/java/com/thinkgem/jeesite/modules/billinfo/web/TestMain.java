package com.thinkgem.jeesite.modules.billinfo.web;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class TestMain {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		
		//拼接json串 （根据 1.	票据分录接口实例拼接的json串格式） 
		JSONObject nodeInvoice = new JSONObject();
		try {
			nodeInvoice.put("invoiceID", "211001360130");
			nodeInvoice.put("invoiceType", "发票");
			nodeInvoice.put("principle", "0");
			nodeInvoice.put("taxNature", "0");
			nodeInvoice.put("companyID", "1001");
			nodeInvoice.put("companyName", "北京芸豆科技科技有限公司");
			nodeInvoice.put("summary", "餐费");
			nodeInvoice.put("money", 2218);
			nodeInvoice.put("tax", 0);
			nodeInvoice.put("total", 2218);
			nodeInvoice.put("payer", "北京芸豆科技科技有限公司");
			nodeInvoice.put("payee", "北京南国酒店管理有限公司");
			nodeInvoice.put("extra", "");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("nodeInvoice:"+nodeInvoice.toString());
		//接口路径
		String urlInvoice = "http://101.251.234.166:8081/v1/invoice/";
		//调用方法  传递json参数 访问路径
		String returnInvoice = loadJSON(urlInvoice, nodeInvoice.toString());
		System.out.println(returnInvoice);
		
		JSONObject resultJsonObject = JSONObject.fromObject(returnInvoice);
		
		System.out.println("sum"  + resultJsonObject.getString("summary"));
		
		
		//拼接json串（根据2.	分录反馈接口实例拼接json格式）
		JSONObject nodeFeedback = new JSONObject();
		try {
			nodeFeedback.put("invoiceID", "20160101001");
			nodeFeedback.put("feedbackType", "negative");
			nodeFeedback.put("summary", "办公费");
			
			JSONArray content = new JSONArray();
			JSONObject content1 = new JSONObject();
			
			content1.put("seq", 0);
			content1.put("subject", "560206");
			content1.put("debit", 100.00);
			content1.put("credit", 0);
			
			JSONObject content2 = new JSONObject();
			content2.put("seq", 0);
			content2.put("subject", "560206");
			content2.put("debit", 100.00);
			content2.put("credit", 0);
			
			content.add(content1);
			content.add(content2);
			
			nodeFeedback.put("content", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("nodeFeedback:"+nodeFeedback.toString());
		//接口路径
		String urlFeedback = "http://101.251.234.166:8081/v1/feedback/";
		//调用方法  传递json参数 访问路径
		String returnFeedback = loadJSON(urlFeedback, nodeFeedback.toString());
		System.out.println(returnFeedback);
	}

	public static String loadJSON(String addurl, String json) {
		StringBuffer sb = new StringBuffer("");
		try {
			// 创建连接
			URL url = new URL(addurl);
			//设置请求头
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 设定请求的方法为"POST"，默认是GET  
			connection.setRequestMethod("POST");
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在    
			// http正文内，因此需要设为true, 默认情况下是false; 
			connection.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;  
			connection.setDoInput(true);
			// Post 请求不能使用缓存 
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			 connection.setRequestProperty("Accept", "Application/json");
			// 设定传送的内容类型是json对象
			connection.setRequestProperty("Content-Type", " application/x-javascript");
			//设置编码格式
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.connect();

			// POST请求
			 OutputStreamWriter paramout = new
			 OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			 paramout.write(json.toString());
			 paramout.flush();

			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String lines;
			//读取返回json流
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			 paramout.close();

			reader.close();
			// 断开连接
			connection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

}
