package com.tuke.fei.kpi.isvote.modules.resultVote.service;

import com.tuke.fei.kpi.isvote.modules.ApiException;
import com.tuke.fei.kpi.isvote.modules.common.mapper.CommonMapper;
import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.common.service.SecurityService;
import com.tuke.fei.kpi.isvote.modules.resultVote.dto.ResultVoteCreateDto;
import com.tuke.fei.kpi.isvote.modules.resultVote.dto.ResultVoteDto;
import com.tuke.fei.kpi.isvote.modules.resultVote.model.ResultVote;
import com.tuke.fei.kpi.isvote.modules.vote.model.Vote;
import com.tuke.fei.kpi.isvote.modules.vote.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.tuke.fei.kpi.isvote.modules.resultVote.repository.ResultVoteRepository;
import org.springframework.data.domain.Sort;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ResultVoteService {

    private final ResultVoteRepository resultVoteRepository;
    private final VoteRepository voteRepository;
    private final CommonMapper commonMapper;
    private final SecurityService securityService;

    public ResultVoteService(ResultVoteRepository resultVoteRepository, VoteRepository voteRepository, CommonMapper commonMapper, SecurityService securityService) {
        this.resultVoteRepository = resultVoteRepository;
        this.voteRepository = voteRepository;
        this.commonMapper = commonMapper;
        this.securityService = securityService;
    }

    public String generateString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_?*!";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public ResultVoteDto createResultVote(ResultVoteCreateDto resultVoteCreateDto) {
        User loggedUser = securityService.getLoggedUser();
        log.info("Creating new ResultVore for user {}", loggedUser.getId());
        ResultVote resultVote = commonMapper.resultVoteCreateDtoToResultVote(resultVoteCreateDto);
        resultVote.setCreatedAt(LocalDateTime.now());
        resultVote.setUser(loggedUser);
        resultVote.setVote(voteRepository.findById(resultVoteCreateDto.getVoteId()).get());
        resultVote.setSecretName(this.generateString(5));
        resultVoteRepository.save(resultVote);
        return commonMapper.resultVoteToResultVoteDto(resultVote);
    }

    public ResultVoteDto updateResultVote(String id, ResultVoteCreateDto resultVoteCreateDto) {
        log.info("Updating ResultVote: {}", id);
        ResultVote resultVote = getResultVoteEntity(id);
        commonMapper.updateResultVoteFromResultVoteCreateDto(resultVoteCreateDto, resultVote);
        resultVoteRepository.save(resultVote);
        return commonMapper.resultVoteToResultVoteDto(resultVote);
    }


    public void deleteResultVote(String id) {
        log.info("Deleting ResultVote: {}", id);
        resultVoteRepository.findById(id).ifPresent(resultVoteRepository::delete);
    }


    public List<ResultVoteDto> getAllResultVotesOfUser() {
        User loggedUser = securityService.getLoggedUser();

        return resultVoteRepository.findAllByUser(loggedUser)
                .stream()
                .map(commonMapper::resultVoteToResultVoteDto)
                .collect(Collectors.toList());
    }

    public List<ResultVoteDto> getAllResultVotesOfVote(String voteId) {
        Vote vote = voteRepository.getOne(voteId);

        return resultVoteRepository.findAllByVote(vote)
                .stream()
                .map(commonMapper::resultVoteToResultVoteDto)
                .collect(Collectors.toList());
    }


    public List<ResultVote> getAll() {
        return resultVoteRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public ResultVoteDto getResultVote(String id) {
        log.info("Fetching ResultVote: {}", id);
        return commonMapper.resultVoteToResultVoteDto(getResultVoteEntity(id));
    }

    private ResultVote getResultVoteEntity(String id) {
        return resultVoteRepository.findById(id)
                .orElseThrow(ApiException::createObjectNotFound);
    }

}
