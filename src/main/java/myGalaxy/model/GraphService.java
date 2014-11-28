package myGalaxy.model;

import java.util.List;

import myGalaxy.MyGalaxy;
import myGalaxy.VK.VkDataProvider;
import myGalaxy.domain.Edge;
import myGalaxy.domain.Graph;
import myGalaxy.domain.Node;
import myGalaxy.graphing.DataProvider;
import myGalaxy.graphing.GraphBuilder;

import org.springframework.stereotype.Service;

@Service
public class GraphService implements IGraphService {

	@Override
	public List<Node> getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Node> getNodes(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Edge> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Edge> getEdges(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Graph buildGraph(String userId, String accessToken) {
		GraphBuilder builder = new GraphBuilder();
		DataProvider provider = new VkDataProvider(accessToken);

		return builder.build(provider, userId);
	}
	
	public Graph asyncBuildGraph(String userId, String accessToken, String id) {
		// TODO: implement start of async graph building
		GraphBuilder builder = new GraphBuilder();
		DataProvider provider = new VkDataProvider(accessToken);
		
		return builder.build(provider, userId);
	}

	public Graph pullGraph(String id) {
		Graph g = new Graph();
		
		Graph graph = MyGalaxy.GRAPH_POOL.get(id);
		if(graph != null) {
			synchronized (graph) {
				g.addAllEdges(graph.pullEdgesChanges());
				g.addAllNodes(graph.pullNodesChanges());
			}
		}
		
		return g;
	}
}
