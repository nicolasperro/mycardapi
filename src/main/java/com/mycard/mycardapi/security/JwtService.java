package com.mycard.mycardapi.security;

import com.mycard.mycardapi.model.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;


@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        long expString = Long.parseLong(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        // ADICIONANDO AS PERMISSÕES (ROLES) COMO UMA CLAIM NO TOKEN
        HashMap<String, Object> claims = new HashMap<>();
        if (usuario.isAdmin()) {
            claims.put("roles", new String[]{"ROLE_ADMIN", "ROLE_USER"});
        } else {
            claims.put("roles", new String[]{"ROLE_USER"});
        }


        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .addClaims(claims) // Adiciona as permissões ao corpo do token
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    // O resto da sua classe JwtService continua igual...

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        } catch (Exception e) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return obterClaims(token).getSubject();
    }
}