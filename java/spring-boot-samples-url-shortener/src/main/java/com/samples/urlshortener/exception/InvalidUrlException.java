package com.samples.urlshortener.exception;

/**
 * Invalid Url Exception.
 *
 * @author liuqu
 */
public class InvalidUrlException extends RuntimeException {

  public InvalidUrlException(String message) {
    super(message);
  }
}
