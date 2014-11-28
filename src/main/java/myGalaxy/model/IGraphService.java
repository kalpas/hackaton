package myGalaxy.model;

import java.util.List;

import myGalaxy.domain.Edge;
import myGalaxy.domain.Node;

public interface IGraphService {
	public List<Node> getNodes();
	public List<Node> getNodes(String id);
	
	public List<Edge> getEdges();
	public List<Edge> getEdges(String id);
}
