package com.berenberg.library.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.berenberg.library.dto.TokenRequest;
import com.berenberg.library.dto.TokenResponse;

@Component
public class JwtUtils {
   private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    public static DecodedJWT Decode(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(SecurityConstants.ISSUER)
                .build(); //Reusable verifier instance
        //Invalid signature/claims
        return verifier.verify(token);
    }
    
    public TokenResponse createToken(TokenRequest request) {
    	logger.info("Creating Json Web Token for client: {}",request.getClientId());	
    	 logger.info("secret===" + request.getClientSecret());
    	
        if(request.getClientSecret()!=SecurityConstants.SECRET){
          //  return new TokenResponse(ExceptionEnum.., null, 0)
        }



    	String token = JWT.create().withIssuer(SecurityConstants.ISSUER)
    			.withSubject(request.getClientId())    			
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                //.withClaim("sessionId", sessionId)
    			//.withClaim("userName", userName.toUpperCase())
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET));
    	logger.info("token "+token);
    	logger.info("Json Web Token created successfully for client: {}",request.getClientId());
    	return  new TokenResponse(token,"bearer", SecurityConstants.EXPIRATION_TIME);
    } 
}
