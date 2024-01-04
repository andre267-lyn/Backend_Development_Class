package com.ideaco.ewallet.controller;

import com.ideaco.ewallet.dto.BalanceDTO;
import com.ideaco.ewallet.dto.EditEmailDTO;
import com.ideaco.ewallet.dto.RequestMoneyDTO;
import com.ideaco.ewallet.dto.SetPhotoDTO;
import com.ideaco.ewallet.exception.InsufficientFundsException;
import com.ideaco.ewallet.exception.SetphotoNotFoundException;
import com.ideaco.ewallet.exception.UserNotFoundException;
import com.ideaco.ewallet.response.BalanceResponse;
import com.ideaco.ewallet.response.EditEmailResponse;
import com.ideaco.ewallet.response.SetPhotoResponse;
import com.ideaco.ewallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PatchMapping("/{user_id}/email")
    public ResponseEntity<EditEmailResponse> editEmail(@PathVariable("user_id") int userId,
                                                       @RequestParam("user_email") String userEmail){
        EditEmailResponse editEmailResponse = new EditEmailResponse();
        try {
            EditEmailDTO editEmailDTO = userService.editUserEmail(userId, userEmail);

            editEmailResponse.setSuccess(true);
            editEmailResponse.setMessage("Successfully changed user email");
            editEmailResponse.setErrorCode("");
            editEmailResponse.setData(editEmailDTO);

            return ResponseEntity.ok().body(editEmailResponse);
        } catch (UserNotFoundException e) {
            editEmailResponse.setSuccess(false);
            editEmailResponse.setMessage("Failed to changed user email");
            editEmailResponse.setErrorCode("400");

            return ResponseEntity.badRequest().body(editEmailResponse);
        }
    }


    @PatchMapping("/set-photo")
    public ResponseEntity<SetPhotoResponse> setPhoto(@RequestParam("user_id") int userId,
                                                     @RequestParam("user_picture") MultipartFile userPicture) {
        SetPhotoResponse setPhotoResponse = new SetPhotoResponse();
        try {
            SetPhotoDTO setPhotoDTO = userService.setUserPhoto(userId, userPicture);

            setPhotoResponse.setSuccess(true);
            setPhotoResponse.setMessage("Successfully changed user picture");
            setPhotoResponse.setErrorCode("");
            setPhotoResponse.setData(setPhotoDTO);
            return ResponseEntity.ok().body(setPhotoResponse);
        } catch (UserNotFoundException e) {
            setPhotoResponse.setSuccess(false);
            setPhotoResponse.setMessage("Failed to change user picture");
            setPhotoResponse.setErrorCode("400");

            return ResponseEntity.badRequest().body(setPhotoResponse);
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> showBalance(@RequestParam("user_id") int userId) {
        BalanceResponse balanceResponse = new BalanceResponse();
        try {
            BalanceDTO balanceDTO = userService.showBalance(userId);

            balanceResponse.setSuccess(true);
            balanceResponse.setMessage("Successfully show balance");
            balanceResponse.setErrorCode("");
            balanceResponse.setData(balanceDTO);

            return ResponseEntity.ok().body(balanceResponse);
        } catch (UserNotFoundException e) {
            balanceResponse.setSuccess(false);
            balanceResponse.setMessage("Failed to show balance");
            balanceResponse.setErrorCode("110");

            return ResponseEntity.badRequest().body(balanceResponse);
        }
    }


    @PostMapping("/{user_id}/top-up")
    public ResponseEntity<BalanceResponse> topUpBalance(@PathVariable("user_id") int userId,
                                                        @RequestParam("amount") double amount) {
        BalanceResponse balanceResponse = new BalanceResponse();
        try {
            BalanceDTO balanceDTO = userService.topUpBalance(userId, amount);

            balanceResponse.setSuccess(true);
            balanceResponse.setMessage("Successfully topped up balance");
            balanceResponse.setErrorCode("");
            balanceResponse.setData(balanceDTO);

            return ResponseEntity.ok().body(balanceResponse);
        } catch (UserNotFoundException e) {
            balanceResponse.setSuccess(false);
            balanceResponse.setMessage("Failed to top up balance");
            balanceResponse.setErrorCode("110");

            return ResponseEntity.badRequest().body(balanceResponse);
        }
    }

    @PostMapping("/{user_id}/request-money")
    public ResponseEntity<String> requestMoney(@PathVariable("user_id") int requesterId,
                                               @RequestParam("target_user_id") int targetUserId,
                                               @RequestParam("amount") double amount) {
        try {
            RequestMoneyDTO request = new RequestMoneyDTO();
            request.setRequesterId(requesterId);
            request.setTargetUserId(targetUserId);
            request.setAmount(amount);

            userService.requestMoney(request);

            return ResponseEntity.ok("Money request sent successfully");
        } catch (UserNotFoundException | InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
