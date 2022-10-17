package io.wisoft.testermatchingplatform.web.dto.response.maker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ConfirmApplyResponse {
    List<UUID> successApplyInformationUUIDDTO;
}
