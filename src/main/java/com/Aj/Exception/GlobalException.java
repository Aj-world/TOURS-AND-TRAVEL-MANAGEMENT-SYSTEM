package com.Aj.Exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(Exception.class)
	public String h1(Exception e,Model m) {
		
		m.addAttribute("error", e.getMessage());
		return"/Authentication/Error_page";
	}

}
