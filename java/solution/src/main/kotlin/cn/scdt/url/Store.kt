package cn.scdt.url

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.await
import io.vertx.redis.client.Redis
import io.vertx.redis.client.RedisAPI
import io.vertx.redis.client.RedisOptions

/**
 * 仓库类
 */
class Store(vertx: Vertx, config: JsonObject) {
  private val v = vertx
  private val connectionString = config.getString("connectionString") ?: "redis://127.0.0.1"
  private lateinit var redis: RedisAPI

  suspend fun initRedis() {
    val client = Redis.createClient(
      v,
      RedisOptions().setConnectionString(connectionString).setMaxPoolSize(8).setMaxPoolWaiting(32)
    ).connect().await()
    redis = RedisAPI.api(client)
  }

  /**
   * 对于已经存过的，通过全链接获取编号，不浪费
   * @param fullUrl - 全链接
   * @return 编号
   */
  suspend fun getId(fullUrl: String): Long {
    val last = redis.get(fullUrl).await()
    return if (last == null) {
      val id = getId()
      setAll(fullUrl, id)
      id
    } else {
      last.toLong()
    }
  }

  /**
   * 通过编号，获取全链接
   * @param id - 编号
   * @return 全链接
   */
  suspend fun getFullUrl(id: Long): String {
    val f = redis.get(id.toString()).await()
    return f?.toString() ?: ""
  }

  /**
   * 对于已经获取编号的，保存长链接对应的编号
   * @param fullUrl - 全链接
   * @param id - 编号
   */
  private suspend fun setAll(fullUrl: String, id: Long) {
    redis.set(listOf(fullUrl, id.toString())).await()
    redis.set(listOf(id.toString(), fullUrl)).await()
  }

  /**
   * 对于没有保存过的地址，直接产生编号
   * 超过设定容量就归零，我们只支持 8 字长
   */
  private suspend fun getId(): Long {
    val id = redis.incr("id").await().toLong()
    if (id > 218340105584896) {
      redis.set(listOf("id", "0")).await()
      return 0L
    }
    return id
  }
}
