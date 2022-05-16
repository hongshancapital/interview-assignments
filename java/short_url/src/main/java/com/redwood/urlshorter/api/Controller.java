package com.redwood.urlshorter.api;


import com.redwood.urlshorter.model.SavedShortUrl;
import com.redwood.urlshorter.services.UrlShortenerService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.redwood.urlshorter.UrlShortenerApplication;
import com.redwood.urlshorter.model.NewUrl;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;

@RestController
@RequestMapping(value = UrlShortenerApplication.BASE_PATH)
@RequiredArgsConstructor
@Slf4j
public class Controller {
    @Autowired
    private final UrlShortenerService service;

    @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SavedShortUrl.class)))
    @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema()))
    @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema()))
    @PostMapping
    public ResponseEntity<SavedShortUrl> create(@Valid @RequestBody NewUrl shortUrl) {
        SavedShortUrl s = service.put(shortUrl);
        ResponseEntity.BodyBuilder builder = ResponseEntity.created(URI.create(UrlShortenerApplication.BASE_PATH + "/" + s.getKey()));
        return builder.body(s);
    }

    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SavedShortUrl.class)))
    @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema()))
    @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema()))
    @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema()))
    @GetMapping("/{key}")
    public SavedShortUrl listById(@NotBlank @PathVariable("key") String key) {
        return service.getByKey(key);
    }
}