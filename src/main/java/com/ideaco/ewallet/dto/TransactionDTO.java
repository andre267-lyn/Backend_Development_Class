package com.ideaco.ewallet.dto;

import java.time.LocalDateTime;

public class TransactionDTO {
    private int transactionId;
    private int transactionSender;
    private int transactionReceiver;
    private int transactionAmount;
    private LocalDateTime transactionTime;
    private String transactionType;
    private String transactionStatus;
    private String senderName;
    private String receiverName;
}
