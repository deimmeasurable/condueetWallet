package com.example.condueetwallet.services;

import com.example.condueetwallet.Dtos.request.CreateUserRequest;
import com.example.condueetwallet.Dtos.reponse.CreateUserResponse;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest createUserRequest);
}
