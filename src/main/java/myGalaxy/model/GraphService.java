package myGalaxy.model;

import java.util.List;
import java.util.Queue;

import myGalaxy.MyGalaxy;
import myGalaxy.VK.VkDataProvider;
import myGalaxy.domain.Edge;
import myGalaxy.domain.Graph;
import myGalaxy.domain.Node;
import myGalaxy.graphing.DataProvider;
import myGalaxy.graphing.GraphBuilder;
import myGalaxy.graphing.QueueHolder;

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

		QueueHolder qh = MyGalaxy.GRAPH_POOL.get(id);
		if (qh != null) {
			if (qh.finished) {
				return null;
			} else {
				synchronized (qh.nodeQueue) {
					Node node;
					while ((node = qh.nodeQueue.poll()) != null) {
						g.addNode(node);
					}
				}
				synchronized (qh.edgeQueue) {
					Edge edge;
					while ((edge = qh.edgeQueue.poll()) != null) {
						g.getEdge(edge);
					}
				}
			}
		}

		return g;
	}
}
