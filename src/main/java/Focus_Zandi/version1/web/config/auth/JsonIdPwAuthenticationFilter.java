package Focus_Zandi.version1.web.config.auth;

import Focus_Zandi.version1.domain.dto.memberdto.MemberLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class JsonIdPwAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public JsonIdPwAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String body = request.getReader().lines().collect(Collectors.joining());
        MemberLoginDto loginDto = objectMapper.readValue(body, MemberLoginDto.class);

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        usernamePasswordAuthenticationToken.setDetails((super.authenticationDetailsSource.buildDetails(request)));

        return super.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
    // Gson 말고 ObjectMapper로도 가능함
//    private Map<String, Object> parseJsonMap(HttpServletRequest request) throws IOException {
//        String body = request.getReader().lines().collect(Collectors.joining());
//        System.out.println("body = " + body);
//        GsonJsonParser gsonJsonParser = new GsonJsonParser();
//        return gsonJsonParser.parseMap(body);
//    }
}
