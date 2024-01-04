package com.ideaco.ewallet.repository;

import com.ideaco.ewallet.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {
    List<TransactionModel> findByTransactionSenderOrTransactionReceiver(int userId, int userId1);
}
