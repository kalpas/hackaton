package myGalaxy.VK.API.auth;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class BaseController {
		
	@RequestMapping(method = RequestMethod.GET)
	public String getCode(ModelMap model, HttpSession session){
		System.out.println("hot home");
		
		return "login";
	}

}
