package io.wisoft.testermatchingplatform.domain.tester;

import io.wisoft.testermatchingplatform.domain.BaseTime;
import io.wisoft.testermatchingplatform.domain.category.Category;
import io.wisoft.testermatchingplatform.domain.grade.Grade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tester")
@NoArgsConstructor
public class Tester extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String phoneNumber;

    private String refreshToken;

    @JoinColumn(name = "PREFER_CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category preferCategory;

    private String introMessage;

    private String introPictureRef;

    @JoinColumn(name = "GRADE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Grade grade;

    public Tester(String email, String password, String nickname, String phoneNumber, Category preferCategory, String introMessage, String introPictureRef, Grade grade) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.preferCategory = preferCategory;
        this.introMessage = introMessage;
        this.introPictureRef = introPictureRef;
        this.grade = grade;
    }
}
