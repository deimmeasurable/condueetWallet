package com.example.condueetwallet.controllers;

import com.example.condueetwallet.Dtos.reponse.ApiResponse;
import com.example.condueetwallet.Dtos.reponse.CreditWalletResponse;

import com.example.condueetwallet.Dtos.request.CreditWalletRequest;
import com.example.condueetwallet.Dtos.request.InitTransactionRequest;
import com.example.condueetwallet.exceptions.WalletException;
import com.example.condueetwallet.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/condueetWallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }
//    @GetMapping("/wallet/{email}/{pin}")
//    public ResponseEntity<?> creditWalletByUser(CreditWalletRequest.@PathVariable String email)

    @PostMapping("/wallet/creditWallet")
    public ResponseEntity<?> creditWalletByUser(@RequestBody InitTransactionRequest initTransactionRequest){
        try{

            CreditWalletResponse response= walletService.creditWalletByUser(initTransactionRequest);
            ApiResponse apiResponse= ApiResponse.builder()
                    .isSuccessFull(true)
                    .statusCode(200)
                    .message("transaction successfull")
                    .payLoad(response)
                    .build();
            return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

        }catch (WalletException e){
            ApiResponse apiResponse= ApiResponse.builder()
                    .message(e.getMessage())
                    .isSuccessFull(false)
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
