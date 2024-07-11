package com.reroute.tmsfm.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class OrganizationNotFoundException extends ResponseStatusException {
    public OrganizationNotFoundException(HttpStatusCode status) {
        super(status);
    }

    public OrganizationNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public OrganizationNotFoundException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
