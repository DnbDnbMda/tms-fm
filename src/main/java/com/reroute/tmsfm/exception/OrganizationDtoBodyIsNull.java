package com.reroute.tmsfm.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class OrganizationDtoBodyIsNull extends ResponseStatusException {
    public OrganizationDtoBodyIsNull(HttpStatusCode status) {
        super(status);
    }

    public OrganizationDtoBodyIsNull(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public OrganizationDtoBodyIsNull(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }
}
