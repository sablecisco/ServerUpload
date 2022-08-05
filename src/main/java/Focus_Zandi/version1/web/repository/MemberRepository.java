package Focus_Zandi.version1.web.repository;

import Focus_Zandi.version1.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    //findBy는 규칙
    // select * from user where username = ?;
    //라는 sql 자동 생성

    public Member findById(long id);

    public Member findByUsername(String username);

    public Member findByUserToken(String providerId);

    public Member findByName(String name);

//    @Modifying
//    @Query("Delete ")
//    public void deleteByUserName(@Param("username") String username);
}
