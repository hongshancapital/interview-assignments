package cn.scdt.url

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestUtil {
  @Test
  fun test1() {
    val u = "aaaaaaaa"
    val l = Util.u2l(u)
    println("l=$l")
    val u2 = Util.l2u(l)
    println("||$u2||")
    Assertions.assertTrue(u == u2)
  }

  @Test
  fun test2() {
    val l = 2L
    val u = Util.l2u(l)
    println("|| this is $u ||")
    val l2 = Util.u2l(u)
    Assertions.assertEquals(l, l2)
  }
}
