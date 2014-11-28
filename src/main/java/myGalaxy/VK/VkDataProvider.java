package myGalaxy.VK;

import java.util.ArrayList;
import java.util.List;

import myGalaxy.VK.API.domain.User;
import myGalaxy.VK.API.friends.Friends;
import myGalaxy.domain.Node;
import myGalaxy.graphing.DataProvider;
import myGalaxy.graphing.Graphing;

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
		List<Node> firstStep = populateConnections4User(connections, center);
		for (Node node : firstStep) {
			populateConnections4User(connections, node);
		}
		return connections;
	}

	private List<Node> populateConnections4User(
			Multimap<Node, Node> connections, Node center) {
		List<User> list = friends.get(center.getId());
		List<Node> nodes = new ArrayList<>();
		for (User user : list) {
			nodes.add(createNode(user));
		}
		connections.putAll(center, nodes);
		return nodes;
	}

	private Node createNode(User user) {
		Node node = new Node();
		node.setId(user.uid);
		node.setName(user.first_name + " " + user.last_name);
		node.setSize(Graphing.DEFAULT_NODE_SIZE.toString());
		return node;
	}

}
