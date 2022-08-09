package Focus_Zandi.version1.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class FolloweeReturner {
    private String username;
    private String memo;
    private int numberOfFollowers;
    private Map<String, List<MonthlyRecordsDto>> monthRecord;

    public FolloweeReturner(MemberReturnerDto followeeReturner, Map<String, List<MonthlyRecordsDto>> allByMonth) {
        this.username = followeeReturner.getFullName();
        this.memo = followeeReturner.getMemo();
        this.numberOfFollowers = followeeReturner.getNumberOfFollowers();
        this.monthRecord = allByMonth;
    }
}
