package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TesterListOfClosedMissionResponse {
    List<TesterOfClosedMissionDTO> testerListOfClosedTestList = new ArrayList<>();

    public static TesterListOfClosedMissionResponse fromApplyInformationList(List<ApplyInformation> applyInformationList) {
        TesterListOfClosedMissionResponse response = new TesterListOfClosedMissionResponse();
        for (ApplyInformation applyInformation : applyInformationList) {
            response.getTesterListOfClosedTestList().add(
                    TesterOfClosedMissionDTO.fromApplyInformation(applyInformation)
            );
        }

        return response;
    }
}
