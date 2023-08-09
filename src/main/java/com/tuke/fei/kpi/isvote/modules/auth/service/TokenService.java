package com.tuke.fei.kpi.isvote.modules.auth.service;

import com.tuke.fei.kpi.isvote.modules.auth.dto.RefreshTokenDto;
import com.tuke.fei.kpi.isvote.modules.auth.dto.TokenResponse;
import com.tuke.fei.kpi.isvote.modules.common.model.User;

public interface TokenService {
    TokenResponse createTokenResponse(User user);

    String checkAndParseRefreshTokenToUserId(RefreshTokenDto refreshTokenDto);
}
