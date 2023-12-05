package com.example.User.controller;

import com.example.User.dto.LoginDto;
import com.example.User.service.UserService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private Environment env;
    private UserService userService;

    public UserController(Environment env, UserService userService) {
        this.env=env;
        this.userService=userService;
    }
    @GetMapping("/welcome")
    public String getWelcome(){
        return "hello";
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        try{
            System.out.print("helllo");
            return ResponseEntity.ok(userService.login(loginDto));
        }
        catch(com.example.numble_insta.exception.ExistUserException e){
            com.example.numble_insta.exception.ExceptionResponse exceptionResponse = new com.example.numble_insta.exception.ExceptionResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exceptionResponse);
        }
    }
}
