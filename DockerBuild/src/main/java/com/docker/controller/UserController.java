package com.docker.controller;


import com.docker.dto.AuthRequest;

import com.docker.model.User;
import com.docker.security.JwtService;
import com.docker.service.UserService;
import org.antlr.v4.runtime.misc.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authManager;

    @Autowired
    private UserService service;
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody User user){
        String message=service.CreateUser(user);
        if(message.equals("user saved successfully")){
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(404).body(message);
    }
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody  User user){
        String message=service.updateUser(user);
        if(message.equals("user updated successfully") || message.equals("no update made")){
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(404).body(message);
    }
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username){
        String message=service.delete(username);
        if(message.equals("user deleted successfully")){
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(404).body(message);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authreRuest){
    	Authentication auth=authManager.authenticate(new UsernamePasswordAuthenticationToken(authreRuest.getUsername(), authreRuest.getPassword()));
    	String token=jwtService.generateToken(authreRuest.getUsername());
    	return ResponseEntity.ok(token);
    }

}
