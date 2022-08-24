package Focus_Zandi.version1.web.controller;

import Focus_Zandi.version1.domain.Member;
import Focus_Zandi.version1.domain.dto.followdto.FolloweeReturner;
import Focus_Zandi.version1.domain.dto.followdto.FollowerNumberReturnerNumDto;
import Focus_Zandi.version1.domain.dto.followdto.FollowerSetterDto;
import Focus_Zandi.version1.domain.dto.memberdto.DetailsDto;
import Focus_Zandi.version1.domain.dto.memberdto.MemberReturnerDto;
import Focus_Zandi.version1.web.repository.FollowersRepository;
import Focus_Zandi.version1.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final FollowersRepository followersRepository;

    //DTO 수정해서 프론트 요구사항 맞추면 됨
    //유저 정보 조회
    @GetMapping("/showMember")
    public MemberReturnerDto showMember(HttpServletRequest request, Authentication authentication) {
        String username = getUsername(authentication);
        MemberReturnerDto details = memberService.findMemberByUserNameWithDetails(username);
        return details;
    }

    @PostMapping("/memberQuit")
    public void memberQuit(Authentication authentication, HttpServletResponse response) throws IOException {
        String username = getUsername(authentication);
        memberService.deleteAll(username);
        Member memberByUserName = memberService.findMemberByUserName(username);
        if (memberByUserName == null) {
            response.setStatus(200);
        } else {
            response.sendError(400, "Delete FAILED!");
        }
    }

    @GetMapping("/members/{name}")
    public MemberReturnerDto showMemberByName(@PathVariable String name) {
        return memberService.findMemberByUserNameWithDetailsV2(name);
    }

    // 유저 정보 수정 (최초생성시에는 null로 기입)
    @PostMapping("/editMember")
    public int getDetails(@RequestBody DetailsDto detailsDto, Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        memberService.join(detailsDto, getUsername(authentication));
        return response.getStatus();
    }

    // 친구 추가
//    @PostMapping("/addFriend")
//    public int followMember(@RequestBody FolloweeNameDto followeeName, Authentication authentication, HttpServletResponse response, HttpServletRequest request) {
//        memberService.makeFollow(followeeName.getFolloweeName(), getUsername(authentication));
//        return response.getStatus();
//    }

    // 친구 추가
    @GetMapping("/addFriend/{email}")
    public FolloweeReturner addFriend (@PathVariable String email, Authentication authentication, HttpServletResponse response, HttpServletRequest request) throws IOException {
        FolloweeReturner followeeReturner = memberService.makeFollow(email, getUsername(authentication));
        if (followeeReturner == null) {
            response.sendError(400, "Failed to add friend");
            return null;
        }
        return  followeeReturner;
    }

    // 친구 삭제
    @GetMapping("/removeFriend/{email}")
    public int unfollowMember(@PathVariable String email, Authentication authentication, HttpServletResponse response, HttpServletRequest request) {
        memberService.makeUnFollow(email, getUsername(authentication));
        return response.getStatus();
    }

    @GetMapping("/getNumberOfFollowers")
    public FollowerNumberReturnerNumDto getNumberOfFollowers(Authentication authentication) {
        int numberOfFollower = memberService.getNumberOfFollower(getUsername(authentication));
        return new FollowerNumberReturnerNumDto(numberOfFollower);
    }

    @PostMapping("/addNumberOfFollowers")
    public int setNumberOfFollowers(@RequestBody FollowerSetterDto followerNumDto, Authentication authentication, HttpServletResponse response) throws IOException {
        int check = memberService.setNumberOfFollow(followerNumDto.getNumberOfFollowers(), getUsername(authentication));
        if (check != followerNumDto.getNumberOfFollowers()) response.setStatus(400);
        return response.getStatus();
    }

    // 전체 친구 조회
    @GetMapping("/findFriend")
    public List<String> getFollowers(Authentication authentication) {
        String username = getUsername(authentication);
        return memberService.getFollowers(username);
    }

    private String getUsername(Authentication authentication) {
        String name = authentication.getName();
        return name;
    }

//    private String getUsername (HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        SecurityContextImpl context = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
//        return context.getAuthentication().getName();
//    }
}
