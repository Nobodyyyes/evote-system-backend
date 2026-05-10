package esmukanov.evote_system.hellgate.models.response;

import java.time.Instant;

public record ApiErrorResponse(

        int status,
        String error,
        String message,
        String path,
        Instant timestamp
) {
}
