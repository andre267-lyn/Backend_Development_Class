package com.ideaco.ewallet.response;

import com.ideaco.ewallet.dto.SetPhotoDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SetPhotoResponse extends BaseResponse{
    private SetPhotoDTO data;
}
