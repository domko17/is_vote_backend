package com.tuke.fei.kpi.isvote.modules.common.service.impl;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.tuke.fei.kpi.isvote.modules.ApiException;
import com.tuke.fei.kpi.isvote.modules.Constants;
import com.tuke.fei.kpi.isvote.modules.auth.security.token.JwtAuthentication;
import com.tuke.fei.kpi.isvote.modules.common.enums.UserType;
import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.common.repository.UserRepository;
import com.tuke.fei.kpi.isvote.modules.common.service.SecurityService;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> loggedUser() {
        return loggedUserId().flatMap(userRepository::findById);
    }

    @Override
    public User getLoggedUser() {
        return loggedUser().orElseThrow(ApiException::createUserNotLoggedIn);
    }

    @Override
    public Optional<User> loggedUserReference() {
        return loggedUserId().map(userRepository::getOne);
    }

    @Override
    public Optional<String> loggedUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(String.class::cast);
    }

    @Override
    public String getLoggedUserId() {
        return loggedUserId().orElseThrow(ApiException::createUserNotLoggedIn);
    }

    @Override
    public Optional<UserType> loggedUserType() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(JwtAuthentication.class::cast)
                .map(JwtAuthentication::getDetails)
                .map(detailsMap -> detailsMap.get(Constants.USER_TYPE_CLAIM))
                .map(String.class::cast)
                .map(UserType::getUserTypeByUsername);
    }

}
