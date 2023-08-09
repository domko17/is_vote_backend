package com.tuke.fei.kpi.isvote.modules.auth.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.tuke.fei.kpi.isvote.modules.ApiException;
import com.tuke.fei.kpi.isvote.modules.Constants;
import com.tuke.fei.kpi.isvote.modules.auth.dto.RefreshTokenDto;
import com.tuke.fei.kpi.isvote.modules.auth.dto.TokenResponse;
import com.tuke.fei.kpi.isvote.modules.auth.security.token.TokenException;
import com.tuke.fei.kpi.isvote.modules.auth.security.token.TokenFactory;
import com.tuke.fei.kpi.isvote.modules.auth.security.token.TokenParser;
import com.tuke.fei.kpi.isvote.modules.auth.service.TokenService;
import com.tuke.fei.kpi.isvote.modules.common.model.User;

import java.util.*;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    private final TokenFactory tokenFactory;
    private final TokenParser tokenParser;

    public TokenServiceImpl(TokenFactory tokenFactory, TokenParser tokenParser) {
        this.tokenFactory = tokenFactory;
        this.tokenParser = tokenParser;
    }

    @Override
    public TokenResponse createTokenResponse(User user) {
        Date issuedAt = new Date();
        List<String> roles = Collections.singletonList(user.getUserType().getCode());
        Map<String, Object> additionalClaims = getAdditionalClaims(user);

        String accessToken = tokenFactory.createAccessToken(user.getId(), roles, additionalClaims, issuedAt);
        String refreshToken = tokenFactory.createRefreshToken(user.getId(), issuedAt);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public String checkAndParseRefreshTokenToUserId(RefreshTokenDto refreshTokenDto) {
        try {
            return (String) tokenParser.parseRefreshToken(refreshTokenDto.getRefreshToken());
        } catch (TokenException e) {
            throw new ApiException(ApiException.FaultType.WRONG_REFRESH_TOKEN, "Wrong refresh token");
        }
    }

    private Map<String, Object> getAdditionalClaims(User user) {
        HashMap<String, Object> additionalClaims = new HashMap<>();

        additionalClaims.put(Constants.DISPLAY_NAME_CLAIM, user.getFirstname());
        additionalClaims.put(Constants.USERNAME_CLAIM, user.getUsername());
        additionalClaims.put(Constants.USER_TYPE_CLAIM, user.getUserType().getName());

        return additionalClaims;
    }
}
