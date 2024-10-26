package com.satish.techie.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.satish.techie.records.Address;
import com.satish.techie.records.TestData;
import com.satish.techie.records.UserData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class JwtService {

    private static final String SECRET = UUID.randomUUID().toString().replace("-","===");
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateToken() {
        TestData testData = TestData.builder()
                .user(UserData.builder().firstName("firstName").lastName("lastName").build())
                .address(Address.builder().flatNo("123").street("street").state("state").build())
                .build();
        Map<String, Object> claims = objectMapper.convertValue(testData, new TypeReference<>() {});
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)))
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * This function will verify signature of the token if fails throw an exception or else
     * it will check for expiration if fails throw an exception or else
     * it will return true as a response.
     *
     * @param token JWT token
     * @return rerun true for success
     */
    public boolean verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(getSecretKey()).build().parse(token);
            return true;
        } catch (SignatureException | ExpiredJwtException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public TestData getPayload(String token) {
        Claims claims = Jwts.parser().verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return objectMapper.convertValue(claims, TestData.class);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

}
