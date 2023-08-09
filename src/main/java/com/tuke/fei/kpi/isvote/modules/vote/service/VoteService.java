package com.tuke.fei.kpi.isvote.modules.vote.service;

import com.sun.tools.jconsole.JConsoleContext;
import com.tuke.fei.kpi.isvote.modules.common.repository.UserRepository;
import com.tuke.fei.kpi.isvote.modules.resultVote.dto.ResultVoteCreateDto;
import com.tuke.fei.kpi.isvote.modules.resultVote.dto.ResultVoteDto;
import com.tuke.fei.kpi.isvote.modules.resultVote.model.ResultVote;
import com.tuke.fei.kpi.isvote.modules.resultVote.repository.ResultVoteRepository;
import com.tuke.fei.kpi.isvote.modules.vote.dto.*;
import com.tuke.fei.kpi.isvote.modules.vote.model.Vote;
import com.tuke.fei.kpi.isvote.modules.vote.model.VoteUsers;
import com.tuke.fei.kpi.isvote.modules.vote.repository.VoteUsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.tuke.fei.kpi.isvote.modules.ApiException;
import com.tuke.fei.kpi.isvote.modules.common.mapper.CommonMapper;
import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.common.service.SecurityService;
import com.tuke.fei.kpi.isvote.modules.vote.repository.VoteRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final ResultVoteRepository resultVoteRepository;
    private final UserRepository userRepository;
    private final VoteUsersRepository voteUsersRepository;
    private final CommonMapper commonMapper;
    private final SecurityService securityService;

    public VoteService(VoteRepository voteRepository, UserRepository userRepository, VoteUsersRepository usersRepository, CommonMapper commonMapper, SecurityService securityService, ResultVoteRepository resultVoteRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.voteUsersRepository = usersRepository;
        this.commonMapper = commonMapper;
        this.securityService = securityService;
        this.resultVoteRepository = resultVoteRepository;
    }

    public VoteDto createVote(VoteCreateDto voteCreateDto) {
        User loggedUser = securityService.getLoggedUser();
        log.info("Creating new Vote for user {}", loggedUser.getId());
        Vote vote = commonMapper.voteCreateDtoToVote(voteCreateDto);

        vote.setTerm(voteCreateDto.getTerm().plusHours(2));
        vote.setDuration(voteCreateDto.getDuration().plusHours(2));
        vote.setCreatedAt(LocalDateTime.now());
        vote.setUser(loggedUser);
        voteRepository.save(vote);

        for (int i = 0; i <  voteCreateDto.getUsers().getId().size(); i++) {
            VoteUsers voteUsers = new VoteUsers();
            voteUsers.setVote(vote);
            voteUsers.setUser(userRepository.findById(voteCreateDto.getUsers().getId().get(i)).get());
            voteUsersRepository.save(voteUsers);
        }
        return commonMapper.voteToVoteDto(vote);
    }

    public VoteDto updateVote(String id, VoteCreateDto voteCreateDto) {
        log.info("Updating Vote: {}", id);
        Vote vote = getVoteEntity(id);
        commonMapper.updateVoteFromVoteCreateDto(voteCreateDto, vote);
        voteRepository.save(vote);
        return commonMapper.voteToVoteDto(vote);
    }

    public VoteDto updateState(VoteStateDto voteStateDto) {
        Vote vote = voteRepository.findById(voteStateDto.getVoteId()).get();
        if (vote.getActiveVoting() != voteStateDto.getActive()){
            vote.setActiveVoting(voteStateDto.getActive());
        }

        voteRepository.save(vote);
        return commonMapper.voteToVoteDto(vote);
    }



    public void deleteVote(String id) {
        log.info("Deleting Vote: {}", id);
        voteRepository.findById(id).ifPresent(voteRepository::delete);
    }

    public VoteDto getVote(String id) {
        log.info("Fetching Vote: {}", id);
        return commonMapper.voteToVoteDto(getVoteEntity(id));
    }

    public VotesByUserDto getVoteByUser(String id) {
        log.info("Fetching Vote: {}", id);
        return commonMapper.voteToVotesByUserDto(getVoteEntity(id));
    }

    public Vote getVoteEntity(String id) {
        return voteRepository.findById(id)
                .orElseThrow(ApiException::createObjectNotFound);
    }

    public List<VoteDto> getAllVotesOfUser() {
        User loggedUser = securityService.getLoggedUser();

        return voteRepository.findAllByUser(loggedUser)
                .stream()
                .map(commonMapper::voteToVoteDto)
                .collect(Collectors.toList());
    }

    public List<Vote> getAll() {
        List<Vote> all = voteRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        for (int i = 0; i <  all.size(); i++) {
            all.get(i).getVoteUsers().clear();
        }
        return all;
    }

    public List<VotesByUserDto> getAllByUser(){
        User loggedUser = securityService.getLoggedUser();
        List<Vote> all = voteRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<VotesByUserDto> allOfUsers = new ArrayList();

        List<String> resultVoteList = new ArrayList();

        for (int i = 0; i <  all.size(); i++) {
            for (int a = 0; a <  this.getVote(all.get(i).getId()).getVoteUsers().size(); a++) {
                if (this.getVote(all.get(i).getId()).getVoteUsers().get(a).getUser().getId() == loggedUser.getId()){
                    allOfUsers.add(this.getVoteByUser(all.get(i).getId()));
                }
            }

            List<ResultVote> result = resultVoteRepository.findAllByVote(voteRepository.findById(all.get(i).getId()).get());

            for (int b = 0; b <  result.size(); b++) {
                String name = result.get(b).getUser().getTitle() +" "+ result.get(b).getUser().getFirstname() +" "+ result.get(b).getUser().getLastname();
                resultVoteList.add( result.get(b).getResultVote() +"=>"+name );
            }

        }

        for (int c = 0; c <  allOfUsers.size(); c++) {
            allOfUsers.get(c).setResultVote(resultVoteList);
        }

        return allOfUsers;
    }

    public VotePageDto getAllPaginatedVoteOfUser(Integer page, Integer size) {
        if (page < 0 || size < 1) {
            throw new ApiException(ApiException.FaultType.INVALID_PARAMS,
                    "Param page must be from 0 higher and page size from 1 higher");
        }
        User loggedUser = securityService.getLoggedUser();
        log.info("Fetching paginated Vote entities for: {}", loggedUser.getId());

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Vote> orderPage = voteRepository.findByUser(loggedUser, pageable);
        return new VotePageDto(orderPage, commonMapper::voteToVoteDto);
    }
}
