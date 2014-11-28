package myGalaxy.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {
	
	private List<Node> nodes = new ArrayList<Node>();
	private List<Edge> edges = new ArrayList<Edge>();

	public synchronized void addNode(Node node) {
		this.nodes.add(node);
	}
	
	public synchronized void getEdge(Edge edge) {
		this.edges.add(edge);
	}
	
	public synchronized void addAllNodes(List<Node> node) {
		this.nodes.addAll(node);
	}
	
	public synchronized void getAllEdges(List<Edge> edge) {
		this.edges.addAll(edge);
	}
	
	public synchronized List<Node> getNewNodes() {
		return this.getNewOnes(this.nodes);
	}
	
	public synchronized List<Edge> getNewEdges() {
		return this.getNewOnes(this.edges);
	}
	
	private <T extends IPassable> List<T> getNewOnes(List<T> items) {
		// (1) List<T> newItems = Collections.<T> emptyList(); 
		List<T> newItems = new ArrayList<T>();
		for(int i = items.size() - 1; i >= 0; i-- ) {
			T n = items.get(i);
			if(!n.isPassed()) {
				newItems.add(n);
				n.setPassed(true);
			} else {
				break;
			}
			/* (1) if(n.isPassed()) {
			   (1) newItems = items.subList(i + 1, items.size());
			   (1) } */
		}
		return newItems;
	}
}
