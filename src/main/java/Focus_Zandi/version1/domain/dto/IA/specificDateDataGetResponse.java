package Focus_Zandi.version1.domain.dto.IA;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;

@Getter
@Setter
public class specificDateDataGetResponse {

    private String username;

    private Integer brokenTime;

    private Integer total_time;

    private Timestamp date;

    private ArrayList<subject> subjectRecord;
}
