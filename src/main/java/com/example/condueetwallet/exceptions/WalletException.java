package com.example.condueetwallet.exceptions;



public class WalletException extends UserAlreadyExistException {
    public WalletException(String message) {
        super(message);
    }
}
