package mygalaxy.vk.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import mygalaxy.vk.api.conf.VKConfig;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestHelper {

	@Autowired
	private VKConfig config;

	public final int TIMEOUT = 400;

	public String execute(URIBuilder builder, String accessToken) {

		builder.addParameter("access_token", accessToken);

		String URI = null;
		try {
			URI = builder.build().toString();
			System.out.println(URI);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet get = new HttpGet(URI);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String entityString = null;
		try {
			response = httpclient.execute(get);
			try {
				entityString = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} finally {
				response.close();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return entityString;

	}

	public String executePost(String accessToken, List<NameValuePair> params) {

		params.add(new BasicNameValuePair("access_token", accessToken));

		URIBuilder builder = getBuilder("execute");
		String URI = null;
		try {
			URI = builder.build().toString();
			System.out.println(URI);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		HttpPost post = new HttpPost(URI);
		post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		post.addHeader("Content-Type", "application/x-www-form-urlencoded");

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String entityString = null;
		try {
			response = httpclient.execute(post);
			try {
				entityString = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} finally {
				response.close();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return entityString;

	}

	public URIBuilder getBuilder(String method) {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(config.API_BASE).setPath(method);
		return builder;

	}

	public void sleep() {
		try {
			Thread.sleep(TIMEOUT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
