package myGalaxy.auth;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@RequestMapping(method = RequestMethod.GET)
	public String getCode(ModelMap model, HttpSession session)
			throws URISyntaxException, IOException {

		System.out.println("hit!");

		return "index.jsp";
	}

}
