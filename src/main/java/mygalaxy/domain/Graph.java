package mygalaxy.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Graph {

	private List<Node> nodes = new ArrayList<Node>();
	private List<Edge> edges = new ArrayList<Edge>();

	public Multimap<Node, Node> internalMap = HashMultimap.create();

	public List<Node> getNodes() {
		return this.nodes;
	}

	public List<Edge> getEdges() {
		return this.edges;
	}

	@JsonIgnore
	public synchronized void addNode(Node node) {
		this.nodes.add(node);
	}

	@JsonIgnore
	public synchronized void getEdge(Edge edge) {
		this.edges.add(edge);
	}

	@JsonIgnore
	public synchronized void addAllNodes(Collection<Node> node) {
		this.nodes.addAll(node);
	}

	@JsonIgnore
	public synchronized void addAllEdges(Collection<Edge> edge) {
		this.edges.addAll(edge);
	}

	@JsonIgnore
	public synchronized List<Node> pullNodesChanges() {
		return this.getNewOnes(this.nodes);
	}

	@JsonIgnore
	public synchronized List<Edge> pullEdgesChanges() {
		return this.getNewOnes(this.edges);
	}

	private <T extends IPassable> List<T> getNewOnes(List<T> items) {
		// (1) List<T> newItems = Collections.<T> emptyList();
		List<T> newItems = new ArrayList<T>();
		for (int i = items.size() - 1; i >= 0; i--) {
			T n = items.get(i);
			if (!n.isPassed()) {
				newItems.add(n);
				n.setPassed(true);
			} else {
				break;
			}
			/*
			 * (1) if(n.isPassed()) { (1) newItems = items.subList(i + 1,
			 * items.size()); (1) }
			 */
		}
		return newItems;
	}

	public int getNodeCount() {
		return nodes.size();
	}

	public Collection<Node> getNeighbors(Node node) {
		Collection<Node> result = new ArrayList<Node>(internalMap.get(node));
		for (Node n : internalMap.keySet()) {
			if (internalMap.get(n).contains(node)) {
				result.add(n);
			}
		}
		return result;
	}

	public Edge getEdge(Node node, Node neighbor) {
		Edge criteria = new Edge();
		criteria.setId(node.getId() + neighbor.getId());
		int indexOf = edges.indexOf(criteria);
		if(indexOf == -1){
			criteria.setId(neighbor.getId() + node.getId());
			indexOf = edges.indexOf(criteria);
		}
		return indexOf>-1?edges.get(indexOf):null;

	}

	public int getDegree(Node node) {
		return Integer.valueOf(node.additionalProperties.get(Node.DEGREE));
	}
}
