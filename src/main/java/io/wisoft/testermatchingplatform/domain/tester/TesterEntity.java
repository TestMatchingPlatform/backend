package io.wisoft.testermatchingplatform.domain.tester;

import io.wisoft.testermatchingplatform.domain.category.CategoryEntity;
import io.wisoft.testermatchingplatform.domain.grade.GradeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tester")
@AllArgsConstructor
@NoArgsConstructor

public class TesterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String phoneNumber;

    @JoinColumn(name = "PREFER_CATEGORY_ID")
    @ManyToOne
    private CategoryEntity preferCategory;

    private String introMessage;

    private String introPictureRef;

    @JoinColumn(name = "GRADE_ID")
    @ManyToOne
    private GradeEntity grade;

    private Timestamp registerTime;

    public static TesterEntity from(final Tester tester) {
        return new TesterEntity(
                tester.getId(),
                tester.getEmail(),
                tester.getPassword(),
                tester.getNickname(),
                tester.getPhoneNumber(),
                CategoryEntity.from(tester.getPreferCategory()),
                tester.getIntroMessage(),
                tester.getIntroPictureRef(),
                GradeEntity.from(tester.getGrade()),
                tester.getRegisterTime()
        );
    }

    public Tester toDomain() {
        return new Tester(
                this.id,
                this.email,
                this.password,
                this.nickname,
                this.phoneNumber,
                this.preferCategory == null ? null : this.preferCategory.toDomain(),
                this.introMessage,
                this.introPictureRef,
                this.grade == null ? null : this.grade.toDomain(),
                this.registerTime
        );
    }

}
