package org.eclipse.jakarta.hello.jwtConfig;


import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@ApplicationScoped
public class Config {


    @Inject
    private KeyforJwt keyforJwt;


    public String generateToken(String username, String password){

        String token = Jwts.builder().header().
                keyId("id").
                and().
                subject(username).
                issuer(username).
                claim("password",password).
                issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis()+1000*60*30)).
                id(UUID.randomUUID().toString()).
                signWith(keyforJwt.getKey()).
                compact();

        return token;
    }


    public <T> T getClaim(String claim, String token,Class<T> type ){

        T extractedClaim = Jwts.parser().
                verifyWith(keyforJwt.getKey()).
                build().
                parseSignedClaims(token).getPayload().get(claim,type);

        return extractedClaim;

    }


    public boolean isValid(String token){
       String claim = getClaim("exp",token,String.class);

        Date exp = new Date(Long.parseLong(claim));
        return  exp.before(new Date());

    }

}
