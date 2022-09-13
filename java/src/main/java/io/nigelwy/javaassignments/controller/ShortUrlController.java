package io.nigelwy.javaassignments.controller;

import io.nigelwy.javaassignments.api.ShortUrlApi;
import io.nigelwy.javaassignments.api.response.GenerateShorturlResponse;
import io.nigelwy.javaassignments.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ShortUrlController implements ShortUrlApi {

    private final ShortUrlService service;

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GenerateShorturlResponse generateShortUrl(@RequestParam String originUrl) {
        return new GenerateShorturlResponse(service.generateShortUrl(originUrl), originUrl);
    }

    @Override
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> getOriginUrl(@PathVariable String shortUrl) {
        return Optional.ofNullable(service.getOriginUrl(shortUrl))
                .map(originUrl -> ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, originUrl)
                        .<Void>build())
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid url")
    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(ConstraintViolationException exp) {
        log.error(exp.getMessage(), exp);
    }
}
