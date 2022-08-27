package com.example.condueetwallet.Dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletCreateWalletRequest {
    private BigDecimal Balance;
    private String pin;
    private String userName;
    private int creditBalance;
}
