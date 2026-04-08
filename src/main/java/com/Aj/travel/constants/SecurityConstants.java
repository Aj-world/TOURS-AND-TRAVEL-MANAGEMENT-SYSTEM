package com.aj.travel.constants;

public final class SecurityConstants {

	public static final String USER = "USER";
	public static final String ADMIN = "ADMIN";
	public static final String ROLE_PREFIX = "ROLE_";
	public static final String HAS_ROLE_USER = "hasRole('" + USER + "')";
	public static final String HAS_ROLE_ADMIN = "hasRole('" + ADMIN + "')";

	private SecurityConstants() {
	}
}
