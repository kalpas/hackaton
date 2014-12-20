package mygalaxy.auth;

import javax.servlet.http.HttpSession;

import mygalaxy.inst.Instagram;
import mygalaxy.vk.api.conf.VKConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class BaseController {

	@Autowired
	private VKConfig config;

	@RequestMapping(method = RequestMethod.GET)
	public String getCode(ModelMap model, HttpSession session) {
		model.addAttribute("vk_client_id", config.getClientId());
		model.addAttribute("vk_scope", config.getScope());
		model.addAttribute("vk_redirect_uri", config.getRedirectUri());
		model.addAttribute("vk_display", config.getAuthDisplay());
		model.addAttribute("vk_response_type", config.getAuthResponseType());

		model.addAttribute("insta_client_id", Instagram.CLIENT_ID);
		model.addAttribute("insta_redirect_uri", Instagram.REDIRECT_URI);
		model.addAttribute("insta_response_type", Instagram.AUTH_RESPONSE_TYPE);

		return "login";
	}

}
