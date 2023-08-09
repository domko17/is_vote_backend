package com.tuke.fei.kpi.isvote.modules.vote.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VoteUpdateDto extends VoteCreateDto {

    @NotNull
    @NotBlank
    private String id;
}
