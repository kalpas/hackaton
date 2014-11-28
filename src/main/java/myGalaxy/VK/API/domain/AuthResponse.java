package myGalaxy.VK.API.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
"access_token",
"expires_in",
"user_id"
})
public class AuthResponse {

@JsonProperty("access_token")
public String accessToken;
@JsonProperty("expires_in")
public Integer expiresIn;
@JsonProperty("user_id")
public Integer userId;

}
