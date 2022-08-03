package Focus_Zandi.version1.web.config;

import Focus_Zandi.version1.web.config.auth.OAuthSuccessHandler;
import Focus_Zandi.version1.web.config.auth.PrincipalOAuth2UserService;
import Focus_Zandi.version1.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Configuration
//@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityAuthConfig extends WebSecurityConfigurerAdapter {

    private final MemberRepository memberRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final PrincipalOAuth2UserService principalOAuth2UserService;

    // 1. 코드받기(인증 완료) 2. 엑세스 토큰을 코드를 통해서 받음(권한이 생김) 3. 사용자 프로필정보 가지고 옴
    // 4-1. 정보토대로 회원가입 자동진행 4-2. 추가정보가 필요하면 추가적인 회원가입창이 나옴
    // 로그인이 되면 엑세스토큰과 사용자프로필정보를 한번에 받음
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrf 비활성화
//        http
//                .formLogin().disable()
//                .httpBasic().disable();
        http
                .authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manger/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .sessionManagement()
                .maximumSessions(1);
        http
                .formLogin()
                .loginProcessingUrl("/loginProc")
                .and()
                .oauth2Login()
                .successHandler(new OAuthSuccessHandler(memberRepository))
                .userInfoEndpoint()// 후처리 시작
                .userService(principalOAuth2UserService); // 서비스에서 후처리함


    }
}