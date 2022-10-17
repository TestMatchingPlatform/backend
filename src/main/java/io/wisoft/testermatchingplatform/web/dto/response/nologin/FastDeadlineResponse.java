package io.wisoft.testermatchingplatform.web.dto.response.nologin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class FastDeadlineResponse {

    private final UUID id;

    private final String title;

    private final String makerNickname;

    private final String company;

    private final Long deadlineRemain;

    private final int reward;

    private final int apply;

    private final int participantCapacity;

    private final String symbolImageRoot;

}
