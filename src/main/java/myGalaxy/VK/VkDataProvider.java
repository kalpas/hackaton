package myGalaxy.VK;

import java.util.ArrayList;
import java.util.List;

import myGalaxy.VK.API.domain.User;
import myGalaxy.VK.API.friends.Friends;
import myGalaxy.domain.Node;
import myGalaxy.graphing.DataProvider;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class VkDataProvider implements DataProvider {
	
	private final Friends friends;

	public VkDataProvider(String accessToken) {
		this.friends = new Friends(accessToken);
	}

	@Override
	public Multimap<Node, Node> getData(String userId) {
		Multimap<Node, Node> connections = HashMultimap.create();

		Node center = new Node();// TODO implement user.get
		center.setId(userId);
		
		List<User> firstStep = friends.get(center.getId());
		List<Node> nodes = new ArrayList<>();
		for (User user : firstStep) {
				nodes.add(new Node(user));
		}
		//connections.putAll(center, nodes);//remove self
		
		for (Node node : nodes) {
			populateConnections4User(connections, node, nodes);
		}
		
		
		
		
		return connections;
	}

	

	private List<Node> populateConnections4User(
			Multimap<Node, Node> connections, Node center, List<Node> all) {
		List<User> list = friends.get(center.getId());
		List<Node> nodes = new ArrayList<>();
		for (User user : list) {
			Node node = new Node(user);
			if (all.contains(node)) {//TODO add tails
				nodes.add(node);
			}
		}
		connections.putAll(center, nodes);
		return nodes;
	}

}
