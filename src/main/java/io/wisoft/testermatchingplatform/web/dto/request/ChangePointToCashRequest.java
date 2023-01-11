package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangePointToCashRequest {
    private long point;

    public static ChangePointToCashRequest newInstance(
            final long cash
    ) {
        ChangePointToCashRequest request = new ChangePointToCashRequest();
        request.point = cash;
        return request;
    }
}
