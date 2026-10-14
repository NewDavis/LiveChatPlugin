package me.newdavis.util;

import com.auth0.jwt.interfaces.DecodedJWT;
import me.newdavis.util.jwks.JWKSManager;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class JWT {

    private String token;

    public JWT(String token) {
        this.token = token;
    }

    public DecodedJWT validate() throws Exception {
        return JWKSManager.getJWTVerifier(readKid()).verify(this.token);
    }

    private String readKid() {
        return com.auth0.jwt.JWT.decode(this.token).getKeyId();
    }

}
