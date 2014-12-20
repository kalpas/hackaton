package mygalaxy.auth;

import javax.servlet.http.HttpSession;

import mygalaxy.inst.InstaConfig;
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
	private VKConfig    vKconfig;

	@Autowired
	private InstaConfig instaConfig;

	@RequestMapping(method = RequestMethod.GET)
	public String getCode(ModelMap model, HttpSession session) {
		model.addAttribute("vk_client_id", vKconfig.getClientId());
		model.addAttribute("vk_scope", vKconfig.getScope());
		model.addAttribute("vk_redirect_uri", vKconfig.getRedirectUri());
		model.addAttribute("vk_display", vKconfig.getAuthDisplay());
		model.addAttribute("vk_response_type", vKconfig.getAuthResponseType());

		model.addAttribute("insta_client_id", instaConfig.getClientId());
		model.addAttribute("insta_redirect_uri", instaConfig.getRedirectUri());
		model.addAttribute("insta_response_type", instaConfig.getAuthResponse≈ype());

		return "login";
	}

}
