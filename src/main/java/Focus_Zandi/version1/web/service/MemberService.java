package Focus_Zandi.version1.web.service;

import Focus_Zandi.version1.domain.Followers;
import Focus_Zandi.version1.domain.Member;
import Focus_Zandi.version1.domain.MemberDetails;
import Focus_Zandi.version1.domain.dto.DetailsDto;
import Focus_Zandi.version1.domain.dto.MemberReturnerDto;
import Focus_Zandi.version1.web.repository.FollowersRepository;
import Focus_Zandi.version1.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowersRepository followersRepository;

    public void join(DetailsDto detailsDto, String username) {
        Member member = memberRepository.findByUsername(username);
        MemberDetails memberDetails = memberRepository.findByUsername(username).getMemberDetails();

        member.setUsername(username);
        memberDetails.setDob(detailsDto.getDob());
        memberDetails.setGender(detailsDto.getGender());
        memberDetails.setOccupation(detailsDto.getOccupation());
        memberDetails.setWorkPlace(detailsDto.getWorkPlace());
        memberDetails.setMemo(detailsDto.getMemo());
        memberDetails.setAge(MemberDetails.calcAge(detailsDto.getDob()));
    }

    public Member findMemberByUserName(String name) {
        return memberRepository.findByUsername(name);
    }

    public MemberReturnerDto findMemberByUserNameWithDetails(String name) {
        Member member = memberRepository.findByUsername(name);
        MemberDetails memberDetails = member.getMemberDetails();
        MemberReturnerDto memberReturnerDto = new MemberReturnerDto(member, memberDetails);
        return memberReturnerDto;
    }

    public MemberReturnerDto findMemberByUserNameWithDetailsV2(String name) {
        Member member = memberRepository.findByName(name);
        MemberDetails memberDetails = member.getMemberDetails();
        MemberReturnerDto memberReturnerDto = new MemberReturnerDto(member, memberDetails);
        return memberReturnerDto;
    }

    public void makeFollow(String followeeName, String username) {
        Member followee = memberRepository.findByUsername(followeeName);
        Member follower = memberRepository.findByUsername(username);

        Followers followerShip = Followers.createFollowerShip(followee.getId(), follower);

        followersRepository.makeFollow(followerShip);
    }

    public void makeUnFollow(String followeeName, String username) {
        long followeeId = memberRepository.findByUsername(followeeName).getId();
        Member follower = memberRepository.findByUsername(username);
        followersRepository.unFollow(followeeId, follower);
    }

    //추후 수정
//    public Member updateMember(MemberUpdateDto updateDto, long memberId) {
//        Member updatedMember = memberRepository.update(updateDto, memberId);
//        return updatedMember;
//    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByUsername(member.getUsername());
        if (findMember != null) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    public List<String> getFollowers(String username) {
        Member member = memberRepository.findByUsername(username);
        return followersRepository.findFollowers(member);
    }
}
