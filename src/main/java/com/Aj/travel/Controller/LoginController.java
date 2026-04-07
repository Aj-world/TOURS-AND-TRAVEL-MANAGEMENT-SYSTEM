package com.aj.travel.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aj.travel.Entity.User;
import com.aj.travel.Service.RegistrationService;

@Controller
public class LoginController {
	private final RegistrationService registrationService;

	public LoginController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@GetMapping("/login")
	public String h1() {
		return "/Authentication/Login_Page";
	}

	@GetMapping("/")
	public String h2(Principal p,Model m) {
	User user= registrationService.findByEmail(p.getName());

		m.addAttribute("name",user.getUserName1());
		return "/User/home_Page";
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/User_about_Page")
	public String h3() {
		return "/User/User_about_Page";
	}

}

