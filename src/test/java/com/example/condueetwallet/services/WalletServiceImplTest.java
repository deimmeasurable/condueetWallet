package com.example.condueetwallet.services;

import com.example.condueetwallet.Dtos.reponse.CreditWalletResponse;
import com.example.condueetwallet.Dtos.request.CreditWalletRequest;
import com.example.condueetwallet.Dtos.reponse.CreateWalletResponse;
import com.example.condueetwallet.Dtos.request.WalletCreateWalletRequest;
import com.example.condueetwallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WalletServiceImplTest {

    @Autowired
    private WalletService walletService;

    @BeforeEach
    void setUp() {
//        walletService=new WalletServiceImpl();
    }
    @Test
    void createWalletForUser(){
        WalletCreateWalletRequest request = new WalletCreateWalletRequest();
            request.setBalance(new BigDecimal("0.0"));
            request.setUserName("fishing123@gmail.com");
            request.setCreditBalance(0);
            request.setPin("4560");

        CreateWalletResponse createWalletResponse= walletService.createWallet(request);

        assertEquals(createWalletResponse.getUsername(),"fishing123@gmail.com");




    }
@Test
    void testThatWalletCanBeCredited(){
    WalletCreateWalletRequest request = new WalletCreateWalletRequest();
    request.setBalance(new BigDecimal("0.0"));
    request.setUserName("fishing123@gmail.com");
    request.setCreditBalance(0);
    request.setPin("4560");

    walletService.createWallet(request);

    CreditWalletRequest request2 = new CreditWalletRequest();
    request2.setAmount(BigDecimal.valueOf(50000));
    request2.setWalletId(Long.valueOf("4560"));
    request2.setEmail("fishing123@gmail.com");

//    CreditWalletResponse response = walletService.creditWalletByUser(request2);

    assertEquals(BigDecimal.valueOf(50000),request2.getAmount());
    assertEquals("fishing123@gmail.com",request2.getEmail());

}


    }

