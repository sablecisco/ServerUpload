package Focus_Zandi.version1.web.config.jwt;

public interface JwtProperties {

    String SECRET = "잔디타이머"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 60000*10; // 10분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
