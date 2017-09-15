package au.org.garvan.vsal.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.io.UnsupportedEncodingException;

public class CoreJWT {
    public static void verifyJWT(String token)
            throws UnsupportedEncodingException, JWTVerificationException {
        String key = ReadConfig.getProp().getProperty("jwt.key");
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token);
    }
}
