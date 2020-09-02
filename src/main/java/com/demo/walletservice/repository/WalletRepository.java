package com.demo.walletservice.repository;

import com.demo.walletservice.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("SELECT w from Wallet w WHERE w.phoneNumber = :phoneNumber ")
    Optional<Wallet> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
