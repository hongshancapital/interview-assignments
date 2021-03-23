package cn.scdt.url

import io.vertx.core.Context
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.async

/**
 * Web 服务
 */
class MainVerticle : CoroutineVerticle() {

  /**
   * 咱家仓库
   */
  private lateinit var store: Store

  /**
   * 初始化
   */
  override fun init(vertx: Vertx, context: Context) {
    super.init(vertx, context)
    store = Store(vertx, context.config())
  }

  /**
   * 处理短连接的 handler
   */
  private val getHandler = Handler<RoutingContext> {
    async {
      val path = it.request().path()
      val id = Util.u2l(path)
      val full = store.getFullUrl(id)
      if (full.isEmpty()) {
        it.response().statusCode = 404
        it.end()
      } else {
        it.response().end(full)
      }
    }
  }

  /**
   * 处理保存长链接的 handler
   */
  private val saveHandler = Handler<RoutingContext> {
    val fullUrl = it.bodyAsString
    async {
      val id = store.getId(fullUrl)
      it.response().end(Util.l2u(id))
    }
  }

  /**
   * 启动服务
   */
  override suspend fun start() {
    store.initRedis()
    val r = Router.router(vertx)
    r.route().handler(BodyHandler.create())
    r.put("/save").handler(saveHandler)
    r.get().handler(getHandler)
    vertx.createHttpServer()
      .requestHandler(r)
      .listen(8080) { http ->
        if (http.succeeded()) {
          println("HTTP server started.")
        } else {
          println("failed")
        }
      }
  }
}
