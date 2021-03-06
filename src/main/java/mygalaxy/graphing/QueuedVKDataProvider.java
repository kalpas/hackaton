package mygalaxy.graphing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mygalaxy.domain.Edge;
import mygalaxy.domain.Node;
import mygalaxy.vk.api.domain.User;
import mygalaxy.vk.api.friends.Friends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

@Component
public class QueuedVKDataProvider {

	@Autowired
	private Friends friends;

	public void setAccessToken(String accessToken) {
		this.friends.setAccessToken(accessToken);
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

	private Set<Node> populateConnections4User(Multimap<Node, Node> connections, Node center, Set<Node> all,
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
