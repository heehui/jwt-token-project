package co.kr.tokenworld.jwtProject.service;

//import io.jsonwebtoken.io.Encoders;
//import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.util.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;


@Service
public class SecurityServiceImpl implements SecurityService {

    //Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    //private final String secretKey = Encoders.BASE64.encode(key.getEncoded());

    private final String secretKey = Base64.getEncoder().encodeToString("Hello World".getBytes());

    @Override
    public String createToken(String subject, long ttlMillis) {
        if (ttlMillis <= 0) {
            throw new RuntimeException("토큰 만료기간은 0 이상이어야 합니다.");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);
        long nowMillis = System.currentTimeMillis();
        builder.setExpiration(new Date(nowMillis + ttlMillis));
        return builder.compact();
    }

    @Override
    public String getSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
