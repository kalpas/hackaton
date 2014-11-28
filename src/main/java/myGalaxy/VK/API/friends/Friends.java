package myGalaxy.VK.API.friends;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import myGalaxy.VK.API.RequestHelper;
import myGalaxy.VK.API.domain.ErrorCodes;
import myGalaxy.VK.API.domain.FriendsResponse;
import myGalaxy.VK.API.domain.User;
import myGalaxy.VK.API.domain.VKError;
import myGalaxy.VK.API.domain.VKErrorResponse;

import org.apache.http.client.utils.URIBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Friends {
	private final String accessToken;

	private ObjectMapper mapper = new ObjectMapper();

	public Friends(String accessToken) {
		this.accessToken = accessToken;
	}

	public List<User> get(String userId) {
		List<User> list = new ArrayList<>();

		// TODO support count over 5000
		URIBuilder builder = RequestHelper.getBuilder("friends.get");
		builder.addParameter("user_id", userId);
		builder.addParameter("order", "name");
		builder.addParameter("fields", "sex,country");

		String entityString = RequestHelper.execute(builder, accessToken);

		try {
			parseResponse(list, entityString);
		} catch (VKError e) {
			if(Integer.valueOf(e.errorCode) == ErrorCodes.TOO_MANY_REQUSTS){
				RequestHelper.sleep();
				return this.get(userId);
			}
		}

		return list;
	}

	private void parseResponse(List<User> list, String entityString) throws VKError {
		FriendsResponse friendsResponse = null;
		try {
			friendsResponse = mapper.readValue(entityString,
					FriendsResponse.class);
			if (friendsResponse != null) {
				Collections.addAll(list, friendsResponse.response);
			}
		} catch (Exception e) {
			try {
				VKError error = mapper.readValue(entityString,
						VKErrorResponse.class).error;
				if (error != null) {
					System.out.println(error.errorMsg);
					throw error;
				}
			} catch (IOException e1) {
				// fuck this shit
				System.out.println(entityString);
				e1.printStackTrace();
				e.printStackTrace();
			}

		}
	}
}
