package com.berenberg.library.security.jwt;



public class SecurityConstants {
	    public static final String SECRET = "SecretKeyToGenJWTsForBerenberLibrary";
	    public static final String ISSUER = "Berenberg";
	    public static final long EXPIRATION_TIME = 3_600_000; // 1hr
	    public static final String TOKEN_PREFIX = "Bearer ";
	    public static final String HEADER_STRING = "Authorization";
	    public static final String GET_AUTH_TOKEN = "/api/v1/library/getToken";
	    public static final String LOGIN = "/api/v1/login";
	   
	

}
