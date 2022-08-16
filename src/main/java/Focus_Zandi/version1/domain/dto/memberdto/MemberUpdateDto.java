package Focus_Zandi.version1.domain.dto.memberdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDto {

    private String name;
    private String gender;
    private String dob;
    private String email;

    private String occupation;
    private String place;
}