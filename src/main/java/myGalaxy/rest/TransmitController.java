package myGalaxy.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import myGalaxy.MyGalaxy;
import myGalaxy.domain.Edge;
import myGalaxy.domain.Graph;
import myGalaxy.domain.Node;
import myGalaxy.model.IGraphService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@ResponseBody
	@RequestMapping(value = "/build", method = RequestMethod.GET)
	public Graph loadGraph(
			@RequestParam(required = true, value = "userId") String userId,
			HttpSession session) {
		return this.graphServ.buildGraph(userId, String.valueOf(session
				.getAttribute(MyGalaxy.VK_SESSION_ACCESS_TOKEN)));
	}
	
	@ResponseBody
	@RequestMapping(value = "/instbuild", method = RequestMethod.GET)
	public Graph instLoadGraph(
			@RequestParam(required = false, value = "userId") String userId,
			HttpSession session) {
		return this.graphServ.buildGraph(userId != null ? userId : String.valueOf(session
				.getAttribute(MyGalaxy.INST_SESSION_USER_ID)), String.valueOf(session
				.getAttribute(MyGalaxy.INST_SESSION_ACCESS_TOKEN)));
	}
	
	@ResponseBody
	@RequestMapping(value = "/pull", method = RequestMethod.GET)
	public Graph pullGraph(@RequestParam(required = true, value = "id") String id) {
		return this.graphServ.pullGraph(id);
	}
}
