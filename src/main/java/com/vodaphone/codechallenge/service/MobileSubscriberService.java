package com.vodaphone.codechallenge.service;

import com.vodaphone.codechallenge.model.MobileSubscriber;

public interface MobileSubscriberService {
  
  MobileSubscriber getMobileSubscriberByNumber(String mobileNumber);

}
