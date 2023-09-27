package com.Aj.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Aj.Entity.User;
import com.Aj.Service.RegistationService;

@Controller
public class LoginController {
	@Autowired
	private RegistationService rService;
	
//	Login Controller
	
	@GetMapping("/login")
	public String h1() {
	
		return "/Authentication/Login_Page";
	}
	
//	After login 
	
	@GetMapping("/")
	public String h2(Principal p,Model m) {
	User user= rService.findByName(p.getName());
	 
		m.addAttribute("name",user.getUserName1());
		return "/User/home_Page";
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/User_about_Page")
	public String h3() {
		return "/User/User_about_Page";
	}
	 
}