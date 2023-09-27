package com.Aj.Controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@RestController
public class FormController {
	
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/createOrder")
	@ResponseBody
	public  String h5(@RequestBody Map<String, Object> data)throws Exception {
		int amt=Integer.parseInt(data.get("amount").toString());
		
		System.out.println("run");
		RazorpayClient razorpay = new RazorpayClient("rzp_test_Obz1IQlDtOvh1b","emEgTjBai7QkbElaxGGDUZc5");

		 
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount",amt*100);
		orderRequest.put("currency","INR");
		orderRequest.put("receipt", "receipt#1");
		 

		Order order = razorpay.orders.create(orderRequest);
		System.out.println(order);
		
		
		return order.toString();
	}

}
 