package com.Aj.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Aj.Entity.User;
import com.Aj.Entity.UserRole;
import com.Aj.Service.RegistrationService;
 
@Controller
@RequestMapping("/Aj")
public class RegisterController {

	@Autowired
	private RegistrationService registrationService;
	
	
	
//	Admin  registation Form

	@GetMapping("/Registion_Admin")
	public String h1() {

		return "/Authentication/Registion_Page_Admin";
	}
	
//	Admin  Do Resistation

	@PostMapping("/Resistation_process_Admin")
	public String h2(@ModelAttribute User admin, Model m) {
			User user1 = registrationService.register(admin, UserRole.ADMIN);
			m.addAttribute("user", user1);
		 
		return "redirect:/Admin_login";
	}

	
	
//	User  registation Form
	
	@GetMapping("/Registion_User")
	public String h3() {

		return "/Authentication/Registion_Page_User";
	}
	
//	USer  Do Resistation

	@PostMapping("/Resistation_process_User")
	public String h4(@ModelAttribute User user, Model m) {
		User admin1 = registrationService.register(user, UserRole.USER);
		m.addAttribute("user", admin1);
			
		return "redirect:/login";
	}
	 

	
	 

	
	 
	

}
