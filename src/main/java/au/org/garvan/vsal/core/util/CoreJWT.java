/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Dmitry Degrave
 * Copyright (c) 2019 Garvan Institute of Medical Research
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package au.org.garvan.vsal.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.security.interfaces.RSAPublicKey;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Properties;

public class CoreJWT {

    private static volatile RSAPublicKey pubKey;

    public static RSAPublicKey getRSAPublicKey(String token, String issuer) {
        RSAPublicKey key = pubKey; // single volatile read
        if (key == null) {
            synchronized (CoreJWT.class) {
                if (pubKey == null) {
                    try {
                        // getting pub key from Auth0
                        JwkProvider provider = new UrlJwkProvider(issuer);
                        DecodedJWT jwt = JWT.decode(token);
                        Jwk jwk = provider.get(jwt.getKeyId());  // Get the kid from received JWT token
                        pubKey = (RSAPublicKey) jwk.getPublicKey();
                        System.out.println("Public key has been acquired from Auth0");
                    } catch (JwkException e) {
                        e.printStackTrace();
                    }
                }
                key = pubKey;
            }
        }
        return key; // non-volatile local read, a tiny bit better performance
    }

    public static void verifyJWT(String token, String jwtAccessValue) throws JWTVerificationException {
        Properties p = ReadConfig.getProp();
        String issuer = p.getProperty("jwtIssuer");
        RSAPublicKey publicKey = getRSAPublicKey(token, issuer);
        Algorithm algorithm = Algorithm.RSA256(publicKey, null);

        // verify
        JWTVerifier verifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .withArrayClaim(p.getProperty("jwtAccessClaim"), jwtAccessValue)
            .acceptLeeway(12) // leeway window in seconds
            .build();
        verifier.verify(token);
    }
}