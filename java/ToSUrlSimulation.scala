package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.util.Random

class ToSUrlSimulation extends Simulation {

  var randomString =
    Iterator.continually(Map("randstring" -> ( "https://" + Random.alphanumeric.take(10).mkString + ".com" )))

  val httpProtocol = http 
    .baseUrl("http://localhost:8115")
    .acceptHeader("text/html,application/xhtml+xml,application/json,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
  //设置baseurl和header头    

  val scn = scenario("BasicSimulation")
    .feed(randomString)
    .exec(http("request_1")
      .post("http://localhost:8115/rt/scdt/toSUrl")
      .body(StringBody("${randstring}")))
  setUp(
      scn
        .inject(
          constantUsersPerSec(500) //设置并发用户数
          during (20 seconds) randomized
          )
        .protocols(httpProtocol)
      )
}
