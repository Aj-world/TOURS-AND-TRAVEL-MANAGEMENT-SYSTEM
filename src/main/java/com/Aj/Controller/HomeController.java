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
public class HomeController {
	
	@Autowired
	private RegistationService rService;
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/home_Page")
	public String h2(Principal p,Model m) {
		User user= rService.findByName(p.getName());
		 
			m.addAttribute("name",user.getUserName1());
			return "/User/home_Page";
		}

}
