package com.tuke.fei.kpi.isvote.modules.vote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class VotePageDto {

    private List<VoteDto> data;
    private Long totalCount;

    public VotePageDto(Page<com.tuke.fei.kpi.isvote.modules.vote.model.Vote> page, Function<com.tuke.fei.kpi.isvote.modules.vote.model.Vote, VoteDto> mapper) {
        this.data = page.getContent().stream().map(mapper).collect(Collectors.toList());
        this.totalCount = page.getTotalElements();
    }
}
