package myGalaxy.graphing;

import myGalaxy.domain.Node;

import com.google.common.collect.Multimap;

public interface DataProvider {
	
	public Multimap<Node, Node> getData(String userId);

}
