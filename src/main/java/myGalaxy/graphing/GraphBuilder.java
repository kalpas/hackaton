package myGalaxy.graphing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import myGalaxy.domain.Edge;
import myGalaxy.domain.Graph;
import myGalaxy.domain.Node;

import com.google.common.collect.Multimap;

public class GraphBuilder {

	public Graph build(DataProvider provider, String userId) {
		Graph graph = new Graph();

		Multimap<Node, Node> map = provider.getData(userId);
		
		Set<Node> userSet = new HashSet<>();
		userSet.addAll(map.values());
		userSet.addAll(map.keys());

		graph.addAllNodes(userSet);

		List<Edge> edges = new ArrayList<>();
		for (Map.Entry<Node, Node> entry : map.entries()) {
			edges.add(new Edge(entry.getKey(), entry.getValue()));
		}
		graph.addAllEdges(edges);

		return graph;
	}
}
