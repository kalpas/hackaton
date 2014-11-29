package myGalaxy.inst;

import java.util.ArrayList;
import java.util.List;

import myGalaxy.domain.Node;
import myGalaxy.graphing.DataProvider;
import myGalaxy.inst.api.Relations;
import myGalaxy.inst.domain.InstResponse;
import myGalaxy.inst.domain.User;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class InstagramDataProvider implements DataProvider{
	private Relations relations;

	private String accessToken;

	public InstagramDataProvider(String accessToken, Relations relations) {
		this.accessToken = accessToken;
		this.relations = relations;
	}
	
	@Override
	public Multimap<Node, Node> getData(String userId) {
		Multimap<Node, Node> connections = HashMultimap.create();

		Node center = new Node();// TODO implement user.get
		center.setId(userId);
		
		List<User> firstStep = ((InstResponse) relations.getFollowers(userId, this.accessToken)).data;
		List<Node> nodes = new ArrayList<>();
		for (User user : firstStep) {
				nodes.add(new Node(user));
		}
		
		for (Node node : nodes) {
			populateConnections4User(connections, node, nodes);
		}
		return connections;
	}

	private List<Node> populateConnections4User(
			Multimap<Node, Node> connections, Node center, List<Node> all) {
		List<User> list = ((InstResponse) relations.getFollowers(center.getId(),this.accessToken)).data;
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
