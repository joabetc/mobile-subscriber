package com.vodaphone.codechallenge.model.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.vodaphone.codechallenge.model.MobileSubscriber;

public interface MobileSubscriberRepository extends CrudRepository<MobileSubscriber, Integer> {

  List<MobileSubscriber> findAll();

  Optional<MobileSubscriber> findByMsisdn(String msisdn);

  List<MobileSubscriber> findByCustomerIdOwner(Integer id);

  List<MobileSubscriber> findByCustomerIdUser(Integer id);

  @Transactional
  int deleteByMsisdn(String string);
  
}
