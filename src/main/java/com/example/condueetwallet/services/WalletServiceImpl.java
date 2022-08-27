package com.example.condueetwallet.services;

import com.example.condueetwallet.Dtos.reponse.CreateWalletResponse;
import com.example.condueetwallet.Dtos.reponse.CreditWalletResponse;
import com.example.condueetwallet.Dtos.reponse.InitializeTransactionResponse;
import com.example.condueetwallet.Dtos.request.CreditWalletRequest;
import com.example.condueetwallet.Dtos.request.InitTransactionRequest;
import com.example.condueetwallet.Dtos.request.WalletCreateWalletRequest;
import com.example.condueetwallet.exceptions.WalletException;
import com.example.condueetwallet.models.Wallet;
import com.example.condueetwallet.repository.WalletRepository;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
//@AllArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {
    private static final int STATUS_CODE_OK = 201;
    @Autowired
    private WalletRepository walletRepository;
    @Value("${payStack_Key}")
    private String paystackKey;

    @Value("https://api.paystack.co/transaction/initialize")
    private String payStack_Url;

//public WalletServiceImpl(WalletRepository walletRepository){
//    this.walletRepository=walletRepository;
//}

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CreateWalletResponse createWallet(WalletCreateWalletRequest request) {
        Wallet wallet = new Wallet();
        wallet.setUserName(request.getUserName());
        wallet.setPin(request.getPin());
        log.info("this is the username : " + wallet.getUserName());
        wallet.setBalance(request.getBalance());
//        walletRepository.save(wallet);
        walletRepository.save(wallet);

        CreateWalletResponse response = new CreateWalletResponse();
        response.setUsername(wallet.getUserName());

        return response;
    }

    @Override
    public CreditWalletResponse creditWalletByUser(CreditWalletRequest creditWalletRequest) {
        log.info("Wallet id is {}->", creditWalletRequest.getWalletId());
        log.info("Wallet is {}->", walletRepository.findById(creditWalletRequest.getWalletId()));
        Wallet foundWallet = walletRepository.findById(creditWalletRequest.getWalletId()).orElseThrow(
                () -> new WalletException("wallet not found")
        );
        InitTransactionRequest request = new InitTransactionRequest();


        Map<?, ?> response = initializePayStackTransaction(creditWalletRequest.getEmail(), creditWalletRequest.getAmount());

        foundWallet.setBalance(creditWalletRequest.getAmount());
        CreditWalletResponse creditWalletResponse = CreditWalletResponse.builder()
                .message("")
                .reference("")
                .status(true)
                .authorizationUrl("")
                .build();


        creditWalletResponse.setMessage(creditWalletResponse.getMessage());


        return creditWalletResponse;

    }

//amount
//e

//
//    private String connectToExternalApi(CreditWalletRequest request2) throws JsonProcessingException {
////        String url="https://api.paystack.co/transaction/initialize";
//// CreditWalletRequest request2 = new CreditWalletRequest();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept", "application/json");
//        headers.set("Content-Type", "application/json");
//        headers.set("Authorization", String.format("Bearer %s", paystackKey));
//        HttpEntity<CreditWalletRequest> entity = new HttpEntity<>(request2, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(payStack_Url, HttpMethod.POST, entity,String.class);
//        if (response.getStatusCode() != HttpStatus.OK){
//            throw new WalletException("Transaction Failed");
//        }
//        ObjectMapper mapper = new ObjectMapper();
//       Map<String, Object> data = mapper.readValue(response.getBody(), Map.class);
//        var referenceCode = (String)data.get("reference");
//        var verify_url = String.format("https://api.paystack.co/transaction/verify/%s", referenceCode);
//        HttpEntity<Void> entity2 = new HttpEntity<>(headers);
//        ResponseEntity<String> response2 = restTemplate.exchange(verify_url, HttpMethod.GET, entity2, String.class);
//        return String.valueOf(response);
//    }

    private Map<?, ?> initializePayStackTransaction(String email, BigDecimal amount) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", String.format("Authorization", "pk_test_3516f5841239ecb4cd97b51b973b6c9de0086967"));
        System.out.println(headers);
        HttpEntity<Map> entity = new HttpEntity<>(headers);

        System.out.println(entity.getHeaders());
        System.out.println(entity.getBody());
        System.out.println(entity.getBody());
        var response = restTemplate.exchange(payStack_Url, HttpMethod.POST, entity, Map.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new WalletException("Transaction Failed");
        }


        return response.getBody();

    }


}
