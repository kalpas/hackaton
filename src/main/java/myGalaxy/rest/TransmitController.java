package myGalaxy.rest;

import myGalaxy.domain.Edge;
import myGalaxy.domain.Node;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest")
public class TransmitController {

	@ResponseBody
	@RequestMapping(value = "/node", method = RequestMethod.GET)
	public Node getNode() {
		Node node = new Node();
		node.setId("test id");
		node.setName("test name");
		node.setSize("2");
		return node;
	}

	@ResponseBody
	@RequestMapping(value = "/edge", method = RequestMethod.GET)
	public Edge getEdge() {
		Edge node = new Edge();
		node.setId("test id");
		node.setFrom("test from");
		node.setTo("test to");
		return node;
	}
}
