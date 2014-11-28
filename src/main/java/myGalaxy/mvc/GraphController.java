package myGalaxy.mvc;

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
	public ModelAndView getGraph(
			@RequestParam(required = true, value = MyGalaxy.SESSION_USER_ID) String userId,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("graph");

		mav.addObject("graph", this.graphServ.buildGraph(userId, String
				.valueOf(session.getAttribute(MyGalaxy.SESSION_ACCESS_TOKEN))));
		return mav;
	}
}
