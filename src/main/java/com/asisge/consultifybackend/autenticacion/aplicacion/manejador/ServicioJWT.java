package com.asisge.consultifybackend.autenticacion.aplicacion.manejador;


import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class ServicioJWT {

    private final Logger logger = Logger.getLogger(ServicioJWT.class.getName());

    @Value("${security.jwt.expiration-minutes}")
    private long systemExpirationMinutes;
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.cookie-name}")
    private String jwtCookie;

    public String generarToken(UsuarioAutenticado user, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (systemExpirationMinutes * 60 * 1000));

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getNombreUsuario())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generarKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key generarKey() {
        byte[] secretAsBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    public String extraerNombreUsuario(String jwt) {
        return extraerClaims(jwt).getSubject();
    }

    private Claims extraerClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generarKey()).build()
                .parseClaimsJws(jwt).getBody();
    }

    public String obtenerJWTDesdeCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie generarResponseCookie(String jwt) {
        return ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(60 * systemExpirationMinutes).httpOnly(true).build();
    }

    public boolean validarTokenJwt(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(generarKey()).build().parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException e) {
            logger.severe(String.format("Token JWT inválido: %s", e.getMessage()));
        } catch (ExpiredJwtException e) {
            logger.severe(String.format("Token JWT vencido: %s", e.getMessage()));
        } catch (UnsupportedJwtException e) {
            logger.severe(String.format("Token JWT no soportado: %s", e.getMessage()));
        } catch (IllegalArgumentException e) {
            logger.severe(String.format("JWT claims string is empty: %s", e.getMessage()));
        }

        return false;
    }

}
