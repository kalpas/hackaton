package myGalaxy.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class GraphController {
	
	@RequestMapping(value = "graph")
	public ModelAndView getGraph() {
		ModelAndView mav = new ModelAndView("graph");

		return mav;
	}
}
