package myGalaxy.graphing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import myGalaxy.domain.Edge;
import myGalaxy.domain.Graph;
import myGalaxy.domain.Node;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;

public class GraphBuilder {
	
	private static final String DEGREE = "degree";
	private static final Double maxSize = 4.0;


	public Graph build(DataProvider provider, String userId) {
		Graph graph = new Graph();

		Multimap<Node, Node> map = provider.getData(userId);
		
		Set<Node> userSet = new HashSet<>();
		userSet.addAll(map.values());
		userSet.addAll(map.keys());
		
		int maxDegree = calculateDegree(userSet, map);
		calculateSize(userSet,maxDegree);
		
		graph.addAllNodes(userSet);

		List<Edge> edges = new ArrayList<>();
		for (Map.Entry<Node, Node> entry : map.entries()) {
			edges.add(new Edge(entry.getKey(), entry.getValue()));
		}
		graph.addAllEdges(edges);
		

		return graph;
	}
	
	private int calculateDegree(Set<Node> set, Multimap<Node, Node> map) {
		int maxDegree = 0;
		for(Node element: set){
			int degree = map.get(element).size();
			degree += HashMultiset.create(map.values()).count(element);
			element.additionalProperties.put(DEGREE, String.valueOf(degree));
			if (degree > maxDegree){
				maxDegree = degree;
			}
		}
		return maxDegree;
	}
	
	private void calculateSize(Set<Node> set, int maxDegree){
		for(Node element: set){
			int degree = Integer.valueOf(element.additionalProperties.get(DEGREE)); 
			element.setSize(String.valueOf(degree*maxSize/maxDegree));
		}
	}
	
}
