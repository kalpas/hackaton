package myGalaxy.VK.API.friends;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import myGalaxy.VK.API.RequestHelper;
import myGalaxy.VK.API.domain.FriendsResponse;
import myGalaxy.VK.API.domain.User;

import org.apache.http.client.utils.URIBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Friends {

	public List<User> get(String userId, String accessToken) {
		List<User> list = new ArrayList<>();

		// TODO support count over 5000
		URIBuilder builder = RequestHelper.getBuilder("friends.get");
		builder.addParameter("user_id", userId);
		builder.addParameter("order", "name");
		builder.addParameter("fields", "sex,country");

		String entityString = RequestHelper.execute(builder, accessToken);

		ObjectMapper mapper = new ObjectMapper();
		FriendsResponse friendsResponse = null;
		try {
			friendsResponse = mapper.readValue(entityString, FriendsResponse.class);
		} catch (Exception e) {
			// fuck this shit
			e.printStackTrace();
		}
		Collections.addAll(list, friendsResponse.response);
		return list;
	}
}
