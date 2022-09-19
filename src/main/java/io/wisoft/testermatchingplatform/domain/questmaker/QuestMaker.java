package io.wisoft.testermatchingplatform.domain.questmaker;

import io.wisoft.testermatchingplatform.domain.BaseTime;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "quest_maker")
@NoArgsConstructor
public class QuestMaker extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

    private String refreshToken;

    public QuestMaker(String email, String password, String nickname, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public void checkPassword(final String confirmPassword) {
        if (!this.password.equals(confirmPassword)) {
            throw new IllegalArgumentException("confirmPassword and password not equal");
        }
    }
}
