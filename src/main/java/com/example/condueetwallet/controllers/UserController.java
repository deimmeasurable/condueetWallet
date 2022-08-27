package com.example.condueetwallet.controllers;

import com.example.condueetwallet.Dtos.reponse.ApiResponse;
import com.example.condueetwallet.Dtos.reponse.CreateUserResponse;
import com.example.condueetwallet.Dtos.request.CreateUserRequest;
import com.example.condueetwallet.exceptions.UserAlreadyExistException;
import com.example.condueetwallet.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/condueetWallet")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/user/Register")
    public ResponseEntity<?>createUser(@RequestBody CreateUserRequest createUserRequest)throws UserAlreadyExistException{
        try{
            CreateUserResponse createUserResponse = userService.createUser(createUserRequest);
            ApiResponse response = ApiResponse.builder()
                    .isSuccessFull(true)
                    .statusCode(200)
                    .message("user created Successfully")
                    .payLoad(createUserResponse)
                    .build();

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(UserAlreadyExistException e){
            ApiResponse response =  ApiResponse.builder()
                    .message(e.getMessage())
                    .isSuccessFull(false)
                    .statusCode(400)
                    .build();
            return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }



    }
}
