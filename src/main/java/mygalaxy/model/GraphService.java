package mygalaxy.model;

import java.util.List;

import mygalaxy.MyGalaxy;
import mygalaxy.domain.Edge;
import mygalaxy.domain.Graph;
import mygalaxy.domain.Node;
import mygalaxy.graphing.DataProvider;
import mygalaxy.graphing.GraphBuilder;
import mygalaxy.graphing.QueueHolder;
import mygalaxy.graphing.QueuedVKDataProvider;
import mygalaxy.inst.InstagramDataProvider;
import mygalaxy.inst.api.Relations;
import mygalaxy.inst.api.Users;
import mygalaxy.vk.VkDataProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraphService implements IGraphService {

	@Autowired
	private Relations relations;

	@Autowired
	private Users     users;

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

	public Graph buildInstGraph(String userId, String accessToken) {
		GraphBuilder builder = new GraphBuilder();
		DataProvider provider = new InstagramDataProvider(accessToken, relations);

		return builder.build(provider, userId);
	}

	public void asyncBuildGraph(String userId, String accessToken, String id) {
		QueuedVKDataProvider provider = new QueuedVKDataProvider(accessToken);

		QueueHolder qh = provider.submitTask(userId);
		MyGalaxy.GRAPH_POOL.put(id, qh);
	}

	public Graph joinedGraph(String vkUserId, String instUserId, String vkAccessToken, String instaAccessToken) {
		GraphBuilder builder = new GraphBuilder();
		InstagramDataProvider instaProvider = new InstagramDataProvider(instaAccessToken, relations);
		VkDataProvider vkProvider = new VkDataProvider(vkAccessToken);

		return builder.build(vkProvider, instaProvider, vkUserId, instUserId, instaAccessToken, users);

	}

	public Graph pullGraph(String id) {
		Graph g = new Graph();

		QueueHolder qh = MyGalaxy.GRAPH_POOL.get(id);
		if (qh != null) {
			if (qh.finished && qh.nodeQueue.isEmpty() && qh.edgeQueue.isEmpty()) {
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
