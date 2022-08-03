package Focus_Zandi.version1.domain.dto;

import Focus_Zandi.version1.domain.Member;
import Focus_Zandi.version1.domain.MemberDetails;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberReturnerDto {

    private String username;
    private String email;

    private String name;
    private String gender;
    private String dob;
    private int age;

    private String occupation;
    private String place;
    private String memo;

    public MemberReturnerDto(Member member) {
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.name = member.getName();
        MemberDetails details = member.getMemberDetails();
        this.gender = details.getGender();
        this.age = details.getAge();
        this.dob = details.getDob();
        this.occupation = details.getOccupation();
        this.place = details.getWorkPlace();
    }

    public MemberReturnerDto(Member member, MemberDetails details) {
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.name = member.getName();
        this.gender = details.getGender();
        this.age = details.getAge();
        this.dob = details.getDob();
        this.occupation = details.getOccupation();
        this.place = details.getWorkPlace();
        this.memo = details.getMemo();
    }
}
