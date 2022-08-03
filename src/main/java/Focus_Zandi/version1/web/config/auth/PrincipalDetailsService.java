package Focus_Zandi.version1.web.config.auth;

import Focus_Zandi.version1.domain.Member;
import Focus_Zandi.version1.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// login요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUserName 함수가 실행된다.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //시큐리티 session => Auth 타입 => UserDetails(PrincipalDetails)
    // 리턴 시 Authentication 내부에 UserDetails가 들어가고, Session에도 이게 들어간다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByUsername(username);

        if (findMember != null) {
            return new PrincipalDetails(findMember);
        }
        return null;
    }
}
