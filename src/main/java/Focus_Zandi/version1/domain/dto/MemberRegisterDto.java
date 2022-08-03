package Focus_Zandi.version1.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberRegisterDto {

    private String username;
    private String password;
    private String email;

    private String name;
    private String gender;
    private String dob;

    private String occupation;
    private String place;
}
