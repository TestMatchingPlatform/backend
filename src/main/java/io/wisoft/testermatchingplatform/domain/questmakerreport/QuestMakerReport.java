package io.wisoft.testermatchingplatform.domain.questmakerreport;

import io.wisoft.testermatchingplatform.domain.apply.Apply;
import io.wisoft.testermatchingplatform.domain.reportpolicy.ReportPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class QuestMakerReport {

    private Long id;
    private Apply apply;
    private ReportPolicy report;
    private String title;
    private Timestamp registerTime;
    private boolean result;
}