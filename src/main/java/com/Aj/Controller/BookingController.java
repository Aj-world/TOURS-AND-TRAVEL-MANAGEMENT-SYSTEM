package com.Aj.Controller;

import java.security.Principal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Aj.Entity.Booking;
import com.Aj.Entity.Package;
import com.Aj.Entity.User;
import com.Aj.Service.RegistationService;

import lombok.Data;

@Data
@Controller
public class BookingController {
	
	private Package package1;
	private Booking booking;
	
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/Packge_process")
	 
	public String h1(@ModelAttribute Package package1 ,@ModelAttribute Booking booking,Model m,Principal p) {
		
		this.package1=package1;
		System.out.println(package1);
		
		this.booking=booking;
		  System.out.println(booking);
		  
		  
		  User user= rService.findByName(p.getName());
			System.out.println();
				
				m.addAttribute("name",user.getUserName1());
		  
		return "/User/Pament_Page";
	}
	
	
@GetMapping("Packge_process2")
@ResponseBody
public String h3() {
	
	  JSONObject jsonObject = new JSONObject();

      // Add key-value pairs to the JSON object
      jsonObject.put("price", package1.getPrice());
      
      String jsonString = jsonObject.toString();

      
      
    return jsonString;
	
}
	 
	
	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Autowired
	private RegistationService rService;
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/Pament_Page_process")
	public String h3(Principal principal) {
		
	User user= rService.findByName(principal.getName());
	
	booking.setUser(user);
	user.getBooking().add(0, booking);
	
 
	
	package1.setUser(user);
	user.getPackage1().add(0, package1);
	
	 
	
	
	
	package1.setBooking(booking);
	booking.setPackage1(package1);
	
	
	
	rService.saveUser(user);
	
	
	
	
	
	
	
	
	
	
	
		
		return "/Authentication/Sucess_page";
	}

}
