package interview.assignments.zhanggang.core.shortener.adapter.api;

import interview.assignments.zhanggang.core.shortener.adapter.api.request.URLRequest;
import interview.assignments.zhanggang.core.shortener.adapter.api.response.URLResponse;
import interview.assignments.zhanggang.core.shortener.application.ShortenerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/shortener")
@RequiredArgsConstructor
public class ShortenerController {
    private final ShortenerApplicationService shortenerApplicationService;

    @PostMapping("/short-url")
    public Mono<URLResponse> getShortUrl(@RequestBody URLRequest urlRequest) {
        return shortenerApplicationService.getShortUrl(urlRequest.getUrl())
                .map(URLResponse::new);
    }

    @PostMapping("/original-url")
    public Mono<URLResponse> getOriginalUrl(@RequestBody URLRequest urlRequest) {
        return shortenerApplicationService.getOriginalUrl(urlRequest.getUrl())
                .map(URLResponse::new);
    }
}
