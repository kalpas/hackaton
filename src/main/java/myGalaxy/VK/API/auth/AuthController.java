package myGalaxy.VK.API.auth;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import myGalaxy.MyGalaxy;
import myGalaxy.VK.API.VK;
import myGalaxy.VK.API.domain.AuthResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@RequestMapping(method = RequestMethod.GET)
	public String getToken(
			@RequestParam(value = "code", required = false) String code,
			HttpSession session) throws URISyntaxException, IOException {

		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(VK.API_HOST).setPath(VK.AUTH_PATH);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		String URI = builder.build().toString();
		HttpPost post = new HttpPost(URI);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("client_id", VK.CLIENT_ID));
		params.add(new BasicNameValuePair("client_secret", VK.CLIENT_SECRET));
		params.add(new BasicNameValuePair("redirect_uri", VK.REDIRECT_URI));
		params.add(new BasicNameValuePair("code", code));
		post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		post.addHeader("Content-Type", "application/x-www-form-urlencoded");
		CloseableHttpResponse response = httpclient.execute(post);

		String entityString;
		try {
			entityString = IOUtils.toString(response.getEntity().getContent());
		} finally {
			response.close();
		}
		ObjectMapper mapper = new ObjectMapper();
		AuthResponse authResponse = null;
		try {
			authResponse = mapper.readValue(entityString, AuthResponse.class);
			session.setAttribute(MyGalaxy.SESSION_ACCESS_TOKEN, authResponse.accessToken);
			session.setAttribute(MyGalaxy.SESSION_EXPIRES_IN, authResponse.expiresIn);
			session.setAttribute(MyGalaxy.SESSION_USER_ID, authResponse.userId);
		} catch (JsonParseException | JsonMappingException e) {
			return "error";
		}

		return "redirect:graph";
	}
	// @RequestParam("access_token") String accessToken,
	// @RequestParam("expires_in") String expiresIn,
	// @RequestParam("user_id") String userId,

}
