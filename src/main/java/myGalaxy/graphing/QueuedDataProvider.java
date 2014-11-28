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

			Node center = new Node();// FIXME implement user.get
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
			Node node = new Node(user);
			nodes.add(node);
			if (!connections.get(center).contains(node)) {
				queues.nodeQueue.add(node);
				queues.edgeQueue.add(new Edge(center, node));
				connections.put(center, node);
			}
		}
		return nodes;
	}
}
