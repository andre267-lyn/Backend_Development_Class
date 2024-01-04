package com.ideaco.ewallet.service;

import com.ideaco.ewallet.dto.BalanceDTO;
import com.ideaco.ewallet.exception.UserNotFoundException;
import com.ideaco.ewallet.model.BalanceModel;
import com.ideaco.ewallet.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;
    public BalanceDTO showBalance(int userId) throws UserNotFoundException {
        Optional<BalanceModel> balanceModelOptional = balanceRepository.findById(userId);
        if (balanceModelOptional.isEmpty()) {
            throw new UserNotFoundException("Receiver not exist");
        }
        BalanceModel balanceModel = balanceModelOptional.get();
        return convertDTO(balanceModel);
    }

    public BalanceDTO convertDTO(BalanceModel balanceModel) {
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setUserId(balanceModel.getUserId());
        balanceDTO.setBalance(balanceModel.getBalance());
        return balanceDTO;
    }
}
