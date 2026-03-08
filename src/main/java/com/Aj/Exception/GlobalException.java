package com.Aj.Exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler({ ResourceNotFoundException.class, BadRequestException.class })
	public String handleBusinessException(RuntimeException e, Model m) {
		m.addAttribute("error", e.getMessage());
		return "/Authentication/Error_page";
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleValidationException(MethodArgumentNotValidException e, Model m) {
		m.addAttribute("error", "Validation failed");
		return "/Authentication/Error_page";
	}
	
	@ExceptionHandler(Exception.class)
	public String h1(Exception e,Model m) {
		
		m.addAttribute("error", e.getMessage());
		return"/Authentication/Error_page";
	}

}
