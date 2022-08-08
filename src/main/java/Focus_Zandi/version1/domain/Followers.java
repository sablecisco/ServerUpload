package Focus_Zandi.version1.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter @Setter
public class Followers {

    @Id @GeneratedValue
    @Column(name = "FOLLOWERS_TABLE_ID")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "FOLLOWEE_ID", nullable = false)
    private long followeeId;

//    @Column(nullable = false)
//    private boolean status = true;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    //B logic

    public static Followers createFollowerShip(long id, Member member) {
        Followers followers = new Followers();
        followers.setFolloweeId(id);
        followers.setMember(member);
        return followers;
    }
}
