package mygalaxy.inst;

public class InstaConfig {

	public final String API_HOST  = "api.instagram.com/oauth";
	public final String AUTH_PATH = "/access_token";

	private String      clientSecret;
	private String      clientId;
	private String      redirectUri;
	private String      authResponse�ype;

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

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getAuthResponse�ype() {
		return authResponse�ype;
	}

	public void setAuthResponse�ype(String auth�esponse�ype) {
		this.authResponse�ype = auth�esponse�ype;
	}

}
