package com.ideaco.ewallet.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class RequestMoneyDTO {
    private int requesterId;
    private int targetUserId;
    private double amount;
}
