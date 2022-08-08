package Focus_Zandi.version1.web.repository;

import Focus_Zandi.version1.domain.Member;
import Focus_Zandi.version1.domain.Records;
import Focus_Zandi.version1.domain.dto.MonthlyRecordsDto;
import Focus_Zandi.version1.domain.dto.MyFollowersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Transactional
public class RecordRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    //레코드 Dto를 받아서, 이를 Records로 변환하고 db에 저장
    public void save(Records records) {
        em.persist(records);
    }

//    public Records findRecordByMemberId(long memberId) {
////        Records records = em.find(Records.class, memberId); // 이거는 pk만 가능
//        List<Records> resultList = em.createQuery("select r from Records r where r.member = :memberId", Records.class)
//                .setParameter("memberId", memberId)
//                .getResultList();
//        for (Records records : resultList) {
//            long recordId = records.getRecordId();
//            System.out.println("recordId = " + recordId);
//        }
//        Records records = resultList.get(0);
//        return records;
//    }

    public Records findRecordByTimeStamp(Member member, String timeStamp) {
        String year = timeStamp.substring(0, 4);
        String month = timeStamp.substring(5, 7);
        String day = timeStamp.substring(8,10);
        List<Records> resultList = em.createQuery("select r from Records r where r.member = :member and r.year = :year and r.month = :month and r.day = :day", Records.class)
                .setParameter("member", member)
                .setParameter("year", year)
                .setParameter("month", month)
                .setParameter("day", day)
                .getResultList();
        if (resultList.isEmpty()) {
            return null;
        }

        return resultList.get(0);
    }

//    public List<Integer> findAllByMonth(String month, Member member) {
//        LocalDate now = LocalDate.now();
//        String year = Integer.toString(now.getYear());
//        List resultList = em.createQuery("Select r.total_time from Records r where r.member = :member and r.year = :year and r.month = :month")
//                .setParameter("member", member)
//                .setParameter("year", year)
//                .setParameter("month", month)
//                .getResultList();
//
//        return resultList;
//    }

//    public List<MonthlyRecordsDto> findAllByMonthV2(String month, Member member) {
//        LocalDate now = LocalDate.now();
//        String year = Integer.toString(now.getYear());
//        List<Records> records = em.createQuery("Select r from Records r where r.member = :member and r.year = :year and r.month = :month")
//                .setParameter("member", member)
//                .setParameter("year", year)
//                .setParameter("month", month)
//                .getResultList();
//
//        List<MonthlyRecordsDto> recordsDtoList = new ArrayList<>();
//        Map<String, List> monthlyRecord = new HashMap<>();
//
//        for (Records record : records) {
//            recordsDtoList.add(new MonthlyRecordsDto(record));
//        }
//        monthlyRecord.put("monthlyRecord", recordsDtoList);
//
//        return recordsDtoList;
//    }

    public Map<String, List<MonthlyRecordsDto>> findAllByMonth(String month, Member member) {
        LocalDate now = LocalDate.now();
        String year = Integer.toString(now.getYear());
        List<Records> records = em.createQuery("Select r from Records r where r.member = :member and r.year = :year and r.month = :month")
                .setParameter("member", member)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();

        List<MonthlyRecordsDto> recordsDtoList = new ArrayList<>();
        Map<String, List<MonthlyRecordsDto>> monthRecord = new HashMap<>();

        for (Records record : records) {
            recordsDtoList.add(new MonthlyRecordsDto(record));
        }
        monthRecord.put("monthRecord", recordsDtoList);

        return monthRecord;
    }

    public List<MyFollowersDto> findFollowersDailyRecords(List<String> followers) {
        List<MyFollowersDto> resultList = new ArrayList<>();
        for (String follower : followers) {
            Member findMember = memberRepository.findByUsername(follower);
            int maxConcentrationTime = findRecordByTimeStamp(findMember, LocalDateTime.now().toString()).getMaxConcentrationTime();
            MyFollowersDto myFollowersDto = new MyFollowersDto(follower, maxConcentrationTime);
            resultList.add(myFollowersDto);
        }
        return resultList;
    }

    public void deleteByMember(Member member) {
        em.createQuery("Delete from Records r where r.member = :member")
                .setParameter("member", member)
                .executeUpdate();
    }

//    public List findFollowersDailyRecords(Member member) {
////        String jpql = "Select r.member, r.maxConcentrationTime from Followers f join fetch Records r on f.followeeId =  where f.member = :member";
////        List<FriendRankDto> resultList = em.createQuery(jpql)
////                .setParameter("member", member)
////                .getResultList();
//
//    }

}
