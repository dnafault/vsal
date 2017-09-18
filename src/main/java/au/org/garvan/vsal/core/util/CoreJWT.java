package au.org.garvan.vsal.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class CoreJWT {
    public static void verifyJWT(String token)
            throws UnsupportedEncodingException, JWTVerificationException {
        Properties p = ReadConfig.getProp();
        Algorithm algorithm = Algorithm.HMAC256(p.getProperty("jwtKey"));
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(p.getProperty("jwtIssuer"))
                .withArrayClaim(p.getProperty("jwtAccessClaim"), p.getProperty("jwtAccessValue"))
                .build();
        verifier.verify(token);
    }
}
