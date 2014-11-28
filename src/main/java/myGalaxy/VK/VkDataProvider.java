package myGalaxy.VK;

import java.util.List;

import myGalaxy.VK.API.domain.User;
import myGalaxy.VK.API.friends.Friends;
import myGalaxy.graphing.DataProvider;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class VkDataProvider implements DataProvider{
	
	private final String accessToken;
	private final Friends friends;
	
	public VkDataProvider(String accessToken) {
		this.accessToken = accessToken;
		this.friends = new Friends(accessToken);
	}

	@Override
	public Multimap<User, User> getData(String userId) {
		Multimap<User, User> connections = HashMultimap.create();
		
		User center  = new User();//TODO implement user.get
		center.uid = userId;
		List<User> firstStep = populateConnections4User(connections, center);
		for(User user: firstStep){
			populateConnections4User(connections, user);
		}
		return connections;
	}

	private List<User> populateConnections4User(Multimap<User, User> connections,
			User center) {
		List<User> list = friends.get(center.uid);
		connections.putAll(center, list);
		return list;
	}

}
