package com.example.condueetwallet.Dtos.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private Object payLoad;
    private boolean isSuccessFull;
    private int statusCode;
    private String message;

}
