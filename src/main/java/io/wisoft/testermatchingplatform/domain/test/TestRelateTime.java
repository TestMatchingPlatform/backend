package io.wisoft.testermatchingplatform.domain.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TestRelateTime {
    @NotNull
    private Date recruitmentTimeStart;
    @NotNull
    private Date recruitmentTimeLimit;
    @NotNull
    private Date durationTimeStart;
    @NotNull
    private Date durationTimeLimit;
}