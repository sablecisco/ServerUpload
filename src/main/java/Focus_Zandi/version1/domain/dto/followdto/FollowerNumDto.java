package Focus_Zandi.version1.domain.dto.followdto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FollowerNumDto {

    private int numberOfFollowers;

    public FollowerNumDto(int numberOfFollower) {
        this.numberOfFollowers = numberOfFollower;
    }
}
