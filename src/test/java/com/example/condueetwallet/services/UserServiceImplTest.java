package com.example.condueetwallet.services;

import com.example.condueetwallet.Dtos.request.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    void setup(){
        userService= new UserServiceImpl();

    }
    @Test
    void createUser(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setFirstName("john");
        createUserRequest.setLastName("shola");
        createUserRequest.setEmail("fishing123@gmail.com");

        userService.createUser(createUserRequest);

        assertEquals(createUserRequest.getLastName(), "shola");
        assertEquals(createUserRequest.getEmail(), "fishing123@gmail.com");



    }

}