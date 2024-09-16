package com.scdt.url.common.logging;


import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.scdt.url.common.util.UuidGenerator.newUuid;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * Add request id to each request for logback logging.
 * If request contains `X-Request-Id` header then it's used as request id.
 * Otherwise a random request id is generated.
 */
@Component
@Order(HIGHEST_PRECEDENCE)
public class RequestIdMdcFilter implements WebFilter {

    public static final String REQUEST_ID = "requestId";
    private static final String HEADER_X_REQUEST_ID = "x-request-id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        populateMdc(headers);
        return chain.filter(exchange)
                .doFinally(t -> clearMdc());
    }


    private void populateMdc(HttpHeaders request) {
        MDC.put(REQUEST_ID, requestId(request));
    }

    private String requestId(HttpHeaders request) {
        var requestIdHeaders = request.getOrEmpty(HEADER_X_REQUEST_ID);
        String headerRequestId = requestIdHeaders.stream().findAny().orElse(null);
        return isNullOrEmpty(headerRequestId) ? newUuid() : headerRequestId;
    }

    private void clearMdc() {
        MDC.remove(REQUEST_ID);
    }

}