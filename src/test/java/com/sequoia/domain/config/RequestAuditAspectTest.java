package com.sequoia.domain.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestAuditAspectTest {

    @Test
    public void test() {
        RequestAuditAspect auditAspect = new RequestAuditAspect();
        auditAspect.doBeforeController(null);
        assertDoesNotThrow(() -> auditAspect.doAfterController(null, null));
    }

}