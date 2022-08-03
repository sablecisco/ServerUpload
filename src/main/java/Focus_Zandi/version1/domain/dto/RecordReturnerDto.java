package Focus_Zandi.version1.domain.dto;

import Focus_Zandi.version1.domain.Records;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecordReturnerDto {

    private int brokenCounter;
    private int maxConcentrationTime;
    private int total_time;
    private String timeStamp;

    public RecordReturnerDto(Records records) {
        this.brokenCounter = records.getBrokenCounter();
        this.maxConcentrationTime = records.getMaxConcentrationTime();
        this.total_time = records.getTotal_time();
        this.timeStamp = records.getYear() + "-" + records.getMonth() + "-" + records.getDay();
    }
}
