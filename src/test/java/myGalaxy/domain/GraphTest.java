package myGalaxy.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class GraphTest {
	@Test
	public void testGetNewNodes() {
		Graph graph = new Graph();
		Node n = new Node();
		n.setId("Test");
		n.setPassed(true);
		graph.addNode(n);
		n = new Node();
		n.setId("Test1");
		graph.addNode(n);
		n = new Node();
		n.setId("Test2");
		graph.addNode(n);
		n = new Node();
		n.setId("Test3");
		graph.addNode(n);
		
		List<Node> newNodes = graph.pullNodesChanges();
		
		assertEquals(3,newNodes.size());
		assertEquals("Test3",newNodes.get(0).getId());
		assertTrue(newNodes.get(0).isPassed());
		assertEquals("Test2",newNodes.get(1).getId());
		assertTrue(newNodes.get(1).isPassed());
		assertEquals("Test1",newNodes.get(2).getId());
		assertTrue(newNodes.get(2).isPassed());
	}

	@Test
	public void testGetNewNodes_emptyNodes() {
		Graph graph = new Graph();
		
		List<Node> newNodes = graph.pullNodesChanges();
		
		assertTrue(newNodes.isEmpty());
	}
}
