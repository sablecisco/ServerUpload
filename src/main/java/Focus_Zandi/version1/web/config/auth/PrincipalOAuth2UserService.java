package Focus_Zandi.version1.web.config.auth;

import Focus_Zandi.version1.domain.Member;
import Focus_Zandi.version1.domain.MemberDetails;
import Focus_Zandi.version1.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    //후처리함수
    //구글로부터 받은 userRequest에 대한 후처리

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final MemberRepository memberRepository;

    //구글로그인 버튼 클릭하면 -> 구글로그인 창 -> 로그인 완료 -> code return (OAuth 라이브러리가 받음) -> AccessToken 요청
    //userRequest wjdqh -> 회원프로필을 받음(loadUser함수) -> 구글로부터 회원 프로필을 받아옴
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //회원가입용 정보
        Member userEntity = createUser(userRequest, oAuth2User);

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }

    //자동 회원가입 진행 로직
    private Member createUser(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oAuth2User.getAttribute("sub");
        String name = oAuth2User.getAttribute("name");
        String username = provider + "_" + providerId;
        String password = passwordEncoder.encode("CommonPassword");
        String email = oAuth2User.getAttribute("email");
        MemberDetails memberDetails = new MemberDetails();

        Member memberEntity = memberRepository.findByProviderId(providerId);
        if(memberEntity == null) {
            System.out.println("최초 로그인");
            memberEntity = Member.builder()
                    .username(username)
                    .name(name)
                    .password(password)
                    .email(email)
                    .memberDetails(memberDetails)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            memberRepository.save(memberEntity);
        } else {
            System.out.println("이미 가입된 사용자");
        }
        return memberEntity;
    }
}
