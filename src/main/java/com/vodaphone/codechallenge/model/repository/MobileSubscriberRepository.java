package com.vodaphone.codechallenge.model.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vodaphone.codechallenge.model.MobileSubscriber;
import com.vodaphone.codechallenge.model.ServiceType;

public interface MobileSubscriberRepository extends CrudRepository<MobileSubscriber, Integer> {

  List<MobileSubscriber> findAll();

  MobileSubscriber findByMsisdn(String msisdn);

  List<MobileSubscriber> findByCustomerIdOwner(Integer id);

  List<MobileSubscriber> findByCustomerIdUser(Integer id);

  @Transactional
  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Query("update MobileSubscriber mo set mo.serviceType = ?2 where mo.msisdn = ?1")
  int setPlan(String msisdn, ServiceType serviceType);

  @Transactional
  int deleteByMsisdn(String string);
  
}
