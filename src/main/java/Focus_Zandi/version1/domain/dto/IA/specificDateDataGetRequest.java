package Focus_Zandi.version1.domain.dto.IA;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class specificDateDataGetRequest {

    private String username;

    private Timestamp date;
}
