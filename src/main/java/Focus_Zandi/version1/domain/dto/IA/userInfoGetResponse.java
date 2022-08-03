package Focus_Zandi.version1.domain.dto.IA;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class userInfoGetResponse {

    private String username;

    private String dDay;

    private String dDayName;

    private String memo;

    private String imgUrl;

    private Integer month;

    private ArrayList<month> monthRecord;     //한달사이 1일,2일..의 total_time

    private Integer brokenCounter;

    private Integer total_time;
}
