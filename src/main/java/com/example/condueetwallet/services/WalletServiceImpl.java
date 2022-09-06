package com.example.condueetwallet.services;

import com.example.condueetwallet.Dtos.reponse.CreateWalletResponse;
import com.example.condueetwallet.Dtos.reponse.CreditWalletResponse;

import com.example.condueetwallet.Dtos.request.CreditWalletRequest;
import com.example.condueetwallet.Dtos.request.InitTransactionRequest;
import com.example.condueetwallet.Dtos.request.WalletCreateWalletRequest;
import com.example.condueetwallet.exceptions.WalletException;
import com.example.condueetwallet.models.Wallet;
import com.example.condueetwallet.repository.WalletRepository;

import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Map;

@Service

@Slf4j
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;
    @Value("${payStack_Key}")
    private String paystackKey;

    @Value("https://api.paystack.co/transaction/initialize")
    private String payStack_Url;

//   private String payStack_secret_key;



    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CreateWalletResponse createWallet(WalletCreateWalletRequest request) {
        Wallet wallet = new Wallet();
        wallet.setUserName(request.getUserName());
        wallet.setPin(request.getPin());
        log.info("this is the username : " + wallet.getUserName());
        wallet.setBalance(request.getBalance());

        walletRepository.save(wallet);

        CreateWalletResponse response = new CreateWalletResponse();
        response.setUsername(wallet.getUserName());

        return response;
    }

    @Override
    public CreditWalletResponse creditWalletByUser(InitTransactionRequest creditWalletRequest) {
        log.info("Wallet id is {}->", creditWalletRequest.getWalletId());
        log.info("Wallet is {}->", walletRepository.findById(creditWalletRequest.getWalletId()));
        Wallet foundWallet = walletRepository.findById(creditWalletRequest.getWalletId()).orElseThrow(
                () -> new WalletException("wallet not found")
        );
        InitTransactionRequest request = new InitTransactionRequest();


        Map<?, ?> response = initializePayStackTransaction(creditWalletRequest.getEmail(), creditWalletRequest.getAmount());

        foundWallet.setBalance(creditWalletRequest.getAmount());
        CreditWalletResponse creditWalletResponse = CreditWalletResponse.builder()
                .message("Authorization URL created")
                .reference("")
                .status(true)
                .authorizationUrl("")
                .build();


        creditWalletResponse.setMessage(creditWalletResponse.getMessage());


        return creditWalletResponse;

    }


    private Map<?, ?> initializePayStackTransaction(String email, BigDecimal amount) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", String.format("Bearer "+"sk_test_c1d5365730b60936c6dfb12f713e5a5016717a60"));
        System.out.println(headers);
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("amount", String.valueOf(amount));
        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(body,headers);

        System.out.println(entity.getHeaders());

        UriComponentsBuilder builderUrl= UriComponentsBuilder.fromHttpUrl(payStack_Url);

        System.out.println(entity);
        System.out.println(payStack_Url);
       var response = restTemplate.exchange(builderUrl.build().encode().toUri(), HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new WalletException("Transaction Failed");
        }


        return response.getBody();

    }



}
