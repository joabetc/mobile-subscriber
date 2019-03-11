package com.vodaphone.codechallenge.model.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vodaphone.codechallenge.model.MobileSubscriber;
import com.vodaphone.codechallenge.model.ServiceType;

public interface MobileSubscriberRepository extends CrudRepository<MobileSubscriber, Integer> {

  List<MobileSubscriber> findAll();

  Optional<MobileSubscriber> findByMsisdn(String msisdn);

  List<MobileSubscriber> findByCustomerIdOwner(Integer id);

  List<MobileSubscriber> findByCustomerIdUser(Integer id);

  @Transactional
  int deleteByMsisdn(String string);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "update MobileSubscriber ms set ms.customerIdOwner = :customerIdOwner, ms.customerIdUser = :customerIdUser where ms.serviceType = :serviceType")
  void updatePlanOwnerUser(
      @Param("serviceType") ServiceType serviceType, 
      @Param("customerIdOwner") Integer customerIdOwner, 
      @Param("customerIdUser") Integer customerIdUser);
  
}
