package com.asisge.consultifybackend.auth.aplicacion.manejador;


import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class ServicioJWT {

    @Value("${security.jwt.expiration-minutes}")
    private long systemExpirationMinutes;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

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
        System.out.println("mi clave es: " + new String(secretAsBytes));
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    public String extraerNombreUsuario(String jwt) {
        return extraerClaims(jwt).getSubject();
    }

    private Claims extraerClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generarKey()).build()
                .parseClaimsJws(jwt).getBody();
    }
}
