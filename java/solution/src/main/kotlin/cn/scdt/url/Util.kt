package cn.scdt.url

/**
 * 工具类：编号到短连接的转换
 * 把 8 位短链接看成 8 位 62 进制数 (a=0,9=61)
 */
object Util {
  /**
   * 短连接转编号
   * @param u 短连接
   * @return 编号
   */
  fun u2l(u: String): Long {
    if (u.isEmpty()) return 0
    var ret: Long = 0
    for (i in u.indices) {
      when (u[i]) {
        in 'a'..'z' -> ret = ret * 62 + (u[i] - 'a')
        in 'A'..'Z' -> ret = ret * 62 + 26 + (u[i] - 'A')
        in '0'..'9' -> ret = ret * 62 + 52 + (u[i] - '0')
      }
    }
    return ret
  }

  /**
   * 根据编号换成短链接
   * @param l 编号
   * @return 短链接
   */
  fun l2u(l: Long): String {
    val dic = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    val sb = StringBuilder()
    var n = l
    var i = 0
    while (n >= 0) {
      sb.append(dic[(n % 62).toInt()])
      n /= 62
      i++
      if (i > 7) break
    }
    return sb.reverse().toString()
  }

}
