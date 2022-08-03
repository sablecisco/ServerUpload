package Focus_Zandi.version1.domain.dto.IA;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userInfoModify {

    private String username;

    private String dDay;

    private String dDayName;

    private String memo;

    private String imgUrl;
}

//1.2 유저 정보 수정은 Request와 Response가 같음
