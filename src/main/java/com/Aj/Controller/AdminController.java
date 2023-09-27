package com.Aj.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.Aj.Entity.User;
import com.Aj.Service.UserService;

 
@Controller
public class AdminController {

	@Autowired
	private UserService	userService;
	

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/Admin_login")
	public String h1() {

		return "/Admin/LoginSuccess";
	}

	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/Admin_Fetch_Data")
	public String h2(Model m) {
		 
		  List<User> userEntities = this.userService.getAllUser();
		  m.addAttribute("user", userEntities);
		 
		return "/Admin/AdminDasBord";
	}

// Delete Code
	
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/Aj/Delete{id}")
	public String h4(@PathVariable int id, Model m) {

		this.userService.deleteUser(id);

		return "redirect: /Admin_Fetch_Data";

	}
	
	
	
	
//	Update related Code
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/Aj/Update{id}")
	public String h3(@PathVariable int id, Model m) {
		m.addAttribute("id", id);
		return "/Admin/UpdateAdmin";
	}
	
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/Aj1/Update{id}")
	public String h5(@PathVariable int id, @ModelAttribute User uEntity) {

		User uEntity2 = this.userService.updateUser(id);

		uEntity2.setUserId(id);
		uEntity2.setUserName1(uEntity.getUserName1());
		uEntity2.setUserPassword(uEntity.getUserPassword());
		uEntity2.setEmail(uEntity.getEmail()); 
		 
		uEntity2.setUserPhoneNO(uEntity.getUserPhoneNO());

		this.userService.saveUser(uEntity2);
		return "redirect:/Admin/Admin_Fetch_Data";
	}

}


