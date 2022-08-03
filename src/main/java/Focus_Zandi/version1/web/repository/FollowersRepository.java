package Focus_Zandi.version1.web.repository;

import Focus_Zandi.version1.domain.Followers;
import Focus_Zandi.version1.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class FollowersRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    public void makeFollow(Followers followers) {
        em.persist(followers);
    }

    public void unFollow(long followeeId, Member follower) {
        String jpql = "delete from Followers m where m.followeeId =:followeeId and m.member = :follower";
        Query query = em.createQuery(jpql)
                .setParameter("followeeId", followeeId)
                .setParameter("follower", follower);
        query.executeUpdate();
    }

    public List<String> findFollowers(Member member) {
        List<Long> list = em.createQuery("select f.followeeId from Followers f where f.member = :member", Long.class)
                .setParameter("member", member)
                .getResultList();

        List<String> followedMembers = new ArrayList<>();

        for (Long aLong : list) {
            Member member1 = memberRepository.findById(aLong);
            followedMembers.add(member1.getUsername());
        }
        return followedMembers;
    }
}
