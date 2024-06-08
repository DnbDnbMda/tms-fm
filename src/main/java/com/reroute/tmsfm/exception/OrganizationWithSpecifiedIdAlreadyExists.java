package com.reroute.tmsfm.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class OrganizationWithSpecifiedIdAlreadyExists extends ResponseStatusException {
    public OrganizationWithSpecifiedIdAlreadyExists(HttpStatusCode status) {
        super(status);
    }

    public OrganizationWithSpecifiedIdAlreadyExists(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public OrganizationWithSpecifiedIdAlreadyExists(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }
}
