package com.scdt.shorturl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class HashidsTest {

  @Test
  public void test_large_number() {
    final long num_to_hash = 9007199254740992L;
    final Hashids a = new Hashids("this is my salt");
    final String res = a.encode(num_to_hash);
    final long[] b = a.decode(res);
    Assertions.assertEquals(num_to_hash, b[0]);
  }

  @Test
  public void test_large_number_not_supported() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      final long num_to_hash = 9007199254740993L;
      final Hashids a = new Hashids("this is my salt");
      a.encode(num_to_hash);
    });
  }

  @Test
  public void test_wrong_decoding() {
    final Hashids a = new Hashids("this is my pepper");
    final long[] b = a.decode("NkK9");
    Assertions.assertEquals(0, b.length);
  }

  @Test
  public void test_one_number() {
    final String expected = "NkK9";
    String res;
    final long num_to_hash = 12345L;
    long[] res2;
    final Hashids a = new Hashids("this is my salt");
    res = a.encode(num_to_hash);
    Assertions.assertEquals(expected, res);
    res2 = a.decode(expected);
    Assertions.assertEquals(1, res2.length);
    Assertions.assertEquals(num_to_hash, res2[0]);
  }

  @Test
  public void test_serveral_numbers() {
    final String expected = "aBMswoO2UB3Sj";
    String res;
    final long[] num_to_hash = { 683L, 94108L, 123L, 5L };
    long[] res2;
    final Hashids a = new Hashids("this is my salt");
    res = a.encode(num_to_hash);
    Assertions.assertEquals(expected, res);
    res2 = a.decode(expected);
    Assertions.assertEquals(num_to_hash.length, res2.length);
    Assertions.assertTrue(Arrays.equals(res2, num_to_hash));
  }

  @Test
  public void test_specifying_custom_hash_alphabet() {
    final String expected = "b332db5";
    String res;
    final long num_to_hash = 1234567L;
    long[] res2;
    final Hashids a = new Hashids("this is my salt", 0, "0123456789abcdef");
    res = a.encode(num_to_hash);
    Assertions.assertEquals(expected, res);
    res2 = a.decode(expected);
    Assertions.assertEquals(num_to_hash, res2[0]);
  }

  @Test
  public void test_specifying_custom_hash_length() {
    final String expected = "gB0NV05e";
    String res;
    final long num_to_hash = 1L;
    long[] res2;
    final Hashids a = new Hashids("this is my salt", 8);
    res = a.encode(num_to_hash);
    Assertions.assertEquals(expected, res);
    res2 = a.decode(expected);
    Assertions.assertEquals(1, res2.length);
    Assertions.assertEquals(num_to_hash, res2[0]);
  }

  @Test
  public void test_randomness() {
    final String expected = "1Wc8cwcE";
    String res;
    final long[] num_to_hash = { 5L, 5L, 5L, 5L };
    long[] res2;
    final Hashids a = new Hashids("this is my salt");
    res = a.encode(num_to_hash);
    Assertions.assertEquals(expected, res);
    res2 = a.decode(expected);
    Assertions.assertEquals(num_to_hash.length, res2.length);
    Assertions.assertTrue(Arrays.equals(res2, num_to_hash));
  }

  @Test
  public void test_randomness_for_incrementing_numbers() {
    final String expected = "kRHnurhptKcjIDTWC3sx";
    String res;
    final long[] num_to_hash = { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L };
    long[] res2;
    final Hashids a = new Hashids("this is my salt");
    res = a.encode(num_to_hash);
    Assertions.assertEquals(res, expected);
    res2 = a.decode(expected);
    Assertions.assertEquals(num_to_hash.length, res2.length);
    Assertions.assertTrue(Arrays.equals(res2, num_to_hash));
  }

  @Test
  public void test_randomness_for_incrementing() {
    Hashids a;
    a = new Hashids("this is my salt");
    Assertions.assertEquals("NV", a.encode(1L));
    Assertions.assertEquals("6m", a.encode(2L));
    Assertions.assertEquals("yD", a.encode(3L));
    Assertions.assertEquals("2l", a.encode(4L));
    Assertions.assertEquals("rD", a.encode(5L));
  }

  @Test
  public void test_for_vlues_greater_int_maxval() {
    final Hashids a = new Hashids("this is my salt");
    Assertions.assertEquals("Y8r7W1kNN", a.encode(9876543210123L));
  }

  @Test
  public void test_issue10() {
    final String expected = "3kK3nNOe";
    String res;
    final long num_to_hash = 75527867232L;
    long[] res2;
    final Hashids a = new Hashids("this is my salt");
    res = a.encode(num_to_hash);
    Assertions.assertEquals(expected, res);
    res2 = a.decode(expected);
    Assertions.assertEquals(1, res2.length);
    Assertions.assertEquals(num_to_hash, res2[0]);
  }

  @Test
  public void test_issue23() {
    final String expected = "9Q7MJ3LVGW";
    String res;
    final long num_to_hash = 1145L;
    long[] res2;
    final Hashids a = new Hashids("MyCamelCaseSalt", 10, "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789");
    res = a.encode(num_to_hash);
    Assertions.assertEquals(expected, res);
    res2 = a.decode(expected);
    Assertions.assertEquals(num_to_hash, res2[0]);
  }

  @Test
  public void test_issue30() {
    final String expected = "";
    String res;
    final long num_to_hash = -1L;
    final Hashids a = new Hashids("this is my salt");
    res = a.encode(num_to_hash);
    Assertions.assertEquals(expected, res);
  }

  @Test
  public void test_issue31() {
    final long[] numbers = new long[500000];
    long current = Hashids.FIXED_MAX_NUMBER;
    for (int i = 0; i < numbers.length; i++) {
      numbers[i] = current--;
    }
    final Hashids a = new Hashids("this is my salt");
    Assertions.assertNotEquals("", a.encode(numbers));
  }

  @Test
  public void test_issue32() {
    final long num_to_hash = -1;
    final Hashids a = new Hashids("this is my salt");
    Assertions.assertEquals("", a.encode(num_to_hash));
  }

  @Test
  public void test_issue45() {
    Hashids hashids = new Hashids("this is my salt");
    long[] numbers = hashids.decode("()");
    Assertions.assertEquals(0, numbers.length);
    numbers = hashids.decode("[]");
    Assertions.assertEquals(0, numbers.length);
    numbers = hashids.decode("недействительный");
    Assertions.assertEquals(0, numbers.length);
    numbers = hashids.decode("無效");
    Assertions.assertEquals(0, numbers.length);
  }

}