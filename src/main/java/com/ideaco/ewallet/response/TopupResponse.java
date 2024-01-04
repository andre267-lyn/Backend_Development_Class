package com.ideaco.ewallet.response;

import com.ideaco.ewallet.dto.BalanceDTO;
import com.ideaco.ewallet.dto.TopupDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TopupResponse {
    private TopupDTO data;
}