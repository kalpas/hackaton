package myGalaxy.inst.api;

import myGalaxy.inst.domain.InstResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class Users {

	public InstResponse search(String userName, String accessToken) {

		MultiValueMap<String, String> headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		RestTemplate template = new RestTemplate();
		return template.exchange(
				("https://api.instagram.com/v1/users/search?q=" + userName
						+ "&access_token=" + accessToken), HttpMethod.GET,
				httpEntity, InstResponse.class).getBody();

	}

}
