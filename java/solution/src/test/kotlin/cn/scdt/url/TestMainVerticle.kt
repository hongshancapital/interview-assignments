package cn.scdt.url

import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.WebClient
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class TestMainVerticle {

  @BeforeEach
  fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
    vertx.deployVerticle(MainVerticle(), testContext.succeeding { testContext.completeNow() })
  }

  @Test
  fun testUrl(vertx: Vertx, testContext: VertxTestContext) {
    val fullUrl = "https://hello.world/this_is_a_long_url1"
    val client = WebClient.create(vertx)

    client.put(8080, "localhost", "/save").sendBuffer(Buffer.buffer(fullUrl)).onFailure {
      testContext.failNow("失败0")
    }.onSuccess {
      val short = it.bodyAsString()
      client.get(8080, "localhost", "/$short").send()
        .onFailure { testContext.failNow("失败1") }
        .onSuccess { r ->
          val ret = r.bodyAsString()
          Assertions.assertTrue(ret == fullUrl)
          testContext.completeNow()
        }
    }
  }

}
