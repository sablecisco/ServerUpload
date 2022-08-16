package Focus_Zandi.version1.domain.dto.followdto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FollowerNumberReturnerNumDto {

    private int numberOfFollowers;

    public FollowerNumberReturnerNumDto(int numberOfFollower) {
        this.numberOfFollowers = numberOfFollower;
    }
}
