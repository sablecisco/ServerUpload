package Focus_Zandi.version1.domain.dto.IA;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class homeScreenGetResponse {

    private Timestamp date;
    private String dDay;
    private Integer total_time;
}
