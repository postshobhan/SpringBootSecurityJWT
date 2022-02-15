package com.example.springboot.security_v2.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.security_v2.model.JWTRequest;
import com.example.springboot.security_v2.model.JWTResponse;
import com.example.springboot.security_v2.service.MyUserDetailsServiceImpl;
import com.example.springboot.security_v2.util.JWTUtility;

@RestController
public class AuthApi {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private MyUserDetailsServiceImpl myUserDetailsServiceImpl;
	
    @GetMapping("/")
    public String sayHello(){
        return "Hello world";
    }
    
    @GetMapping("/user")
    public String sayHelloUser(){
        return "Hello User";
    }
    
    @GetMapping("/admin")
    public String sayHelloAdmin(){
        return "Hello Admin";
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<JWTResponse>  autheticate(@RequestBody JWTRequest jwtRequest) throws Exception{
    	
    	try {
    	authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(
    					jwtRequest.getUserName(), 
    					jwtRequest.getPassword()));
    	}catch(BadCredentialsException e) {
    		
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    		//throw new Exception("Invalid Credentials", e);
    	}
    	
    	final UserDetails userDetails
    		= myUserDetailsServiceImpl.loadUserByUsername(jwtRequest.getUserName());
    	
    	final String token = jwtUtility.generateToken(userDetails);
    	
    	
    	return ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,
                    "Bearer " + token
                )
                .body(new JWTResponse(token));
    	
    	
    }
    
}
