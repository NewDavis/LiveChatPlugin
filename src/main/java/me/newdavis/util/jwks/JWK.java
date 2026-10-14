package me.newdavis.util.jwks;

import java.math.BigInteger;

public class JWK {

    private String algorithm;
    private BigInteger modulus;
    private BigInteger exponent;

    public JWK(String algorithm, BigInteger modulus, BigInteger exponent) {
        this.algorithm = algorithm;
        this.modulus = modulus;
        this.exponent = exponent;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public BigInteger getExponent() {
        return exponent;
    }
}
