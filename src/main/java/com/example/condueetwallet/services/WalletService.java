package com.example.condueetwallet.services;

import com.example.condueetwallet.Dtos.reponse.CreateWalletResponse;
import com.example.condueetwallet.Dtos.reponse.CreditWalletResponse;
import com.example.condueetwallet.Dtos.request.CreditWalletRequest;
import com.example.condueetwallet.Dtos.request.InitTransactionRequest;
import com.example.condueetwallet.Dtos.request.WalletCreateWalletRequest;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {
   CreateWalletResponse createWallet(WalletCreateWalletRequest request);



   CreditWalletResponse creditWalletByUser(InitTransactionRequest initializePayStackTransaction) throws Exception;
}
