package com.Aj.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class AboutController {

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/about_Page")
	public String h2() {
		return "/User/about_Page";
	}
	
}
