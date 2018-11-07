package com.wiki.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wiki.security.SecurityConstants.HEADER_STRING;
import static com.wiki.security.SecurityConstants.SECRET;
import static com.wiki.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        Optional.ofNullable(req.getHeader(HEADER_STRING)).map(value -> value.replace(TOKEN_PREFIX, ""))
                .map(token -> Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody())
                .map(claims -> {
                    String username = claims.getSubject();
                    Collection<?> roles = claims.get("roles", Collection.class);
                    Set<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(String.class::cast)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toSet());

                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
                })
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        chain.doFilter(req, res);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String header) {
        header = header.replace(TOKEN_PREFIX, "");
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(header).getBody();
        String username = claims.getSubject();
        Collection<?> roles = claims.get("roles", Collection.class);
        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(String.class::cast)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}
