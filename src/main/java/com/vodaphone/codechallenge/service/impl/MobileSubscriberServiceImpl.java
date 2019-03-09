package com.vodaphone.codechallenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodaphone.codechallenge.exceptions.MobileSubscriberNotFoundException;
import com.vodaphone.codechallenge.model.MobileSubscriber;
import com.vodaphone.codechallenge.model.ServiceType;
import com.vodaphone.codechallenge.model.repository.MobileSubscriberRepository;
import com.vodaphone.codechallenge.service.MobileSubscriberService;

@Service
public class MobileSubscriberServiceImpl implements MobileSubscriberService {

  @Autowired
  private MobileSubscriberRepository mobileSubscriberRepository;

  @Override
  public MobileSubscriber getMobileSubscriberByNumber(String mobileNumber) {
    return mobileSubscriberRepository.findByMsisdn(mobileNumber)
        .orElseThrow(() -> new MobileSubscriberNotFoundException(mobileNumber));
  }

  @Override
  public List<MobileSubscriber> getAllMobileSubscribers() {
    return mobileSubscriberRepository.findAll();
  }

  @Override
  public List<MobileSubscriber> getMobileSubscriberByCustomerIdOwner(int id) {
    return mobileSubscriberRepository.findByCustomerIdOwner(id);
  }

  @Override
  public List<MobileSubscriber> getMobileSubscriberByCustomerIdUser(int id) {
    return mobileSubscriberRepository.findByCustomerIdUser(id);
  }

  @Override
  public int changeMobileSubscriberPlan(String mobileNumber) {
    MobileSubscriber mobileSubscriber = mobileSubscriberRepository.findByMsisdn(mobileNumber);
    return mobileSubscriberRepository.setPlan(mobileNumber, getOpositeServiceType(mobileSubscriber.getServiceType()));
  }
  
  private ServiceType getOpositeServiceType(ServiceType serviceType) {
    return ServiceType.getOpposite(serviceType);
  }

  @Override
  public int deleteByNumber(String mobileNumber) {
    return mobileSubscriberRepository.deleteByMsisdn(mobileNumber);
  }

}
