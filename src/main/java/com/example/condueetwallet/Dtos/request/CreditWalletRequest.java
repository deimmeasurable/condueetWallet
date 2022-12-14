package com.example.condueetwallet.Dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreditWalletRequest {
    private BigDecimal amount;
    private String email;
    private Long walletId;
    private String currency;
}
