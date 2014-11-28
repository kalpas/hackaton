package myGalaxy.graphing;

import myGalaxy.domain.Graph;

public class GraphBuilder {
	private final DataProvider provider;

	public GraphBuilder(DataProvider provider) {
		this.provider = provider;
	}

	public Graph build() {
		Graph graph = new Graph();
		return graph;

	}

}
