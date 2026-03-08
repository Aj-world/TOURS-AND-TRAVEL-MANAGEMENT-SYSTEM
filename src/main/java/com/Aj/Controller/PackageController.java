package com.Aj.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PackageController {
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/Package_Page")
	public String ha() {
		 
		return "/User/Package_Page";
	}
	
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View1")
	public String h1() {
		 
		return "/Packages/View1";
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View2")
	public String h2() {
		return "/Packages/View2";
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View3")
	public String h3() {
		return "/Packages/View3";
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View4")
	public String h4() {
		return "/Packages/View4";
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View5")
	public String h5() {
		return "/Packages/View5";
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View6")
	public String h6() {
		return "/Packages/View6";
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View7")
	public String h7() {
		return "/Packages/View7";
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View8")
	public String h8() {
		return "/Packages/View8";
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View9")
	public String h9() {
		return "/Packages/View9";
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View91")
	public String h91() {
		return "/Packages/View91";
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View92")
	public String h92() {
		return "/Packages/View92";
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/View93")
	public String h93() {
		return "/Packages/View93";
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/Book_Page")
	public String bookPage() {
		return "/User/Book_Page";
	}

}
