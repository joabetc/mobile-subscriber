package com.vodaphone.codechallenge.service;

import java.util.List;

import com.vodaphone.codechallenge.dto.MobileSubscriberDTO;
import com.vodaphone.codechallenge.dto.PlanDTO;

public interface MobileSubscriberService {
  
  MobileSubscriberDTO getMobileSubscriberByNumber(String mobileNumber);

  List<MobileSubscriberDTO> getAllMobileSubscribers();

  List<MobileSubscriberDTO> getMobileSubscriberByCustomerIdOwner(int id);

  List<MobileSubscriberDTO> getMobileSubscriberByCustomerIdUser(int id);

  MobileSubscriberDTO changeMobileSubscriberPlan(String mobileNumber1);

  int deleteByNumber(String mobileNumber);

  MobileSubscriberDTO createMobileSubscriber(MobileSubscriberDTO mobileSubscriber);

  void changePlanOwnerOrId(PlanDTO plan);

}
