package br.com.app.tanamao.security;

import br.com.app.tanamao.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class TokenAuthenticationService {

    static final long EXPIRATION_TIME = SecurityConstants.EXPIRATION_TIME;
    static final String SECRET = SecurityConstants.SECRET;
    static final String TOKEN_PREFIX = SecurityConstants.TOKEN_PREFIX;
    static final String HEADER_STRING = SecurityConstants.HEADER_STRING;

    static void addAuthentication(HttpServletResponse response, String username, Collection<? extends GrantedAuthority> authorities) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        String token = TOKEN_PREFIX + " " + JWT;
        response.addHeader(HEADER_STRING, token);

        String json = "{ \"token\" : \"" + JWT + "\", \"role\" : \"" + authorities + "\" }";

        try {
            response.getOutputStream().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Authentication getByToken(String token) {
        String user = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            return getByToken(token);
        }
        return null;
    }

}
