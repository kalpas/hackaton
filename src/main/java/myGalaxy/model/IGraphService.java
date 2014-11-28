package myGalaxy.model;

import java.util.List;

import myGalaxy.domain.Edge;
import myGalaxy.domain.Graph;
import myGalaxy.domain.Node;

public interface IGraphService {
	List<Node> getNodes();
	List<Node> getNodes(String id);
	
	List<Edge> getEdges();
	List<Edge> getEdges(String id);
	
	Graph buildGraph(String userId, String accessToken);
	Graph buildGraph(String userId, String accessToken, String id);
	Graph pullGraph(String id);
}
