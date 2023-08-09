package com.tuke.fei.kpi.isvote.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;
import com.tuke.fei.kpi.isvote.modules.common.enums.UserType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateUserDto {

    private String title;

    @NotNull
    @NotBlank
    private String firstname;

    @NotNull
    @NotBlank
    private String lastname;

    @NotNull
    @NotBlank
    private String mail;

    private UserType userType;

    private String userName;
}
