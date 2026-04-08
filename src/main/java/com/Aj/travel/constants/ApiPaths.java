package com.aj.travel.constants;

public final class ApiPaths {

	public static final String ROOT = "/";
	public static final String LOGIN = "/login";
	public static final String LOGOUT = "/logout";
	public static final String HOME = "/home";
	public static final String ABOUT = "/about";
	public static final String PROFILE_ABOUT = "/profile/about";
	public static final String ADMIN = "/admin";
	public static final String LOGIN_SUCCESS = "/login-success";
	public static final String DASHBOARD = "/dashboard";
	public static final String USERS = "/users";
	public static final String USER_BY_ID = "/users/{id}";
	public static final String USER_EDIT = "/users/{id}/edit";
	public static final String USER_DELETE = "/users/{id}/delete";
	public static final String AUTH = "/auth";
	public static final String REGISTER = "/register";
	public static final String AUTH_LOGIN = AUTH + LOGIN;
	public static final String AUTH_REGISTER = AUTH + REGISTER;
	public static final String AUTH_REGISTER_ADMIN = AUTH_REGISTER + ADMIN;
	public static final String AUTH_REGISTER_USER = AUTH_REGISTER + "/user";
	public static final String REGISTER_ADMIN = "/admin";
	public static final String REGISTER_USER = "/user";
	public static final String PACKAGES = "/packages";
	public static final String PACKAGE_BY_ID = "/{id}";
	public static final String BOOKINGS = "/bookings";
	public static final String BOOKING_PRICE = "/{bookingId}/price";
	public static final String PAYMENTS = "/payments";
	public static final String PAYMENT_ORDERS = "/orders";
	public static final String PAYMENT_VERIFY = "/verify";
	public static final String PAYMENT_SUCCESS = "/success";
	public static final String PAYMENTS_ALL = PAYMENTS + "/**";
	public static final String PAYMENT_ORDERS_FULL = PAYMENTS + PAYMENT_ORDERS;
	public static final String H2_CONSOLE = "/h2-console/**";
	public static final String ERROR = "/error";
	public static final String CSS_ALL = "/css/**";
	public static final String JS_ALL = "/js/**";
	public static final String IMAGES_ALL = "/images/**";
	public static final String REDIRECT_PREFIX = "redirect:";

	private ApiPaths() {
	}
}
