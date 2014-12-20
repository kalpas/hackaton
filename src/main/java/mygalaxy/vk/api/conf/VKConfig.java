package mygalaxy.vk.api.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class VKConfig {

	public final String API_HOST         = "oauth.vk.com";
	public final String API_BASE         = "api.vk.com/method/";
	public final String AUTH_PATH        = "/access_token";

	@Autowired
	@Qualifier("vkClientSecret")
	private String      clientSecret     = "ahX5Q63OgLkTkzAoIvDn";
	@Autowired
	@Qualifier("vkClientId")
	private String      clientId         = "4653561";
	@Autowired
	@Qualifier("vkScope")
	private String      scope            = "friends";
	@Autowired
	@Qualifier("vkAuthDisplay")
	private String      authDisplay      = "page";
	@Autowired
	@Qualifier("vkAuthResponseType")
	private String      authResponseType = "code";
	@Autowired
	@Qualifier("vkRedirectUri")
	private String      redirectUri      = "http://galaxy.kalpas.net/auth/vk";

	public String getClientSecret() {
		return clientSecret;
	}

	public String getClientId() {
		return clientId;
	}

	public String getScope() {
		return scope;
	}

	public String getAuthDisplay() {
		return authDisplay;
	}

	public String getAuthResponseType() {
		return authResponseType;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

}
