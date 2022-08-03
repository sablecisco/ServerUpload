package Focus_Zandi.version1.domain.dto;

import lombok.Data;

@Data
public class DetailsDto {

    private String role = "ROLE_USER";

    private String gender;
    private String dob;

    private String occupation;
    private String workPlace;
    private String memo;
}
