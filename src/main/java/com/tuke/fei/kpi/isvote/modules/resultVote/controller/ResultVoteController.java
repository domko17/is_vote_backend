package com.tuke.fei.kpi.isvote.modules.resultVote.controller;

import com.tuke.fei.kpi.isvote.modules.resultVote.dto.ResultVoteCreateDto;
import com.tuke.fei.kpi.isvote.modules.resultVote.dto.ResultVoteDto;
import com.tuke.fei.kpi.isvote.modules.resultVote.model.ResultVote;
import com.tuke.fei.kpi.isvote.modules.resultVote.service.ResultVoteService;
import com.tuke.fei.kpi.isvote.modules.vote.dto.VoteCreateDto;
import com.tuke.fei.kpi.isvote.modules.vote.dto.VoteDto;
import com.tuke.fei.kpi.isvote.modules.vote.dto.VotePageDto;
import com.tuke.fei.kpi.isvote.modules.vote.model.Vote;
import com.tuke.fei.kpi.isvote.modules.vote.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/result-vote")
public class ResultVoteController {


    private final ResultVoteService resultVoteService;

    public ResultVoteController(ResultVoteService resultVoteService) {
        this.resultVoteService = resultVoteService;
    }

    @PostMapping("/create-result-vote")
    public ResultVoteDto createResultVote(@Valid @RequestBody ResultVoteCreateDto resultVoteCreateDto) {
        return resultVoteService.createResultVote(resultVoteCreateDto);
    }

    @PutMapping("/update-result-vote/{id}")
    public ResultVoteDto updateResultVote(@PathVariable String id, @Valid @RequestBody ResultVoteCreateDto resultVoteCreateDto) {
        return resultVoteService.updateResultVote(id, resultVoteCreateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResultVote(@PathVariable String id) {
        resultVoteService.deleteResultVote(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResultVoteDto getResultVote(@PathVariable String id) {
        return resultVoteService.getResultVote(id);
    }

    @GetMapping("/all")
    public List<ResultVote> getAllResultVotes() {
        return resultVoteService.getAll();
    }

    @GetMapping("/all-of-user")
    public List<ResultVoteDto> getAllResultVotesOfUser() {
        return resultVoteService.getAllResultVotesOfUser();
    }

    @GetMapping("/all-of-vote/{voteId}")
    public List<ResultVoteDto> getAllResultVotesOfVote(@PathVariable String voteId) {
        return resultVoteService.getAllResultVotesOfVote(voteId);
    }


}
