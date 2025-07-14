package com.docker.controller;


import com.docker.model.User;
import com.docker.service.UserService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

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


}
