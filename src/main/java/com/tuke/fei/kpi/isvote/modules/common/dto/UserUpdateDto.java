package com.tuke.fei.kpi.isvote.modules.common.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
public class UserUpdateDto {
    @NotNull
    @NotBlank
    private List<String> id;
}
