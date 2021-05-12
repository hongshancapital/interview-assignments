package net.rungo.zz.domain;

import lombok.Data;

import java.util.concurrent.CompletableFuture;

@Data
public class RequestParam {
    private String  domainUrl;
    private CompletableFuture<String> future;
}
