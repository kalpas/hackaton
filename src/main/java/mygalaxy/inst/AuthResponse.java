package mygalaxy.inst;

import mygalaxy.inst.domain.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "access_token", "user" })
public class AuthResponse {

	@JsonProperty("access_token")
	public String accessToken;
	@JsonProperty("user")
	public User   user;

}
