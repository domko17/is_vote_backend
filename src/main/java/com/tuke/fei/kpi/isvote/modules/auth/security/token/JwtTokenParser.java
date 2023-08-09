package com.tuke.fei.kpi.isvote.modules.auth.security.token;


import io.jsonwebtoken.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tuke.fei.kpi.isvote.modules.auth.security.token.JwtTokenFactory.TOKEN_TYPE_CLAIM_NAME;

public class JwtTokenParser implements TokenParser {

    private final String signingKey;

    public JwtTokenParser(String signingKey) {
        this.signingKey = signingKey;
    }

    /**
     * Parse given JWT token into {@link org.springframework.security.core.Authentication} instance
     *
     * @param token JWT token to be parsed
     * @return authentication instance
     * @throws TokenException if token type is different than {@link TokenType#ACCESS_TOKEN} or token is
     *         invalid
     */
    @Override
    public JwtAuthentication parseAccessToken(String token) throws TokenException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token).getBody();

            if (!TokenType.ACCESS_TOKEN.getAbbreviation().equals(claims.get(TOKEN_TYPE_CLAIM_NAME))) {
                throw new TokenException("Wrong type of token. Expected type: " + TokenType.ACCESS_TOKEN);
            }

            @SuppressWarnings("unchecked")
            List<String> roles = claims.get(JwtTokenFactory.AUTHORITIES_CLAIM_NAME, List.class);
            Set<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            return new JwtAuthentication(claims.getSubject(), authorities, claims);
        } catch (SignatureException ex) {
            throw new TokenException("Invalid JWT signature.", ex);
        } catch (MalformedJwtException ex) {
            throw new TokenException("Invalid JWT token.", ex);
        } catch (ExpiredJwtException ex) {
            throw new TokenException("Expired JWT token.", ex);
        } catch (UnsupportedJwtException ex) {
            throw new TokenException("Unsupported JWT token.", ex);
        } catch (IllegalArgumentException ex) {
            throw new TokenException("JWT claims string is empty.", ex);
        }
    }

    /**
     * Parse given JWT token and returns subject claim
     *
     * @param token JWT token to be parsed
     * @return subject claim
     * @throws TokenException if token type is different than {@link TokenType#REFRESH_TOKEN} or token
     *         is invalid
     */
    @Override
    public Object parseRefreshToken(String token) throws TokenException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token).getBody();

            if (!TokenType.REFRESH_TOKEN.getAbbreviation().equals(claims.get(TOKEN_TYPE_CLAIM_NAME))) {
                throw new TokenException("Wrong type of token. Expected type: " + TokenType.REFRESH_TOKEN);
            }

            return claims.getSubject();
        } catch (SignatureException ex) {
            throw new TokenException("Invalid JWT signature.", ex);
        } catch (MalformedJwtException ex) {
            throw new TokenException("Invalid JWT token.", ex);
        } catch (ExpiredJwtException ex) {
            throw new TokenException("Expired JWT token.", ex);
        } catch (UnsupportedJwtException ex) {
            throw new TokenException("Unsupported JWT token.", ex);
        } catch (IllegalArgumentException ex) {
            throw new TokenException("JWT claims string is empty.", ex);
        }
    }
}
