package com.vodaphone.codechallenge.exceptions;

public class MobileSubscriberAlreadyExistsException extends RuntimeException {

  /**
   * Serial version UID
   */
  private static final long serialVersionUID = 5396559750359124910L;

  public MobileSubscriberAlreadyExistsException(String msisdn) {
    super("Number already exists " + msisdn);
  }
}
