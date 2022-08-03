package Focus_Zandi.version1.domain.dto.IA;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class studyRecordSave {
    private String username;

    private Integer brokenCounter;

    private Integer total_time;

    private Timestamp date;

}

//2.1 오늘공부기록 저장은 Request 와 Response 가 같음음