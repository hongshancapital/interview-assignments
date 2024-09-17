package performance;

import io.gatling.javaapi.core.PauseType;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static performance.GatlingConstant.*;

public class GetShortUrlSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = http.baseUrl(BASE_URL);
    Iterator<Map<String, Object>> feeder =
            Stream.generate((Supplier<Map<String, Object>>) () -> {
                        String randomString = UUID.randomUUID().toString();
                        return Collections.singletonMap("randomString", randomString);
                    }
            ).iterator();

    ScenarioBuilder scn = scenario("get short url")
            .feed(feeder)
            .pause(Duration.ofMillis(200))
            .repeat(100)
            .on(
                    exec(
                            http("get short url")
                                    .post("/shortener/short-url")
                                    .header(ACCEPT, APPLICATION_JSON_TYPE)
                                    .header(CONTENT_TYPE, APPLICATION_JSON_TYPE)
                                    .body(StringBody("{\"url\":\"https://www.test.com/search=#{randomString}\"}"))
                    )
            );

    {
        setUp(scn.injectOpen(rampUsers(15).during(Duration.ofSeconds(5))))
                .maxDuration(Duration.ofSeconds(10))
                .protocols(httpProtocol);
    }
}
