package com.ideaco.ewallet.service;

import com.ideaco.ewallet.dto.BalanceDTO;
import com.ideaco.ewallet.dto.EditEmailDTO;
import com.ideaco.ewallet.dto.RequestMoneyDTO;
import com.ideaco.ewallet.dto.SetPhotoDTO;
import com.ideaco.ewallet.exception.InsufficientFundsException;
import com.ideaco.ewallet.exception.UserNotFoundException;
import com.ideaco.ewallet.model.UserModel;
import com.ideaco.ewallet.repository.SetphotoRepository;
import com.ideaco.ewallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileService fileService;

    public EditEmailDTO editUserEmail(int userId, String newEmail) throws UserNotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);
        if(userModelOptional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }

        UserModel userModel = userModelOptional.get();
        userModel.setUserEmail(newEmail);

        userRepository.save(userModel);

        return convertDTO(userModel);
    }

    public EditEmailDTO convertDTO(UserModel userModel){
        EditEmailDTO editEmailDTO = new EditEmailDTO();
        editEmailDTO.setUserId(userModel.getUserId());
        editEmailDTO.setUserEmail(userModel.getUserEmail());
        return editEmailDTO;
    }

    @Autowired
    private BalanceService balanceService;
    public BalanceDTO showBalance(int userId) throws UserNotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);
        if (userModelOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        try {
            return balanceService.showBalance(userId);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    public BalanceDTO topUpBalance(int userId, double amount) throws UserNotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);
        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            double currentBalance = userModel.getBalance(); // Asumsikan ada field balance di UserModel
            double newBalance = currentBalance + amount;
            userModel.setBalance(newBalance); // Update saldo pada UserModel
            userRepository.save(userModel);
            return balanceService.showBalance(userId);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public SetPhotoDTO setUserPhoto(int userId, MultipartFile userPicture) throws UserNotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);
        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            String filename = fileService.saveFile(userPicture);
            userModel.setUserPicture(filename);
            userRepository.save(userModel);
            return convertPhotoDTO(userModel);
        } else {
            throw new UserNotFoundException("user not found");

        }
    }

    public SetPhotoDTO convertPhotoDTO (UserModel userModel){
        SetPhotoDTO setPhotoDTO = new SetPhotoDTO();
        setPhotoDTO.setUserId(userModel.getUserId());
        setPhotoDTO.setUserPicture(userModel.getUserPicture());
        return setPhotoDTO;
    }

    public void requestMoney(RequestMoneyDTO request) throws UserNotFoundException, InsufficientFundsException {
        Optional<UserModel> requesterOptional = userRepository.findById(request.getRequesterId());
        Optional<UserModel> targetOptional = userRepository.findById(request.getTargetUserId());

        if (requesterOptional.isPresent() && targetOptional.isPresent()) {
            UserModel requester = requesterOptional.get();
            UserModel target = targetOptional.get();

            double requesterBalance = requester.getBalance();
            double amount = request.getAmount();

            if (requesterBalance >= amount) {
                double newRequesterBalance = requesterBalance - amount;
                double newTargetBalance = target.getBalance() + amount;

                requester.setBalance(newRequesterBalance);
                target.setBalance(newTargetBalance);

                userRepository.save(requester);
                userRepository.save(target);
            } else {
                throw new InsufficientFundsException("Insufficient funds");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
}
