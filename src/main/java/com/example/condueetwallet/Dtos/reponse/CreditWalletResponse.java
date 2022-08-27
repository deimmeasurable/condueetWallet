package com.example.condueetwallet.Dtos.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreditWalletResponse {
    private String message;
    private boolean status;
    private String reference;
    private String authorizationUrl;
}
