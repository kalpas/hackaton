package myGalaxy.mvc;

import java.util.Random;

import javax.servlet.http.HttpSession;

import myGalaxy.MyGalaxy;
import myGalaxy.model.IGraphService;

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
	public ModelAndView getGraph() {
		ModelAndView mav = new ModelAndView("graph");
		return mav;
	}
	
	@RequestMapping(value = "build")
	public ModelAndView asyncBuild(
			@RequestParam(required = true, value = MyGalaxy.SESSION_USER_ID) String userId,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("graph");

		String id = String.valueOf(new Random(1000).nextLong());

		this.graphServ.asyncBuildGraph(userId, String
				.valueOf(session.getAttribute(MyGalaxy.SESSION_ACCESS_TOKEN)),id);

		mav.addObject("graphId", id);
		return mav;
	}
}
