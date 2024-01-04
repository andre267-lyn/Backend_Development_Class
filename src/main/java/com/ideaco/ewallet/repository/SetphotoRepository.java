package com.ideaco.ewallet.repository;

import com.ideaco.ewallet.model.BalanceModel;
import com.ideaco.ewallet.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SetphotoRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUserId(int userId);
}