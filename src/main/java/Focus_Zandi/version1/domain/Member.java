package Focus_Zandi.version1.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter @Setter
@RequiredArgsConstructor
@Entity
@Table(name = "Members")
public class Member extends JpaBaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBERDETAILS_ID")
    private MemberDetails memberDetails;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "STATUS_ID")
//    private MemberStatus memberStatus;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String userToken;

//    @CreationTimestamp
//    private Timestamp createdAt;
//    @UpdateTimestamp
//    private Timestamp updatedAt;

    @Builder
    public Member(String username, String password, String email, String name, String userToken, MemberDetails memberDetails) {
        this.memberDetails = memberDetails;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.userToken = userToken;
    }
}
