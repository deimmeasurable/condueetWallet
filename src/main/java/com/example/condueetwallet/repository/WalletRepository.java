package com.example.condueetwallet.repository;

import com.example.condueetwallet.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findWalletByUserName(String email);

}
