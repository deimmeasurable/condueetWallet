package com.example.condueetwallet.Dtos.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
    private String firstName;
    private String lastName;
    private String email;
}