package com.example.condueetwallet.services;

import com.example.condueetwallet.Dtos.request.CreateUserRequest;
import com.example.condueetwallet.Dtos.reponse.CreateUserResponse;
import com.example.condueetwallet.Dtos.request.WalletCreateWalletRequest;
import com.example.condueetwallet.exceptions.UserAlreadyExistException;
import com.example.condueetwallet.models.User;
import com.example.condueetwallet.models.Wallet;
import com.example.condueetwallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletService walletService;


    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        if(checkIfUserAlreadyExist(createUserRequest.getEmail())) throw new UserAlreadyExistException("user with this email or id already exist"+createUserRequest.getEmail());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());

        userRepository.save(user);

        WalletCreateWalletRequest walletCreateWalletRequest= new WalletCreateWalletRequest();
        walletCreateWalletRequest.setUserName(createUserRequest.getEmail());
        walletService.createWallet(walletCreateWalletRequest);

        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setEmail(user.getEmail());
        createUserResponse.setFirstName(user.getFirstName());
        createUserResponse.setLastName(user.getLastName());

        return createUserResponse;
    }
    private boolean checkIfUserAlreadyExist(String email){
        return  userRepository.findUserByEmail(email).isPresent();
    }
}
