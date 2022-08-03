package Focus_Zandi.version1.domain.dto;

import lombok.Data;

@Data
public class JwtReturner {

    private String accessToken;
    private String RefToken;

    public JwtReturner(String accessToken, String refToken) {
        this.accessToken = accessToken;
        RefToken = refToken;
    }
}
