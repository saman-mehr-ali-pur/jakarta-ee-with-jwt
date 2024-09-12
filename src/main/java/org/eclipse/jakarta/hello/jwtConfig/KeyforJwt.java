package org.eclipse.jakarta.hello.jwtConfig;


import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;

import javax.crypto.SecretKey;
@ApplicationScoped
public class KeyforJwt {
    private SecretKey key;

    public  KeyforJwt(){
        this.key = Jwts.SIG.HS256.key().build();

    }
    public SecretKey getKey() {
        return key;
    }



}
