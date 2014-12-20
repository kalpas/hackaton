package mygalaxy.auth;

import javax.servlet.http.HttpSession;

import mygalaxy.inst.Instagram;
import mygalaxy.vk.api.VK;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class BaseController {
		
	@RequestMapping(method = RequestMethod.GET)
	public String getCode(ModelMap model, HttpSession session){
		model.addAttribute("vk_client_id", VK.CLIENT_ID);
		model.addAttribute("vk_scope", VK.SCOPE);
		model.addAttribute("vk_redirect_uri", VK.REDIRECT_URI);
		model.addAttribute("vk_display", VK.AUTH_DISPLAY);
		model.addAttribute("vk_response_type", VK.AUTH_RESPONSE_TYPE);
		
		model.addAttribute("insta_client_id", Instagram.CLIENT_ID);
		model.addAttribute("insta_redirect_uri", Instagram.REDIRECT_URI);
		model.addAttribute("insta_response_type", Instagram.AUTH_RESPONSE_TYPE);
		
		return "login";
	}

}
