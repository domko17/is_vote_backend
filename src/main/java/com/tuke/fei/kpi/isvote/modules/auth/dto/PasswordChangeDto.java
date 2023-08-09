package com.tuke.fei.kpi.isvote.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PasswordChangeDto {

    @NotNull
    private String newPassword;

    @NotNull
    private String oldPassword;
}
