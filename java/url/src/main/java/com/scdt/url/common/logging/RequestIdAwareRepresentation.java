package com.scdt.url.common.logging;

import com.scdt.url.common.ddd.Representation;
import lombok.Getter;
import org.slf4j.MDC;

import static com.scdt.url.common.logging.RequestIdMdcFilter.REQUEST_ID;


public abstract class RequestIdAwareRepresentation implements Representation {
    @Getter
    private final String requestId;

    protected RequestIdAwareRepresentation() {
        this.requestId = MDC.get(REQUEST_ID);
    }
}
