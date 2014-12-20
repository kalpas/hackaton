package mygalaxy.vk.api.friends;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mygalaxy.vk.api.RequestHelper;
import mygalaxy.vk.api.domain.ErrorCodes;
import mygalaxy.vk.api.domain.FriendsResponse;
import mygalaxy.vk.api.domain.User;
import mygalaxy.vk.api.domain.VKError;
import mygalaxy.vk.api.domain.VKErrorResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.base.Joiner;

public class Friends {

	@Autowired
	private RequestHelper         requestHelper;

	private static final String[] FIELDS = { "sex", "country", "photo_200_orig", "connections" };

	private final String          accessToken;

	private ObjectMapper          mapper = new ObjectMapper();

	public Friends(String accessToken) {
		this.accessToken = accessToken;
	}

	public List<User> get(String userId) {
		List<User> list = new ArrayList<>();

		// TODO support count over 5000
		URIBuilder builder = requestHelper.getBuilder("friends.get");
		builder.addParameter("user_id", userId);
		builder.addParameter("order", "name");
		builder.addParameter("fields", Joiner.on(",").join(FIELDS));

		String entityString = requestHelper.execute(builder, accessToken);

		try {
			parseResponse(list, entityString);
		} catch (VKError e) {
			if (Integer.valueOf(e.errorCode) == ErrorCodes.TOO_MANY_REQUSTS) {
				requestHelper.sleep();
				return this.get(userId);
			}
		}

		return list;
	}

	private void parseResponse(List<User> list, String entityString) throws VKError {
		FriendsResponse friendsResponse = null;
		try {
			friendsResponse = mapper.readValue(entityString, FriendsResponse.class);
			if (friendsResponse != null) {
				Collections.addAll(list, friendsResponse.response);
			}
		} catch (Exception e) {
			try {
				VKError error = mapper.readValue(entityString, VKErrorResponse.class).error;
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

	public Map<User, List<User>> batchGet(List<User> users) {
		Map<User, List<User>> map = new HashMap<User, List<User>>();
		for (int i = 0; i < users.size(); i += 25) {
			List<User> temp = new ArrayList<>();
			String query = "return {\"result\":[";
			for (int j = 0; (j < 25 && (i + j) < users.size()); j++) {
				query += String
				        .format(" {\"user\":%s,\"friends\": API.friends.get({\"user_id\":%s,\"order\":\"name\",\"fields\":[\"sex\",\"country\",\"photo_200_orig\",\"connections\"]})},",
				                users.get(i + j).uid, users.get(i + j).uid);
				temp.add(users.get(i + j));
			}
			query += "]};";
			System.out.println(query);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("code", query));

			String result = requestHelper.executePost(accessToken, params);
			System.out.println(result);

			try {
				parseBatchResponse(result, temp, map);
			} catch (VKError e) {
				if (Integer.valueOf(e.errorCode) == ErrorCodes.TOO_MANY_REQUSTS) {
					System.out
					        .println("Fucked Up Beyond All Recognition - Burn it to the ground and start over from scratch; it's totally destroyed.");
				}
			}
		}

		return map;
	}

	private Map<User, List<User>> parseBatchResponse(String entity, List<User> users, Map<User, List<User>> map)
	        throws VKError {
		Map<User, List<User>> result = null;
		try {
			// result = mapper.readValue(entity,
			// BatchGetFriendsResultWrapper.class).response;
			result = parseJson(entity);
			map.putAll(result);
		} catch (Exception e) {
			try {
				VKError error = mapper.readValue(entity, VKErrorResponse.class).error;
				if (error != null) {
					System.out.println(error.errorMsg);
					throw error;
				}
			} catch (IOException e1) {
				// fuck this shit
				System.out.println(entity);
				e1.printStackTrace();
				e.printStackTrace();
			}

		}
		return map;
	}

	private Map<User, List<User>> parseJson(String entity) {
		Map<User, List<User>> map = new HashMap<>();

		try {

			JsonNode node = mapper.readTree(entity);
			ArrayNode array = (ArrayNode) node.get("response").withArray("result");
			Iterator<JsonNode> iterator = array.elements();
			while (iterator.hasNext()) {
				JsonNode element = iterator.next();
				String userId = element.get("user").asText();
				ArrayNode friends = (ArrayNode) element.withArray("friends");
				Iterator<JsonNode> friendsIterator = friends.elements();

				List<User> list = new ArrayList<>();
				User u = new User();
				u.uid = userId;
				map.put(u, list);
				while (friendsIterator.hasNext()) {
					JsonNode friend = friendsIterator.next();
					ObjectReader reader = mapper.reader(User.class);

					try {
						User usr = reader.readValue(friend);
						list.add(usr);
					} catch (IOException exception) {
						System.out.println(friend.toString());
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;

	}

}
