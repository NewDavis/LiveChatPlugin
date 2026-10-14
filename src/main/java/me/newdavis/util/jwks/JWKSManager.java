package me.newdavis.util.jwks;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import me.newdavis.LiveChatPlugin;

import org.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.KeyFactory;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;

public class JWKSManager {

    private static HashMap<String, JWK> jwks = new HashMap<>();
    private static HashMap<String, JWTVerifier> verifierCache = new HashMap<>();

    public static void fetchJWKS() {
        try {
            URL jwksEndpoint = new URL(LiveChatPlugin.getConfiguration().getString("jwks-endpoint"));

            URLConnection connection = jwksEndpoint.openConnection();

            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String json = reader.readLine();

            JSONObject jwksResponse = new JSONObject(json);
            if (!jwksResponse.has("keys")) throw new Exception("No JWKS found.");

            jwksResponse = jwksResponse.getJSONObject("keys");

            for (String kid : jwksResponse.keySet()) {
                JSONObject jwkObject = jwksResponse.getJSONObject(kid);

                jwks.put(
                    kid,
                    new JWK(
                        jwkObject.getString("alg"),
                        new BigInteger(1, Base64.getUrlDecoder().decode(jwkObject.getString("n"))),
                        new BigInteger(1, Base64.getUrlDecoder().decode(jwkObject.getString("e")))
                    )
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static RSAKey getRSAKey(String kid) throws Exception {
        if (!jwks.containsKey(kid)) return null;

        JWK jwk = jwks.get(kid);

        RSAPublicKeySpec spec = new RSAPublicKeySpec(jwk.getModulus(), jwk.getExponent());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

    public static JWTVerifier getJWTVerifier(String kid) throws Exception {
        if (verifierCache.containsKey(kid)) return verifierCache.get(kid);

        RSAKey key = getRSAKey(kid);
        if (key == null) throw new Exception("RSA Key not found.");

        Algorithm algorithm = Algorithm.RSA256(key);
        JWTVerifier verifier = JWT.require(algorithm).build();

        verifierCache.put(kid, verifier);

        return verifier;
    }

}
