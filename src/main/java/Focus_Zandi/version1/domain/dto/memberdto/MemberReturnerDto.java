package Focus_Zandi.version1.domain.dto.memberdto;

import Focus_Zandi.version1.domain.Member;
import Focus_Zandi.version1.domain.MemberDetails;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberReturnerDto {

    private String userToken;
    private String email;
    private String fullName;
    private String memo;
    private int numberOfFollowers;

    public MemberReturnerDto(Member member, MemberDetails details) {
        this.userToken = member.getUserToken();
        this.fullName = member.getName();
        this.email = member.getEmail();
        this.memo = details.getMemo();
        this.numberOfFollowers = details.getNumberOfFollowers();
    }
}
