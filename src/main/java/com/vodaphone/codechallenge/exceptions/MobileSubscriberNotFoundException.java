package com.vodaphone.codechallenge.exceptions;

public class MobileSubscriberNotFoundException extends RuntimeException {

  private static final String COULD_NOT_FIND_NUMBER = "Could not find number ";
  /**
   * Serial Version UID
   */
  private static final long serialVersionUID = -4781557712979325035L;

  public MobileSubscriberNotFoundException(String msisdn) {
    super(COULD_NOT_FIND_NUMBER + msisdn);
  }
}
