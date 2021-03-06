package mygalaxy.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import mygalaxy.MyGalaxy;
import mygalaxy.domain.Edge;
import mygalaxy.domain.Graph;
import mygalaxy.domain.Node;
import mygalaxy.model.IGraphService;

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
	@RequestMapping(value = "/vk", method = RequestMethod.GET)
	public Graph loadGraph(@RequestParam(required = false, value = "userId") String vkUserId, HttpSession session) {
		String vkId = vkUserId != null ? vkUserId : String.valueOf(session.getAttribute(MyGalaxy.VK_SESSION_USER_ID));
		return this.graphServ.buildGraph(vkId, String.valueOf(session.getAttribute(MyGalaxy.VK_SESSION_ACCESS_TOKEN)));
	}

	@ResponseBody
	@RequestMapping(value = "/insta", method = RequestMethod.GET)
	public Graph instLoadGraph(@RequestParam(required = false, value = "userId") String userId, HttpSession session) {
		return this.graphServ.buildInstGraph(
		        userId != null ? userId : String.valueOf(session.getAttribute(MyGalaxy.INST_SESSION_USER_ID)),
		        String.valueOf(session.getAttribute(MyGalaxy.INST_SESSION_ACCESS_TOKEN)));
	}

	@ResponseBody
	@RequestMapping(value = "/joined", method = RequestMethod.GET)
	public Graph joinedLoadGraph(@RequestParam(required = false, value = "vkUserId") String vkUserId,
	        @RequestParam(required = false, value = "instUserId") String instUserId, HttpSession session) {
		String instId = instUserId != null ? instUserId : String.valueOf(session
		        .getAttribute(MyGalaxy.INST_SESSION_USER_ID));
		String vkId = vkUserId != null ? vkUserId : String.valueOf(session.getAttribute(MyGalaxy.VK_SESSION_USER_ID));
		return this.graphServ.joinedGraph(vkId, instId,
		        String.valueOf(session.getAttribute(MyGalaxy.VK_SESSION_ACCESS_TOKEN)),
		        String.valueOf(session.getAttribute(MyGalaxy.INST_SESSION_ACCESS_TOKEN)));
	}

	@ResponseBody
	@RequestMapping(value = "/pull", method = RequestMethod.GET)
	public Graph pullGraph(@RequestParam(required = true, value = "id") String id) {
		return this.graphServ.pullGraph(id);
	}
}
