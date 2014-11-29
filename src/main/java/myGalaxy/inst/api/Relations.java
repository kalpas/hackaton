package myGalaxy.inst.api;

import myGalaxy.inst.domain.InstResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class Relations {
	
	public InstResponse getFollowers(String userId, String accessToken) {
		MultiValueMap<String, String> headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		RestTemplate template = new RestTemplate();
		return template.exchange(("https://api.instagram.com/v1/users/" + userId
				+ "/follows?access_token=" + accessToken), HttpMethod.GET,
				httpEntity, InstResponse.class).getBody();
	}

	public InstResponse getFollowedBy(String userId, String accessToken) {
		MultiValueMap<String, String> headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		RestTemplate template = new RestTemplate();
		return template.exchange(("https://api.instagram.com/v1/users/" + userId
				+ "/followed-by?access_token=" + accessToken), HttpMethod.GET,
				httpEntity, InstResponse.class).getBody();
	}
	
	public InstResponse getRequestedBy(String userId, String accessToken) {
		MultiValueMap<String, String> headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		RestTemplate template = new RestTemplate();
		return template.exchange(("https://api.instagram.com/v1/users/" + userId
				+ "/requested-by?access_token=" + accessToken), HttpMethod.GET,
				httpEntity, InstResponse.class).getBody();
	}
}
