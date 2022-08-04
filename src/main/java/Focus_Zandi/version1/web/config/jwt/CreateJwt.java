package Focus_Zandi.version1.web.config.jwt;

import Focus_Zandi.version1.domain.Member;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.Key;
import java.util.Date;

public class CreateJwt {

    public static String createAccessToken(Member memberEntity) {
        return JWT.create()
                .withSubject(memberEntity.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
//                .withExpiresAt(new Date(System.currentTimeMillis() + 60000*10))
                .withClaim("id", memberEntity.getId())
                .withClaim("username", memberEntity.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public static String createRefreshToken(Member memberEntity, String AccessToken) {
        return JWT.create()
                .withSubject(memberEntity.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+ 60000*100))
                .withClaim("AccessToken", AccessToken)
                .withClaim("username", memberEntity.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }
}




