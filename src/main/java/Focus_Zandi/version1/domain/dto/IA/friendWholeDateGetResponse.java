package Focus_Zandi.version1.domain.dto.IA;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class friendWholeDateGetResponse {
    private String username;

    private String dDay;

    private String memo;

    private String imgUrl;

    private ArrayList<month> monthRecord;
}
