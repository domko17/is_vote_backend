package com.tuke.fei.kpi.isvote.modules.auth.security.token;


import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TokenFactory {
    String createAccessToken(String subject, List<String> roles, Map<String, Object> additionalClaims, Date issuedAt);

    String createRefreshToken(String subject, Date issuedAt);
}
