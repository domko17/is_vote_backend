package com.tuke.fei.kpi.isvote.modules.vote.controller;

import com.tuke.fei.kpi.isvote.modules.vote.dto.*;
import com.tuke.fei.kpi.isvote.modules.vote.model.Vote;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tuke.fei.kpi.isvote.modules.vote.service.VoteService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/create-vote")
    public VoteDto createVote(@Valid @RequestBody VoteCreateDto voteCreateDto) {
        return voteService.createVote(voteCreateDto);
    }

    @PutMapping("/update-state")
    public VoteDto updateState(@Valid @RequestBody VoteStateDto voteStateDto) {
        return voteService.updateState(voteStateDto);
    }


    @PutMapping("/update-vote/{id}")
    public VoteDto updateVote(@PathVariable String id, @Valid @RequestBody VoteCreateDto voteCreateDto) {
        return voteService.updateVote(id, voteCreateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable String id) {
        voteService.deleteVote(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public VoteDto getVote(@PathVariable String id) {
        return voteService.getVote(id);
    }

    @GetMapping("/all")
    public List<Vote> getAllVotes() {
        return voteService.getAll();
    }

    @GetMapping("/by-user")
    public List<VotesByUserDto> getAllVotesOfUser() {
        return voteService.getAllByUser();
    }

    @GetMapping("/paginated")
    public VotePageDto getAllPaginatedVoteOfUser(@RequestParam Integer page, @RequestParam Integer size) {
        return voteService.getAllPaginatedVoteOfUser(page, size);
    }

}
