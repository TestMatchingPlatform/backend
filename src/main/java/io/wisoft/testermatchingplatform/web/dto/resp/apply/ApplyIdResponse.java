package io.wisoft.testermatchingplatform.web.dto.resp.apply;

import io.wisoft.testermatchingplatform.domain.apply.Apply;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyIdResponse {
    private Long id;
    public static ApplyIdResponse from(final Apply apply){
        return new ApplyIdResponse(
                apply.getId()
        );
    }
}
