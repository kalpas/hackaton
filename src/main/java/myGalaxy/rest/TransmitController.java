package myGalaxy.rest;

import java.util.List;

import myGalaxy.domain.Edge;
import myGalaxy.domain.Node;
import myGalaxy.model.IGraphService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest")
public class TransmitController {

	@Autowired
	private IGraphService graphServ;
	
	@ResponseBody
	@RequestMapping(value = "/node", method = RequestMethod.GET)
	public List<Node> getNode() {
		return this.graphServ.getNodes();
	}

	@ResponseBody
	@RequestMapping(value = "/edge", method = RequestMethod.GET)
	public List<Edge> getEdge() {
		return this.graphServ.getEdges();
	}
}
