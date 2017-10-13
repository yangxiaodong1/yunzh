package com.thinkgem.jeesite.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class IntelligentAccountUtils {
	public static String loadJSON(String addurl, String json) {
		StringBuffer sb = new StringBuffer("");
		try {
			// 创建连接
			URL url = new URL(addurl);
			// 设置请求头
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
			// 设置编码格式
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.connect();

			// POST请求
			OutputStreamWriter paramout = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			paramout.write(json.toString());
			paramout.flush();

			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String lines;
			// 读取返回json流
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			paramout.close();

			reader.close();
			// 断开连接
			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
