package Focus_Zandi.version1.domain;

import Focus_Zandi.version1.domain.dto.recorddto.RecordsDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "Records")
public class Records extends JpaBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 연관관계가 이게 맞나
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Id @GeneratedValue
    @Column(name = "RECORD_ID")
    private long recordId;

    @Column(nullable = false)
    private int brokenCounter;

    @Column(nullable = false)
    private int maxConcentrationTime;

    @Column(nullable = false)
    private int total_time;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private String month;

    @Column(nullable = false)
    private String day;

    // 비즈니스 로직 //
    public static Records createRecords(Member member, RecordsDto recordsDto) {
        Records records = new Records();
        records.setMember(member);
        records.setYear(recordsDto.getDate().substring(0,4));
        records.setMonth(recordsDto.getDate().substring(5,7));
        records.setDay(recordsDto.getDate().substring(8,10));

        records.setTotal_time(recordsDto.getConcentratedTime());
        records.setBrokenCounter(recordsDto.getBrokenCount());
        records.setMaxConcentrationTime(0);

        return records;
    }
}
