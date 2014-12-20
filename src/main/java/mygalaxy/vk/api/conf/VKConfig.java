package mygalaxy.vk.api.conf;


public class VKConfig {

	public final String API_HOST         = "oauth.vk.com";
	public final String API_BASE         = "api.vk.com/method/";
	public final String AUTH_PATH        = "/access_token";

	private String      clientSecret     = "ahX5Q63OgLkTkzAoIvDn";
	private String      clientId         = "4653561";
	private String      scope            = "friends";
	private String      authDisplay      = "page";
	private String      authResponseType = "code";
	private String      redirectUri      = "http://galaxy.kalpas.net/auth/vk";

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getAuthDisplay() {
		return authDisplay;
	}

	public void setAuthDisplay(String authDisplay) {
		this.authDisplay = authDisplay;
	}
	public String getAuthResponseType() {
		return authResponseType;
	}

	public void setAuthResponseType(String authResponseType) {
		this.authResponseType = authResponseType;
	}
	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

}
