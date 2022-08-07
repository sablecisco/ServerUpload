package Focus_Zandi.version1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class MemberDetails {

    @Id
    @GeneratedValue
    @Column(name = "MEMBERDETAILS_ID")
    private long id;

    @Column(nullable = false)
    private String role = "ROLE_USER";

    private String gender;
    private String dob;
    private int age;

    private String occupation;
    private String workPlace;
    private int numberOfFollowers = 0;
    private String memo = "한줄 소개를 입력해주세요";

    public static int calcAge(String dob) {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();

        int user_year = Integer.parseInt(dob.substring(0, 4));
        int user_month = Integer.parseInt(dob.substring(5, 7));
        int user_day = Integer.parseInt(dob.substring(8, 10));

        int age = 0;

        if (month > user_month) {
            age = year - user_year;
        } else if (month < user_month) {
            age = year - user_year + 1;
        } else if (month == user_month) {
            if (day >= user_day) {
                age = year - user_year;
            } else {
                age = age = year - user_year + 1;
            }
        }
        return age;
    }
}
