package com.vodaphone.codechallenge.service;

import java.util.List;

import com.vodaphone.codechallenge.model.MobileSubscriber;

public interface MobileSubscriberService {
  
  MobileSubscriber getMobileSubscriberByNumber(String mobileNumber);

  List<MobileSubscriber> getAllMobileSubscribers();

  List<MobileSubscriber> getMobileSubscriberByCustomerIdOwner(int id);

  List<MobileSubscriber> getMobileSubscriberByCustomerIdUser(int id);

  int changeMobileSubscriberPlan(String mobileNumber1);

  int deleteByNumber(String mobileNumber);

}
