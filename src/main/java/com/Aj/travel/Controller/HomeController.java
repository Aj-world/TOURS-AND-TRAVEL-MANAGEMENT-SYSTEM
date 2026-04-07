package com.aj.travel.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aj.travel.Entity.User;
import com.aj.travel.Service.PackageService;
import com.aj.travel.Service.RegistrationService;

@Controller
public class HomeController {

	@Autowired
	private RegistrationService rService;

	@Autowired
	private PackageService packageService;

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/home_Page")
	public String h2(Principal p,Model m) {
		User user= rService.findByEmail(p.getName());
		 
			m.addAttribute("name",user.getUserName1());
			m.addAttribute("packages", packageService.getAllPackages(PageRequest.of(0, 3)).getContent());
			return "/User/home_Page";
		}

}

