package mygalaxy.graphing;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import mygalaxy.domain.Edge;
import mygalaxy.domain.Node;

public class QueueHolder {
	public Queue<Node> nodeQueue = new ConcurrentLinkedQueue<>();
	public Queue<Edge> edgeQueue = new ConcurrentLinkedQueue<>();
	
	public Boolean finished = false;

}
