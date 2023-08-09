package com.tuke.fei.kpi.isvote.modules.common.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserCreateDto {

    @NotNull
    private String userId;

    @NotNull
    private String userType;

    @NotNull
    private String mail;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;


}
