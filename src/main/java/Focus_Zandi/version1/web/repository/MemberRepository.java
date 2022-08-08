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

    Member findById(long id);

    Member findByUsername(String username);

    Member findByUserToken(String providerId);

    Member findByName(String name);

    @Modifying
    @Query("Delete from Member m where m.username = :username")
    void deleteByUserName(@Param("username") String username);

    @Modifying
    @Query("Delete from MemberDetails md where md.id = :id")
    void deleteDetails(@Param("id") long id);
}
