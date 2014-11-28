package myGalaxy.VK.API;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RequestHelper {
	
	public static final int TIMEOUT = 400;
	private static Long lastRequest = System.currentTimeMillis();

	public static String execute(URIBuilder builder, String accessToken) {

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
//			long delta = System.currentTimeMillis() - lastRequest;
//			if(delta > TIMEOUT){
//				try {
//					Thread.sleep(delta);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
			lastRequest = System.currentTimeMillis();
			response = httpclient.execute(get);
			try {
				entityString = IOUtils.toString(response.getEntity()
						.getContent());
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

	public static URIBuilder getBuilder(String method) {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(VK.API_BASE).setPath(method);
		return builder;

	}
	
	public static void sleep(){
		try {
			Thread.sleep(RequestHelper.TIMEOUT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
