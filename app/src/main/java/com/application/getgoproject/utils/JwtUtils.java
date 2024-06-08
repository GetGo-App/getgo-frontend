package com.application.getgoproject.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {

    public static String getUsernameFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getSubject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRoleFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim("http://schemas.microsoft.com/ws/2008/06/identity/claims/role").asString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
