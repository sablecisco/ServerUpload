package Focus_Zandi.version1.domain.dto.followdto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyFollowersDto {

    private String username;
    private int maxConcentrationTime;

    public MyFollowersDto(String follower, int maxConcentrationTime) {
        this.maxConcentrationTime = maxConcentrationTime;
        this.username = follower;
    }
}
