package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Mission;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMissionIncludeImageResponse {
    private final UUID id;


    public static UpdateMissionIncludeImageResponse fromMission(Mission mission) {
        UpdateMissionIncludeImageResponse response = new UpdateMissionIncludeImageResponse(mission.getId());
        return response;
    }
}
