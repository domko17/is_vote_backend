package com.tuke.fei.kpi.isvote.modules.auth.security.token;


import com.tuke.fei.kpi.isvote.modules.properties.SecurityProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class JwtTokenFactory implements TokenFactory {
    public static final String AUTHORITIES_CLAIM_NAME = "authorities";
    public static final String TOKEN_TYPE_CLAIM_NAME = "token_type";

    private final String signingKey;
    private final String issuer;
    private final int accessTokenValiditySeconds;
    private final int refreshTokenValiditySeconds;

    public JwtTokenFactory(SecurityProperties securityProperties) {
        this.signingKey = securityProperties.getSigningKey();
        this.issuer = securityProperties.getIssuer();
        this.accessTokenValiditySeconds = securityProperties.getAccessTokenValidity();
        this.refreshTokenValiditySeconds = securityProperties.getRefreshTokenValidity();
    }


    @Override
    public String createAccessToken(String subject, List<String> roles, Map<String, Object> additionalClaims, Date issuedAt) {
        Date expiration = new Date(issuedAt.getTime() + (accessTokenValiditySeconds * 1000));

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setIssuer(issuer)
                .setExpiration(expiration)
                .claim(TOKEN_TYPE_CLAIM_NAME, TokenType.ACCESS_TOKEN)
                .claim(AUTHORITIES_CLAIM_NAME, roles)
                .addClaims(additionalClaims)
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();

    }

    @Override
    public String createRefreshToken(String subject, Date issuedAt) {
        Date expiration = new Date(issuedAt.getTime() + (refreshTokenValiditySeconds * 1000));

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setIssuer(issuer)
                .setExpiration(expiration)
                .claim(TOKEN_TYPE_CLAIM_NAME, TokenType.REFRESH_TOKEN)
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }
}
