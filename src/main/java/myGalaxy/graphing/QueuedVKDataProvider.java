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

public class QueuedVKDataProvider {

	private final Friends friends;

	public QueuedVKDataProvider(String accessToken) {
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

			List<User> set = friends.get(center.getId());
			Set<Node> nodes = new HashSet<>();
			for (User user : set) {
				Node node = new Node(user);
				nodes.add(node);
				queues.nodeQueue.add(node);
			}

			for (Node node : nodes) {
				populateConnections4User(connections, node, nodes, queues);
			}

			queues.finished = true;
			queues.finished.notifyAll();
		}
	}

	private Set<Node> populateConnections4User(
			Multimap<Node, Node> connections, Node center, Set<Node> all,
			QueueHolder queues) {

		List<User> set = friends.get(center.getId());
		Set<Node> nodes = new HashSet<>();
		for (User user : set) {
			Node node = new Node(user);
			nodes.add(node);
			if (all.contains(node)) {
				queues.nodeQueue.add(node);
				queues.edgeQueue.add(new Edge(center, node));
				connections.put(center, node);
			}
		}
		return nodes;
	}
}
