package io.wisoft.testermatchingplatform.web.dto.request.maker;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CompleteRequest {
    private List<UUID> completeTesterIdDTOList;

}