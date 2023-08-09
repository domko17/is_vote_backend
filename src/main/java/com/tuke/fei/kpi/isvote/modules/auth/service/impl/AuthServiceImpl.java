package com.tuke.fei.kpi.isvote.modules.auth.service.impl;

import com.tuke.fei.kpi.isvote.modules.SendEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tuke.fei.kpi.isvote.modules.ApiException;
import com.tuke.fei.kpi.isvote.modules.auth.dto.*;
import com.tuke.fei.kpi.isvote.modules.auth.service.AuthService;
import com.tuke.fei.kpi.isvote.modules.auth.service.TokenService;
import com.tuke.fei.kpi.isvote.modules.common.dto.UserDto;
import com.tuke.fei.kpi.isvote.modules.common.mapper.CommonMapper;
import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.common.repository.UserRepository;
import com.tuke.fei.kpi.isvote.modules.common.service.SecurityService;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final CommonMapper commonMapper;
    @Autowired
    private SendEmailService sendEmailService;


    public AuthServiceImpl(SecurityService securityService, PasswordEncoder passwordEncoder,
                           TokenService tokenService, UserRepository userRepository,
                           CommonMapper commonMapper) {
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.commonMapper = commonMapper;
    }

    @Transactional
    @Override
    public void resetPassword(String mail) {
        //todo implement
    }

    @Override
    public UserDto getCurrentUser() {
        return commonMapper.userToCurrentUserDto(securityService.getLoggedUser());
    }

    @Override
    public TokenResponse logInViaRefreshToken(RefreshTokenDto refreshTokenDto) {
        String userId = tokenService.checkAndParseRefreshTokenToUserId(refreshTokenDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> ApiException.createObjectNotFound("User not found"));
        user = userRepository.save(user);
        return tokenService.createTokenResponse(user);
    }

    @Override
    public TokenResponse logInViaCredentials(CredentialsDto credentials) {
        User user = userRepository.findByUsername(credentials.getUsername())
                .filter(u -> passwordEncoder.matches(credentials.getPassword(), u.getPassword()))
                .orElseThrow(() -> new ApiException(ApiException.FaultType.WRONG_CREDENTIALS, "Wrong username or password"));

        user.setLastLogin(LocalDateTime.now());
        user = userRepository.save(user);
        return tokenService.createTokenResponse(user);
    }

    @Override
    public TokenResponse changePassword(PasswordChangeDto passwordChangeDto) {
        User loggedUser = securityService.getLoggedUser();
        if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), loggedUser.getPassword())) {
            throw new ApiException(ApiException.FaultType.INVALID_PASSWORD, "Old password doesn't match");
        }
        loggedUser.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        loggedUser = userRepository.save(loggedUser);
        return tokenService.createTokenResponse(loggedUser);
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


    @Override
    public UserDto createUser(CreateUserDto createUserDto) {

        User user = commonMapper.createUserDtoToUser(createUserDto);
        long countUser = userRepository.count();
        String userName = createUserDto.getFirstname().substring(0,1).toLowerCase() + createUserDto.getLastname().substring(0,1).toLowerCase() + String.format("_%03d", countUser);
        String secretName = this.generateString(5);
        String password = this.generateString(8);

        while (!userRepository.findByUsername(userName).isEmpty()){
            countUser++;
            userName = createUserDto.getFirstname().substring(0,1).toLowerCase() + createUserDto.getLastname().substring(0,1).toLowerCase() + String.format("_%03d", countUser);
        }

        if (userRepository.findByUsernameOrMail(null, createUserDto.getMail()).isPresent()) {
            throw ApiException.createUserAlreadyExists();
        }

        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(userName);
        userRepository.save(user);

        String emailBody = String.format("Prihlasovacie meno: %s \n Prihlasovacie heslo: %s", userName, password);
        sendEmailService.sendEmail(createUserDto.getMail(), emailBody, "IS Vote KPI");

        return commonMapper.userToCurrentUserDto(user);
    }
}
