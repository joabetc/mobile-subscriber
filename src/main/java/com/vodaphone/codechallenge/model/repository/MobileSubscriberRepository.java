package com.vodaphone.codechallenge.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vodaphone.codechallenge.model.MobileSubscriber;

public interface MobileSubscriberRepository extends CrudRepository<MobileSubscriber, Integer> {

  List<MobileSubscriber> findAll();

  MobileSubscriber findByMsisdn(String msisdn);

  List<MobileSubscriber> findByCustomerIdOwner(Integer id);

  List<MobileSubscriber> findByCustomerIdUser(Integer id);
}
