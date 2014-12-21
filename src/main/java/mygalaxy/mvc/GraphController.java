package mygalaxy.mvc;

import java.util.Random;

import javax.servlet.http.HttpSession;

import mygalaxy.MyGalaxy;
import mygalaxy.model.IGraphService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class GraphController {

	@Autowired
	private IGraphService graphServ;

	@RequestMapping(value = "graph")
	public ModelAndView getGraph(@RequestParam(required = false, value = "type") String type,HttpSession session) {
		ModelAndView mav = new ModelAndView("graph");

		String id = String.valueOf(new Random(1000).nextLong());

		mav.addObject("graphId", id);
		mav.addObject("graphType", type);

		return mav;
	}

	@RequestMapping(value = "asyncgraph")
	public ModelAndView asyncBuild(@RequestParam(required = false, value = MyGalaxy.VK_SESSION_USER_ID) String userId,
	        HttpSession session) {
		ModelAndView mav = new ModelAndView("graph");

		String id = String.valueOf(new Random(1000).nextLong());

		mav.addObject("graphId", id);

		this.graphServ.asyncBuildGraph(
		        userId != null ? userId : String.valueOf(session.getAttribute(MyGalaxy.VK_SESSION_USER_ID)),
		        String.valueOf(session.getAttribute(MyGalaxy.VK_SESSION_ACCESS_TOKEN)), id);

		mav.addObject("graphId", id);
		return mav;
	}
}
