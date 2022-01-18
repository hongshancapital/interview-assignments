package performance;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static performance.GatlingConstant.*;

public class GetUrlFullStepSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = http.baseUrl(BASE_URL);

    ScenarioBuilder scn = scenario("get url full step")
            .pause(Duration.ofMillis(200))
            .repeat(100)
            .on(
                    exec(
                            http("get short url")
                                    .post("/shortener/short-url")
                                    .header(ACCEPT, APPLICATION_JSON_TYPE)
                                    .header(CONTENT_TYPE, APPLICATION_JSON_TYPE)
                                    .body(StringBody("{\"url\":\"https://www.test.com/search=" + UUID.randomUUID() + "\"}"))
                                    .check(
                                            jsonPath("$.url").saveAs("shortURL")
                                    )
                    ).exec(
                            http("get original url")
                                    .post("/shortener/original-url")
                                    .header(ACCEPT, APPLICATION_JSON_TYPE)
                                    .header(CONTENT_TYPE, APPLICATION_JSON_TYPE)
                                    .body(StringBody("{\"url\":\"#{shortURL}\"}"))
                    )
            );

    {
        setUp(scn.injectOpen(rampUsers(50).during(Duration.ofSeconds(5))))
                .maxDuration(Duration.ofSeconds(10))
                .protocols(httpProtocol);
    }
}
