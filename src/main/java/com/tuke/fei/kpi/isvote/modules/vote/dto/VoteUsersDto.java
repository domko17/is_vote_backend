package com.tuke.fei.kpi.isvote.modules.vote.dto;

import com.tuke.fei.kpi.isvote.modules.common.dto.UserDto;
import com.tuke.fei.kpi.isvote.modules.common.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class VoteUsersDto {


    @NotNull
    @NotBlank
    private UserDto user;

}



