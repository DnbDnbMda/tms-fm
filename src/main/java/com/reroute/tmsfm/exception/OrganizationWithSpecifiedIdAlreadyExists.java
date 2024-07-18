package com.reroute.tmsfm.exception;

public class OrganizationWithSpecifiedIdAlreadyExists extends RuntimeException {
    public OrganizationWithSpecifiedIdAlreadyExists(String message) {
        super(message);
    }
}
