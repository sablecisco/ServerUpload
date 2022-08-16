package Focus_Zandi.version1.web.service;

import Focus_Zandi.version1.domain.Followers;
import Focus_Zandi.version1.domain.Member;
import Focus_Zandi.version1.domain.MemberDetails;
import Focus_Zandi.version1.domain.dto.memberdto.DetailsDto;
import Focus_Zandi.version1.domain.dto.followdto.FolloweeReturner;
import Focus_Zandi.version1.domain.dto.memberdto.MemberReturnerDto;
import Focus_Zandi.version1.domain.dto.recorddto.MonthlyRecordsDto;
import Focus_Zandi.version1.web.repository.FollowersRepository;
import Focus_Zandi.version1.web.repository.MemberRepository;
import Focus_Zandi.version1.web.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowersRepository followersRepository;
    private final RecordRepository recordRepository;

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

    public FolloweeReturner makeFollow(String email, String username) {
        LocalDate now = LocalDate.now();
        int monthValue = now.getMonthValue();
        String month = "";
        if (monthValue < 10) {
            month = '0' + String.valueOf(monthValue);
        } else if (monthValue == 11 || monthValue == 12) {
            month = String.valueOf(monthValue);
        }

        Member followee = memberRepository.findByEmail(email);
        String followeeUsername = followee.getUsername();
        Member follower = memberRepository.findByUsername(username);

//        int numberOfFollowers = follower.getMemberDetails().getNumberOfFollowers();
//        follower.getMemberDetails().setNumberOfFollowers(numberOfFollowers + 1);

        Followers followerShip = Followers.createFollowerShip(followee.getId(), follower);

        followersRepository.makeFollow(followerShip);

        MemberReturnerDto followeeReturner = findMemberByUserNameWithDetails(followeeUsername);
        Map<String, List<MonthlyRecordsDto>> allByMonth = recordRepository.findAllByMonth(month, followee);

        FolloweeReturner followeeData = new FolloweeReturner(followeeReturner, allByMonth);
        return followeeData;
    }

    public void makeUnFollow(String email, String username) {
        long followeeId = memberRepository.findByEmail(email).getId();
        Member follower = memberRepository.findByUsername(username);

//        int numberOfFollowers = follower.getMemberDetails().getNumberOfFollowers();
//        follower.getMemberDetails().setNumberOfFollowers(numberOfFollowers - 1);

        followersRepository.unFollow(followeeId, follower);
    }

    public void deleteAll(String username) {
        Member member = findMemberByUserName(username);
        followersRepository.deleteByMember(member);
        recordRepository.deleteByMember(member);
        memberRepository.deleteByUserName(username);
        long id = member.getMemberDetails().getId();
        memberRepository.deleteDetails(id);
    }

    //추후 수정
//    public Member updateMember(MemberUpdateDto updateDto, long memberId) {
//        Member updatedMember = memberRepository.update(updateDto, memberId);
//        return updatedMember;
//    }

    public List<String> getFollowers(String username) {
        Member member = memberRepository.findByUsername(username);
        return followersRepository.findFollowers(member);
    }

    public int setNumberOfFollow(int numberOfFollowers, String username) {
        memberRepository.findByUsername(username).getMemberDetails().setNumberOfFollowers(numberOfFollowers);
        return memberRepository.findByUsername(username).getMemberDetails().getNumberOfFollowers();
    }

    public int getNumberOfFollower(String username) {
        return memberRepository.findByUsername(username).getMemberDetails().getNumberOfFollowers();
    }
}
