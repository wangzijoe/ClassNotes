package com.example.demo.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpClientTest {

	@Test
	public void testHttp() throws Exception {
		String doGetOrPost = doGetOrPost("get", "http://101.37.148.209:88", null, null, null, null);
		System.err.println(doGetOrPost);
	}

	private String doGetOrPost(String method, String url, String jsonParam, String cookie, String token, String dirPro)
			throws Exception {
		org.apache.http.client.HttpClient httpClient = org.apache.http.impl.client.HttpClientBuilder.create().build();
		// 接受客户端发回的响应
		org.apache.http.HttpResponse httpResponse = null;
		if ("POST".equals(method.toUpperCase())) {
			// 通过HttpPost来发送post请求
			org.apache.http.client.methods.HttpPost httpPost = new org.apache.http.client.methods.HttpPost(url);
			httpPost.addHeader("Cookie", cookie);
			httpPost.addHeader("id_token", token);
			httpPost.addHeader("iPlanetDirectoryPro", dirPro);
			org.apache.http.entity.StringEntity stringEntity = new org.apache.http.entity.StringEntity(jsonParam);
			stringEntity.setContentEncoding("utf-8");
			stringEntity.setContentType("application/json");
			// 第一步：通过setEntity 将我们的entity对象传递过去
			httpPost.setEntity(stringEntity);
			// 通过client来执行请求，获取一个响应结果
			httpResponse = httpClient.execute(httpPost);
		}
		if ("GET".equals(method.toUpperCase())) {
			// 创建一个httpGet请求
			org.apache.http.client.methods.HttpGet httpGet = new org.apache.http.client.methods.HttpGet(url);
			// 创建一个htt客户端

			// 添加cookie到头文件
			httpGet.addHeader("Cookie", cookie);
			httpGet.addHeader("id_token", token);
			httpGet.addHeader("iPlanetDirectoryPro", dirPro);
			httpResponse = httpClient.execute(httpGet);
		}
		// 获取返回状态
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		// 如果是响应成功
		if (statusCode == org.apache.http.HttpStatus.SC_OK) {
			org.apache.http.HttpEntity responseHttpEntity = httpResponse.getEntity();
            return org.apache.http.util.EntityUtils.toString(responseHttpEntity, "utf-8");
		}
		return "{}";
	}

}
