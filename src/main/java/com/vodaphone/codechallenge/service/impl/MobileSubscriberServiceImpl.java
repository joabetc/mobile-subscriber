package com.vodaphone.codechallenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.vodaphone.codechallenge.model.MobileSubscriber;
import com.vodaphone.codechallenge.model.repository.MobileSubscriberRepository;
import com.vodaphone.codechallenge.service.MobileSubscriberService;

public class MobileSubscriberServiceImpl implements MobileSubscriberService {

  @Autowired
  private MobileSubscriberRepository mobileSubscriberRepository;

  @Override
  public MobileSubscriber getMobileSubscriberByNumber(String mobileNumber) {
    return mobileSubscriberRepository.findByMsisdn(mobileNumber);
  }

}
