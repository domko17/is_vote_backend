package com.tuke.fei.kpi.isvote.modules.common.mapper;

import com.tuke.fei.kpi.isvote.modules.resultVote.dto.ResultVoteCreateDto;
import com.tuke.fei.kpi.isvote.modules.resultVote.dto.ResultVoteDto;
import com.tuke.fei.kpi.isvote.modules.resultVote.model.ResultVote;
import com.tuke.fei.kpi.isvote.modules.vote.dto.VoteUsersDto;
import com.tuke.fei.kpi.isvote.modules.vote.dto.VotesByUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import com.tuke.fei.kpi.isvote.modules.auth.dto.CreateUserDto;
import com.tuke.fei.kpi.isvote.modules.common.dto.UserDto;
import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.vote.dto.VoteCreateDto;
import com.tuke.fei.kpi.isvote.modules.vote.dto.VoteDto;
import com.tuke.fei.kpi.isvote.modules.vote.model.Vote;

@Mapper(implementationName = "CommonMapperImplementation",
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.ERROR)
public interface CommonMapper {

    UserDto userToCurrentUserDto(User user);

    User createUserDtoToUser(CreateUserDto createUserDto);

    User updateUserFromUserCreateDto(CreateUserDto createUserDto, @MappingTarget User user);

    Vote voteCreateDtoToVote(VoteCreateDto voteCreateDto);

    VoteDto voteToVoteDto(Vote vote);

    VotesByUserDto voteToVotesByUserDto(Vote vote);

    void updateVoteFromVoteCreateDto(VoteCreateDto voteCreateDto, @MappingTarget Vote vote);


    ResultVote resultVoteCreateDtoToResultVote(ResultVoteCreateDto resultVoteCreateDto);

    ResultVoteDto resultVoteToResultVoteDto(ResultVote resultVote);

    void updateResultVoteFromResultVoteCreateDto(ResultVoteCreateDto resultVoteCreateDto, @MappingTarget ResultVote resultVote);

}
