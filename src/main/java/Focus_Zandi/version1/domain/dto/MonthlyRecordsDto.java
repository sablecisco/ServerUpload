package Focus_Zandi.version1.domain.dto;

import Focus_Zandi.version1.domain.Records;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MonthlyRecordsDto {

    private int concentratedTime;
    private int brokenCount;
    private String date;

    public MonthlyRecordsDto(Records records) {
        this.concentratedTime = records.getTotal_time();
        this.brokenCount = records.getBrokenCounter();
        this.date = records.getYear() + "-" + records.getMonth() + "-" + records.getDay();
    }
}
