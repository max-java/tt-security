package com.tutrit.tt.security.custom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigestSpi;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.security.MessageDigest;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TokenService {

    @Value("${secret.key:default}")
    private String secretKey;

    public String generateToken(Principal principal) {
        int result = Objects.hash(principal.getId(), principal.getFullName());
        result = result + secretKey.hashCode() + Arrays.hashCode(principal.getRoles());
        result = Objects.hash(result);
        return digest("MD5", BigInteger.valueOf(result).toString()).substring(10, 20);
    }

    public boolean validateToken(Principal principal) {
        String token = principal.getToken();
        String validToken = generateToken(principal);
        return validToken.equals(token);
    }

    public String digest(String alg, String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(alg);
            byte[] buffer = input.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();
            return encodeHex(digest);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String encodeHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}

