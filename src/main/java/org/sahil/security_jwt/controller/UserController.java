package org.sahil.security_jwt.controller;

import org.sahil.security_jwt.dao.AuthenticationResponse;
import org.sahil.security_jwt.dao.LoginRequest;
import org.sahil.security_jwt.entity.UserEntity;
import org.sahil.security_jwt.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
   /* @Autowired
    private UserSservice userService;*/
    @Autowired
    private AuthenticationService authService;
    @PostMapping("jwt/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }
    @PostMapping("jwt/register")
    public ResponseEntity<AuthenticationResponse> add(@RequestBody UserEntity user){

        return ResponseEntity.ok(authService.register(user));
    }
    @GetMapping("/loggedin")
    public ResponseEntity<String> loggedIn(){
        return ResponseEntity.ok("WELCOME TO e-Store!!");
    }

}
