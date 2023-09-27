package com.Aj.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Aj.Entity.User;
import com.Aj.Service.RegistationService;
 
@Controller
@RequestMapping("/Aj")
public class RegisterController {

	@Autowired
	private PasswordEncoder pEncoder;
	
	@Autowired
	private RegistationService rService;
	
	
	
//	Admin  registation Form

	@GetMapping("/Registion_Admin")
	public String h1() {

		return "/Authentication/Registion_Page_Admin";
	}
	
//	Admin  Do Resistation

	@PostMapping("/Resistation_process_Admin")
	public String h2(@ModelAttribute User admin, Model m) {
		 
			 
			User uEntity = new User();
			uEntity.setUserName1(admin.getUserName1());
			uEntity.setEmail(admin.getEmail());
			uEntity.setUserPassword(pEncoder.encode(admin.getUserPassword()));
			uEntity.setUserPhoneNO(admin.getUserPhoneNO());
			uEntity.setUserRole(admin.getUserRole());
			User user1 = this.rService.saveUser(uEntity);
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
		 
		
		User aEntity = new User();

		aEntity.setUserName1(user.getUserName1());
		aEntity.setEmail(user.getEmail());
		aEntity.setUserPassword(pEncoder.encode(user.getUserPassword()));
		aEntity.setUserPhoneNO(user.getUserPhoneNO());
		aEntity.setUserRole(user.getUserRole());
		User admin1 = this.rService.saveUser(aEntity);
		m.addAttribute("user", admin1);
			
		return "redirect:/login";
	}
	 

	
	 

	
	 
	

}
