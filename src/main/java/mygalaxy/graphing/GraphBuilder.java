package mygalaxy.graphing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import mygalaxy.domain.Edge;
import mygalaxy.domain.Graph;
import mygalaxy.domain.Node;
import mygalaxy.graphing.stats.Modularity;
import mygalaxy.graphing.stats.Modularity.CommunityStructure;
import mygalaxy.inst.InstagramDataProvider;
import mygalaxy.inst.api.Users;
import mygalaxy.inst.domain.InstResponse;
import mygalaxy.inst.domain.User;
import mygalaxy.vk.VkDataProvider;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;

public class GraphBuilder {

	private static final Double maxSize = 15.0;

	public Graph build(DataProvider provider, String userId) {
		Graph graph = new Graph();
		Multimap<Node, Node> map = provider.getData(userId);

		graph.internalMap = map;

		Set<Node> userSet = new HashSet<>();
		userSet.addAll(map.values());
		userSet.addAll(map.keys());

		int maxDegree = calculateDegree(userSet, map);
		calculateSize(userSet, maxDegree);

		graph.addAllNodes(userSet);

		List<Edge> edges = new ArrayList<>();
		for (Map.Entry<Node, Node> entry : map.entries()) {
			Edge edge = new Edge(entry.getKey(), entry.getValue());
			edge.setColor(entry.getKey().getColor());
			edges.add(edge);
		}
		graph.addAllEdges(edges);

		Modularity modularity = new Modularity();
		Modularity.CommunityStructure structure = modularity.execute(graph);

		colorize(graph, structure);

		return graph;
	}

	private void colorize(Graph graph, CommunityStructure structure) {
		String[] colors = new String[structure.N];
		for (int i = 0; i < structure.N; i++) {
			colors[i] = generateColor();
		}

		for (Node n : graph.getNodes()) {
			n.setColor(colors[n.getCommunityId()]);
		}

	}

	public String generateColor() {
		Random rand = new Random();
		int r = rand.nextInt(170);
		int g = rand.nextInt(170);
		int b = rand.nextInt(170);
		return "#" + Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
	}

	public Graph build(VkDataProvider vkProvider, InstagramDataProvider instagramProvider, String vkUserId,
	        String instaUserId, String accessToken, Users instaUsersApi) {
		Graph graph = new Graph();

		Multimap<Node, Node> vkMap = vkUserId != null && !"null".equals(vkUserId) ? vkProvider.getData(vkUserId)
		        : HashMultimap.<Node, Node> create();
		Multimap<Node, Node> instaMap = instaUserId != null && !"null".equals(instaUserId) ? instagramProvider
		        .getData(instaUserId) : HashMultimap.<Node, Node> create();

		graph.internalMap = vkMap;

		Multimap<Node, Node> temp = HashMultimap.create();
		for (Node vkUser : vkMap.keySet()) {
			String instaAccount = vkUser.additionalProperties.get(Node.INSTA);
			if (instaAccount != null) {
				InstResponse instResponse = accessToken != null && !"null".equals(accessToken) ? instaUsersApi.search(
				        instaAccount, accessToken) : new InstResponse();
				if (instResponse != null && instResponse.data != null && !instResponse.data.isEmpty()) {
					User instaUser = instResponse.data.get(0);
					Collection<Node> instaNodes = instaMap.get(new Node(instaUser));
					temp.putAll(vkUser, instaNodes);
					for (Node instaNode : instaNodes) {
						temp.putAll(instaNode, instaMap.get(instaNode));
					}
				}
			}
		}
		vkMap.putAll(temp);

		Set<Node> userSet = new HashSet<>();
		userSet.addAll(vkMap.values());
		userSet.addAll(vkMap.keys());

		int maxDegree = calculateDegree(userSet, vkMap);
		calculateSize(userSet, maxDegree);

		graph.addAllNodes(userSet);

		List<Edge> edges = new ArrayList<>();
		for (Map.Entry<Node, Node> entry : vkMap.entries()) {
			Edge edge = new Edge(entry.getKey(), entry.getValue());
			edge.setColor(entry.getKey().getColor());
			edges.add(edge);
		}
		graph.addAllEdges(edges);

		Modularity modularity = new Modularity();
		Modularity.CommunityStructure structure = modularity.execute(graph);

		colorize(graph, structure);

		return graph;

	}

	private int calculateDegree(Set<Node> set, Multimap<Node, Node> map) {
		int maxDegree = 0;
		for (Node element : set) {
			int degree = map.get(element).size();
			degree += HashMultiset.create(map.values()).count(element);
			element.additionalProperties.put(Node.DEGREE, String.valueOf(degree));
			if (degree > maxDegree) {
				maxDegree = degree;
			}
		}
		return maxDegree;
	}

	private void calculateSize(Set<Node> set, int maxDegree) {
		for (Node element : set) {
			int degree = Integer.valueOf(element.additionalProperties.get(Node.DEGREE));
			element.setSize(String.valueOf(degree * maxSize / maxDegree + 3));
		}
	}

}
