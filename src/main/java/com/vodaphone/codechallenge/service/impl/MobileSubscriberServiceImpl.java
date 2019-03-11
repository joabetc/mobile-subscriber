package com.vodaphone.codechallenge.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodaphone.codechallenge.dto.MobileSubscriberDTO;
import com.vodaphone.codechallenge.dto.PlanDTO;
import com.vodaphone.codechallenge.exceptions.MobileSubscriberAlreadyExistsException;
import com.vodaphone.codechallenge.exceptions.MobileSubscriberNotFoundException;
import com.vodaphone.codechallenge.model.MobileSubscriber;
import com.vodaphone.codechallenge.model.ServiceType;
import com.vodaphone.codechallenge.model.repository.MobileSubscriberRepository;
import com.vodaphone.codechallenge.service.MobileSubscriberService;

@Service
public class MobileSubscriberServiceImpl implements MobileSubscriberService {
  
  private static ModelMapper mapper = new ModelMapper();

  @Autowired
  private MobileSubscriberRepository mobileSubscriberRepository;

  @Override
  public MobileSubscriberDTO getMobileSubscriberByNumber(String mobileNumber) {
    return mobileSubscriberRepository.findByMsisdn(mobileNumber)
        .map(subscriber -> {
          MobileSubscriberDTO mobileSubscriberDTO = new MobileSubscriberDTO();
          mapper.map(subscriber, mobileSubscriberDTO);
          return mobileSubscriberDTO;
        })
        .orElseThrow(() -> new MobileSubscriberNotFoundException(mobileNumber));
  }

  @Override
  public List<MobileSubscriberDTO> getAllMobileSubscribers() {
    List<MobileSubscriberDTO> mobileSubscriberDTOs = new ArrayList<>();
    mobileSubscriberRepository.findAll().forEach(new Consumer<MobileSubscriber>() {

      @Override
      public void accept(MobileSubscriber t) {
        MobileSubscriberDTO mobileSubscriberDTO = new MobileSubscriberDTO();
        mapper.map(t, mobileSubscriberDTO);
        mobileSubscriberDTOs.add(mobileSubscriberDTO);
      }
    });
    return mobileSubscriberDTOs;
  }

  @Override
  public List<MobileSubscriberDTO> getMobileSubscriberByCustomerIdOwner(int id) {
    List<MobileSubscriberDTO> mobileSubscriberDTOs = new ArrayList<>();
    mobileSubscriberRepository.findByCustomerIdOwner(id).forEach(new Consumer<MobileSubscriber>() {

      @Override
      public void accept(MobileSubscriber t) {
        MobileSubscriberDTO mobileSubscriberDTO = new MobileSubscriberDTO();
        mapper.map(t, mobileSubscriberDTO);
        mobileSubscriberDTOs.add(mobileSubscriberDTO);
      }
    });
    return mobileSubscriberDTOs;
  }

  @Override
  public List<MobileSubscriberDTO> getMobileSubscriberByCustomerIdUser(int id) {
    List<MobileSubscriberDTO> mobileSubscriberDTOs = new ArrayList<>();
    mobileSubscriberRepository.findByCustomerIdUser(id).forEach(new Consumer<MobileSubscriber>() {

      @Override
      public void accept(MobileSubscriber t) {
        MobileSubscriberDTO mobileSubscriberDTO = new MobileSubscriberDTO();
        mapper.map(t, mobileSubscriberDTO);
        mobileSubscriberDTOs.add(mobileSubscriberDTO);
      }
    });
    return mobileSubscriberDTOs;
  }

  @Override
  public MobileSubscriberDTO changeMobileSubscriberPlan(String mobileNumber) {
    return mobileSubscriberRepository.findByMsisdn(mobileNumber)
        .map(subscriber -> {
          subscriber.setServiceType(getOpositeServiceType(subscriber.getServiceType()));
          MobileSubscriberDTO mobileSubscriberDTO = new MobileSubscriberDTO();
          mapper.map(mobileSubscriberRepository.save(subscriber), mobileSubscriberDTO);
          return mobileSubscriberDTO;
        })
        .orElseThrow(() -> new MobileSubscriberNotFoundException(mobileNumber));
  }

  @Override
  public int deleteByNumber(String mobileNumber) {
    return mobileSubscriberRepository.deleteByMsisdn(mobileNumber);
  }

  @Override
  public MobileSubscriberDTO createMobileSubscriber(MobileSubscriberDTO mobileSubscriberDTO) {
    MobileSubscriber mobileSubscriber = new MobileSubscriber();
    mapper.map(mobileSubscriberDTO, mobileSubscriber);
    if (mobileSubscriberRepository.findByMsisdn(mobileSubscriber.getMsisdn()).isPresent()) {
      throw new MobileSubscriberAlreadyExistsException(mobileSubscriber.getMsisdn());
    } else {
      MobileSubscriber saved = mobileSubscriberRepository.save(mobileSubscriber);
      if (saved != null) {
        mapper.map(saved, mobileSubscriberDTO);
      }
      return mobileSubscriberDTO;
    }
  }
  
  private ServiceType getOpositeServiceType(ServiceType serviceType) {
    return ServiceType.getOpposite(serviceType);
  }

  @Override
  public void changePlanOwnerOrId(PlanDTO plan) {
    
    mobileSubscriberRepository.updatePlanOwnerUser(plan.getServiceType(), plan.getCustomerIdOwner(), plan.getCustomerIdUser());
    
  }

}
