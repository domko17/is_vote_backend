package com.tuke.fei.kpi.isvote.modules.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.tuke.fei.kpi.isvote.modules.ApiException.FaultType;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto {
    private FaultType faultType;
    private String message;
}
