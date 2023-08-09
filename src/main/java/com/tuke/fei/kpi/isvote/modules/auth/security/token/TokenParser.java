package com.tuke.fei.kpi.isvote.modules.auth.security.token;

import org.springframework.security.core.Authentication;

public interface TokenParser {
    Authentication parseAccessToken(String token) throws TokenException;

    Object parseRefreshToken(String token) throws TokenException;
}
