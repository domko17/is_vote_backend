package com.tuke.fei.kpi.isvote.modules.vote.dto;

import com.tuke.fei.kpi.isvote.modules.common.dto.UserUpdateDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VoteCreateDto {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String contentVote;

    @Size(max = 255)
    private String note;

    @NotNull
    private LocalDateTime term;

    @NotNull
    private LocalDateTime duration;

    @NotNull
    private Integer typeVote;

    @NotNull
    private Integer activeVoting;

    @NotNull
    private List<String> answersVote;

    private UserUpdateDto users;
}