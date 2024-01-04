package com.ideaco.ewallet.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TopupDTO {
    private int userId;
    private double amount;
}
