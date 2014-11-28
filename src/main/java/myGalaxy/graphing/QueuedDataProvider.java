package myGalaxy.graphing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import myGalaxy.VK.API.domain.User;
import myGalaxy.VK.API.friends.Friends;
import myGalaxy.domain.Edge;
import myGalaxy.domain.Node;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class QueuedDataProvider {

	private final Friends friends;

	public QueuedDataProvider(String accessToken) {
		this.friends = new Friends(accessToken);
	}

	public QueueHolder submitTask(final String userId) {

		final QueueHolder queues = new QueueHolder();

		Runnable task = new Runnable() {

			@Override
			public void run() {
				getData(userId, queues);
			}
		};
		Thread thread = new Thread(task);
		thread.start();

		return queues;
	}

	private void getData(String userId, QueueHolder queues) {
		synchronized (queues.finished) {

			Multimap<Node, Node> connections = HashMultimap.create();

			Node center = new Node();// TODO implement user.get
			center.setId(userId);
			Set<Node> firstStep = populateConnections4User(connections, center,
					queues);
			for (Node node : firstStep) {
				populateConnections4User(connections, node, queues);
			}

			queues.finished = true;
			queues.finished.notifyAll();
		}
	}

	private Set<Node> populateConnections4User(
			Multimap<Node, Node> connections, Node center, QueueHolder queues) {
		if (!connections.containsKey(center)) {
			queues.nodeQueue.add(center);
		}

		List<User> set = friends.get(center.getId());
		Set<Node> nodes = new HashSet<>();
		for (User user : set) {
			Node node = createNode(user);
			nodes.add(node);
			if (!connections.get(center).contains(node)) {
				queues.nodeQueue.add(node);
				queues.edgeQueue.add(createEdge(center, node));
				connections.put(center, node);
			}
		}
		return nodes;
	}

	private Node createNode(User user) {
		Node node = new Node();
		node.setId(user.uid);
		node.setName(user.first_name + " " + user.last_name);
		node.setSize(Graphing.DEFAULT_NODE_SIZE.toString());
		return node;
	}

	private Edge createEdge(Node key, Node value) {
		Edge edge = new Edge();
		edge.setFrom(key.getId());
		edge.setTo(value.getId());
		edge.setId(key.getId() + value.getId());
		return edge;
	}

}
